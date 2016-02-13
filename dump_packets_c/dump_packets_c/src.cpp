#include <Windows.h>
//#include <winsock2.h>
#include <vector>

#pragma comment (lib, "Ws2_32.lib")

#define BUFLEN 512  //Max length of buffer
#define PORT 8888   //The port on which to listen for incoming data

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

  tok = strtok_s(cmd, "delimiters", &context);
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

void InitSocket()
{
  SOCKET s;
  struct sockaddr_in server, si_other;
  int slen, recv_len;
  char buf[BUFLEN];
  WSADATA wsa;

  slen = sizeof(si_other);

  if (WSAStartup(MAKEWORD(2, 2), &wsa) != 0)
  {
    MessageBoxA(NULL, "WSAStartup failed.", "Error injecting DLL", MB_OK);
    return;
  }

  //Create a socket
  if ((s = socket(AF_INET, SOCK_DGRAM, 0)) == INVALID_SOCKET)
  {
    MessageBoxA(NULL, "Could not create socket", "Error injecting DLL", MB_OK);
    return;
  }

  //Prepare the sockaddr_in structure
  server.sin_family = AF_INET;
  server.sin_addr.s_addr = INADDR_ANY;
  server.sin_port = htons(PORT);

  //Bind
  if (bind(s, (struct sockaddr *)&server, sizeof(server)) == SOCKET_ERROR)
  {
    MessageBoxA(NULL, "Bind failed with error code", "Error injecting DLL", MB_OK);
    return;
  }

  MessageBoxA(NULL, "In like Flynn!", "Success injecting DLL", MB_OK);
  //keep listening for data
  while (1)
  {

    //clear the buffer by filling null, it might have previously received data
    memset(buf, '\0', BUFLEN);

    //try to receive some data, this is a blocking call
    if ((recv_len = recvfrom(s, buf, BUFLEN, 0, (struct sockaddr *) &si_other, &slen)) == SOCKET_ERROR)
    {
      //printf("recvfrom() failed with error code : %d", WSAGetLastError());
      return;
    }

    ProcessCommand(buf);
 
  //print details of the client/peer and the data received
  //printf("Received packet from %s:%d\n", inet_ntoa(si_other.sin_addr), ntohs(si_other.sin_port));
  //printf("Data: %s\n", buf);

  //now reply the client with the same data
  //if (sendto(s, buf, recv_len, 0, (struct sockaddr*) &si_other, slen) == SOCKET_ERROR)
  //{
  //  //printf("sendto() failed with error code : %d", WSAGetLastError());
  //  return;
  // }
}

closesocket(s);
WSACleanup();

return;
}

BOOL APIENTRY DllMain(HMODULE hModule, DWORD ul_reason_for_call, LPVOID lpReserved)
{
  switch (ul_reason_for_call)
  {
  case DLL_PROCESS_ATTACH:
    DisableThreadLibraryCalls(hModule);
    CreateThread(NULL, 0, (unsigned long(__stdcall*)(void*))InitSocket, NULL, 0, NULL);
  }
  return TRUE;
}