#include <Windows.h>
#include <cstdio>

#define BUFLEN 512  //Max length of buffer

/*
say:      mode=1
whisper:  mode=2
yell:     mode=3
*/
void(*_tibia_say)(int mode, char* buff) = (void(*) (int, char*))0x004067c0;

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

void ProcessCommand(char *cmd)
{
  char *tok;
  char *delimiters = "(,)";
  char* context = NULL;

  tok = strtok_s(cmd, delimiters, &context);
  int cmdLen = strlen(tok);
  for (int i = 0; i < cmdLen; i++)
    tok[i] = tolower(tok[i]);
  if (strcmp(tok, "say") == 0)
  {
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return;
    int mode = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return;
     _tibia_say(mode, tok);
  }
  else if (strcmp(tok, "useitem") == 0)
  {
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param1 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param2 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param3 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param4 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param5 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param6 = atoi(tok);

    _tibia_use_item(param1, param2, param3, param4, param5, param6);
  }
  else if (strcmp(tok, "usewith") == 0)
  {
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param1 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param2 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param3 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param4 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param5 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param6 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param7 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param8 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param9 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param10 = atoi(tok);


    _tibia_use_with(param1, param2, param3, param4, param5, param6, 
      param7, param8, param9, param10);
  }
  else if (strcmp(tok, "moveitem") == 0)
  {
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param1 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param2 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param3 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param4 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param5 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param6 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param7 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param8 = atoi(tok);
    tok = strtok_s(NULL, delimiters, &context);
    if (!tok) return; int param9 = atoi(tok);

    _tibia_move_item(param1, param2, param3, param4, param5, param6, param7, param8, param9);
  }
  else if (strcmp(tok, "stop") == 0)
  {
    _tibia_stop();
  }
  else if (strcmp(tok, "turnnorth") == 0)
  {
    _tibia_turn_north();
  }
  else if (strcmp(tok, "turnsouth") == 0)
  {
    _tibia_turn_south();
  }
  else if (strcmp(tok, "turnwest") == 0)
  {
    _tibia_turn_west();
  }
  else if (strcmp(tok, "turneast") == 0)
  {
    _tibia_turn_east();
  }

    return;
}

/*
https://github.com/avidinsight/win32-named-pipes-example
https://msdn.microsoft.com/en-us/library/windows/desktop/aa365150(v=vs.85).aspx
*/
void PipeControl()
{
  DWORD pId = GetCurrentProcessId();
  char pipeName[256];
  sprintf_s(pipeName, "\\\\.\\pipe\\thronia%d", pId);

start_listening:
  // Create a pipe to send data
  HANDLE pipe = CreateNamedPipe(
    pipeName, // name of the pipe
    PIPE_ACCESS_INBOUND, // 1-way pipe -- raed only
    PIPE_TYPE_MESSAGE, // send data as a byte stream
    1, // only allow 1 instance of this pipe
    0, // no outbound buffer
    0, // no inbound buffer
    0, // use default wait time
    NULL // use default security attributes
    );

  if (pipe == NULL || pipe == INVALID_HANDLE_VALUE) {
    goto start_listening;
  }

  // This call blocks until a client process connects to the pipe
  BOOL result = ConnectNamedPipe(pipe, NULL);
  if (!result) {
    CloseHandle(pipe); // close the pipe
    goto start_listening;
  }

  char buffer[BUFLEN];
  while (1)
  {
    // The read operation will block until there is data to read
    DWORD numBytesRead = 0;
    result = ReadFile(
      pipe,
      buffer, // the data from the pipe will be put here
      BUFLEN * sizeof(char), // number of bytes allocated
      &numBytesRead, // this will store number of bytes actually read
      NULL // not using overlapped IO
      );

    if (result) {
      buffer[numBytesRead] = 0;
      ProcessCommand(buffer);
    }
    else {
      CloseHandle(pipe);
      goto start_listening;
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