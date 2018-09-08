#include <Windows.h>
#include <cstdio>
#include <string>

#include "PipeProtocolHandler.h"
#include "PipeMessage.h"

#define BUFLEN 512  //Max length of buffer

/*
say:      mode=1
whisper:  mode=2
yell:     mode=3
*/
void(*_tibia_say)(int mode, const char* buff) = (void(*) (int, const char*))0x00407310;

void(*_tibia_turn_north)() = (void(*) ())0x00404730;
void(*_tibia_turn_south)() = (void(*) ())0x00404A70;
void(*_tibia_turn_west)() = (void(*) ())0x00404c10;
void(*_tibia_turn_east)() = (void(*) ())0x004048D0;


/*
use on floor:
flag_FFFF = use_x
src       = use_y
slot      = use_z
id        = item_id
dstflag   = 1
dstbpid   = first_closed_bp_index
use on eq:
flag_FFFF = 0xFFFF(65535)
src       = eq_slot_index
slot      = 0
id        = item_id
dstflag   = 0
dstbpid   = first_closed_bp_index
use on bp:
flag_FFFF = 0xFFFF(65535)
src       = src_bp_index+0x40
slot      = src_slot_index
id        = item_id
dstflag   = src_slot_index
dstbpid   = if (open in new container) first_closed_bp_index else src_bp_index
*/
void(*_tibia_use_item)(int flag_FFFF, int src, int slot,
	int id, int dstflag, int dstbpid/* first closed bp id*/)
	= (void(*)(int, int, int, int, int, int))0x004056f0;

/*
move from bp to bp:
flag          = 0xFFFF
srccontainer  = dst_bp_index+0x40
srcslot       = dst_slot_index
id            = item_id
srcslotflag   = srcslot
flag2         = 0xFFFF
container     = src_bp_index+0x40
dstslot       = src_slot_index
count         = item_count
move from eq to bp:
flag          = 0xFFFF
srccontainer  = eq_slot_index
src_slot      = 0
id            = item_id
srcslotflag   = 0
flag2         = 0xFFFF
container     = dst_bp_index+0x40
dstslot       = dst_slot_index
count         = item_count
move from bp to eq:
flag          = 0xFFFF
srccontainer  = src_bp_index+0x40
srcslot       = src_slot_index
id            = item_id
srcslotflag   = srcslot
flag2         = 0xFFFF
container     = eq_slot_index
dstslot       = 0
count         = item_count
move from bp to ground:
*/
void(*_tibia_move_item)(int flag, int srccontainer, int srclot, int id,
	int srcslotflag, int flag2, int container, int dstslot, int count)
	= (void(*)(int, int, int, int, int, int, int, int, int))0x00404DB0;


void(*_tibia_use_with)(int flag, int srccontainer, int srclot, int id,
	int srcslotflag, int x, int y, int z, int idtouseon, int srcstackpos)
	= (void(*)(int, int, int, int, int, int, int, int, int, int))0x004058f0;

/*
stops attack
*/
void(*_tibia_stop)() = (void(*)())0x00409140;

// ATTACK 408E40;
// 1 param - creature id

void(*_tibia_attack)(unsigned int creatureid) = (void(*)(unsigned int))0x00408E40;


// command ids
static const int CMD_SAY = 0;
static const int CMD_ATTACK = 1;

// read commands
static const int CMD_READ_MEM = 100;
static const int CMD_WSOCK_ADDR = 101;

// set memory commands


FILE* f;

void ProcessCommand(char *cmd, HANDLE pipe)
{
	switch (cmd[0])
	{
		case CMD_SAY:
			_tibia_say(cmd[1], &cmd[2]);
			break;
		case CMD_ATTACK:
			_tibia_attack(*(unsigned int*)&cmd[1]);
			break;
		default:
			break;
	}
}

