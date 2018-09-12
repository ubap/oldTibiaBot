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
static const int CMD_CALL = 1;
static const int CMD_READ_MEM = 2;
static const int CMD_WRITE_MEM = 3;

FILE* f;


void callWithDynamicArgs(void* addr, unsigned int argCount, void* args) {
	_asm {
		mov eax, argCount;
		mov ecx, args

		test eax, eax
		jz no_args
		add_argument_to_stack:
			add eax, -1
			lea ebx, [ecx + eax * 4]
			push[ebx]

			test eax, eax
			jnz add_argument_to_stack;
		no_args:
			call addr;
			mov eax, argCount;
			test eax, eax
			jz dont_restore_stack;

			imul eax, 4;
			add esp, eax;
		dont_restore_stack:
	}
}

bool ProcessCall(PipeMessage* message) {
	DWORD address = message->nextDWORD();
	int argCount = message->nextDWORD();
	int argTypes[1024];
	void* args[1024];
	for (int i = 0; i < argCount; i++) {
		argTypes[i] = message->nextDWORD(); // 1 - int, 2 - char*
		switch (argTypes[i]) {
		case 1: {
			args[i] = (void*)message->nextDWORD(); break;
		}
		case 2: {
			int length = message->nextDWORD();
			args[i] = (void*)message->nextBytes(length);
			break;
		}
		default:
			return false;
		}
	}

	callWithDynamicArgs((void*)address, argCount, args);
}

bool ProcessReadMem(PipeProtocolHandler* handler, PipeMessage* message) {
	DWORD address = message->nextDWORD();
	DWORD size = message->nextDWORD();
	return handler->sendData((char*)address, size);
}

bool ProcessWriteMem(PipeMessage* message) {
	DWORD address = message->nextDWORD();
	DWORD size = message->nextDWORD();
	const char* data = message->nextBytes(size);
	if (data == nullptr)
		return false;
	memcpy((void*) address, data, size);
	return true;
}

bool ProcessDetermineWsockAddr(PipeProtocolHandler* handler) {
	DWORD address = (DWORD) GetModuleHandle("ws2_32.dll");
	return handler->sendData((char*)&address, sizeof(DWORD));
}

bool ProcessMessage(PipeProtocolHandler* handler, PipeMessage* message) {
	int opCode = message->nextDWORD();
	switch (opCode) {
		case CMD_CALL:
			return ProcessCall(message);
		case CMD_READ_MEM:
			return ProcessReadMem(handler, message);
		case CMD_WRITE_MEM:
			return ProcessWriteMem(message);
	}


	return false;
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
			try {
			// handle incoming msgs
			PipeMessage message = pipeHandler.readMessage();
			if (message.error())
				break;
				ProcessMessage(&pipeHandler, &message);
			}
			catch (...) {
				break;
			}
		}
	}
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