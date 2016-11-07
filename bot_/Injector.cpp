#include "Injector.h"

uint32_t INJECTOR::inject(DWORD pId, const std::string dllName)
{
	char dllPath[2100];
	int pathLength = GetCurrentDirectory(2048, (LPSTR)dllPath);
	if (!pathLength)
		return 1;
	dllPath[pathLength] = '\\';
	memcpy(&dllPath[pathLength+1], dllName.c_str(), dllName.size()+1);

	HANDLE pHandle = OpenProcess(PROCESS_ALL_ACCESS, false, pId);
	if (!pHandle)
		return 2;

	LPVOID addr = (LPVOID)GetProcAddress(GetModuleHandle("kernel32.dll"), "LoadLibraryA");
	if (!addr)
		return 3;

	LPVOID arg = (LPVOID)VirtualAllocEx(pHandle, NULL, strlen(dllPath), MEM_RESERVE | MEM_COMMIT, PAGE_READWRITE);
	if (!arg)
		return 4;

	int n = WriteProcessMemory(pHandle, arg, dllPath, strlen(dllPath), NULL);
	if (n == 0)
		return 5;

	HANDLE threadID = CreateRemoteThread(pHandle, NULL, 0, (LPTHREAD_START_ROUTINE)addr, arg, NULL, NULL);
	if (!threadID)
		return 6;

	CloseHandle(pHandle);

	return 0;
}