bool ProcessSay(PipeProtocolHandler* handler, PipeMessage* message) {
	unsigned char channel = message->nextByte();
	const char* text = message->nextText();
	switch (channel) {
	case 1: case 2: case 3:
		_tibia_say(channel, text);
		return true;
	}
	return false;
}

bool ProcessReadMem(PipeProtocolHandler* handler, PipeMessage* message) {
	DWORD address = message->nextDWORD();
	DWORD size = message->nextDWORD();
	return handler->sendData((char*)address, size);
}

bool ProcessDetermineWsockAddr(PipeProtocolHandler* handler) {
	DWORD address = (DWORD) GetModuleHandle("ws2_32.dll");
	return handler->sendData((char*)&address, sizeof(DWORD));
}

bool ProcessMessage(PipeProtocolHandler* handler, PipeMessage* message) {
	unsigned char opCode = message->nextByte();
	switch (opCode) {
	case CMD_SAY:
		return ProcessSay(handler, message);
	case CMD_READ_MEM:
		return ProcessReadMem(handler, message);
	case CMD_WSOCK_ADDR:
		return ProcessDetermineWsockAddr(handler);
	default:
		break;
	}
	return true;
}

/*
https://github.com/avidinsight/win32-named-pipes-example
https://msdn.microsoft.com/en-us/library/windows/desktop/aa365150(v=vs.85).aspx
*/
void PipeControl()
{
	fopen_s(&f, "payload_debug.dat", "a+");
	DWORD pId = GetCurrentProcessId();
	char pipeName[256];
	sprintf_s(pipeName, "\\\\.\\pipe\\oldTibiaBot%d", pId);

	while (true) {
		PipeProtocolHandler pipeHandler = PipeProtocolHandler(pipeName);
		if (!pipeHandler.connect())
			continue;

		while (true) {
			// handle incoming msgs
			PipeMessage message = pipeHandler.readMessage();
			if (message.error())
				break;

			ProcessMessage(&pipeHandler, &message);
		}
	}


//start_listening:
//	// Create a pipe to send data
//	HANDLE pipe = CreateNamedPipe(
//		pipeName, // name of the pipe
//		PIPE_ACCESS_DUPLEX, // 2-way pipe
//		PIPE_TYPE_MESSAGE, // send data as a byte stream
//		1, // only allow 1 instance of this pipe
//		0, // no outbound buffer
//		0, // no inbound buffer
//		0, // use default wait time
//		NULL // use default security attributes
//	);
//
//	if (pipe == NULL || pipe == INVALID_HANDLE_VALUE) {
//		goto start_listening;
//	}
//
//	// This call blocks until a client process connects to the pipe
//	BOOL result = ConnectNamedPipe(pipe, NULL);
//	if (!result) {
//		CloseHandle(pipe); // close the pipe
//		goto start_listening;
//	}
//
//	char buffer[BUFLEN];
//	while (1)
//	{
//		// The read operation will block until there is data to read
//		DWORD numBytesRead = 0;
//		result = ReadFile(
//			pipe,
//			buffer, // the data from the pipe will be put here
//			BUFLEN * sizeof(char), // number of bytes allocated
//			&numBytesRead, // this will store number of bytes actually read
//			NULL // not using overlapped IO
//		);
//
//		if (result) {
//			buffer[numBytesRead] = 0;
//			fwrite(buffer, 1, numBytesRead+1, f);
//			ProcessCommand(buffer, pipe);
//		}
//		else {
//			CloseHandle(pipe);
//			goto start_listening;
//		}
//	}
}

BOOL APIENTRY DllMain(HMODULE hModule, DWORD ul_reason_for_call, LPVOID lpReserved)
{
	switch (ul_reason_for_call)
	{
	case DLL_PROCESS_ATTACH:
		DisableThreadLibraryCalls(hModule);
		CreateThread(NULL, 0, (unsigned long(__stdcall*)(void*))PipeControl, NULL, 0, NULL);
	}
	return TRUE;
}