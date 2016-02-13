#include <cstdio>
#include <Windows.h>

/*
http://resources.infosecinstitute.com/using-createremotethread-for-dll-injection-on-windows/
*/

int main(int argc, char **argv)
{
  if (argc != 3)
  {
    printf("Usage: pId dll");
    return 1;
  }
  char* dllName = argv[2];
  DWORD pId = atoi(argv[1]);

  HANDLE pHandle = OpenProcess(PROCESS_ALL_ACCESS, false, pId);
  if (!pHandle)
  {
    printf("Error: Could not get process handle");
    return 2;
  }

  LPVOID addr = (LPVOID)GetProcAddress(GetModuleHandle("kernel32.dll"), "LoadLibraryA");
  if (!addr)
  {
    printf("Error: the LoadLibraryA function was not found inside kernel32.dll library.");
    return 3;
  }

  LPVOID arg = (LPVOID)VirtualAllocEx(pHandle, NULL, strlen(dllName), MEM_RESERVE | MEM_COMMIT, PAGE_READWRITE);
  if (!arg)
  {
    printf("Error: the memory could not be allocated inside the chosen process.");
    return 4;
  }

  int n = WriteProcessMemory(pHandle, arg, dllName, strlen(dllName), NULL);
  if (n == 0)
  {
    printf("Error: there was no bytes written to the process's address space.");
    return 5;
  }

  HANDLE threadID = CreateRemoteThread(pHandle, NULL, 0, (LPTHREAD_START_ROUTINE)addr, arg, NULL, NULL);
  if (!threadID)
  {
    printf("Error: the remote thread could not be created.");
    return 6;
  }

  CloseHandle(pHandle);

  printf("Success: the remote thread was successfully created.");
  return 0;
}