#include <cstdio>
#include <Windows.h>
#include <shlwapi.h>
#include <stdio.h>
#include <tchar.h>
#include <psapi.h>

#include "tclap/CmdLine.h"

/*
http://resources.infosecinstitute.com/using-createremotethread-for-dll-injection-on-windows/
*/

int inject(DWORD pId, const char* dllPath)
{

	char dllAbsolutePath[MAX_PATH];
	if (PathIsRelative(dllPath)) {
		int pathLength = GetCurrentDirectory(MAX_PATH, dllAbsolutePath);
		dllAbsolutePath[pathLength] = '\\'; // path separator
		strcpy_s(dllAbsolutePath + pathLength + 1, MAX_PATH - pathLength - 1, dllPath);
	}
	else {
		strcpy_s(dllAbsolutePath, MAX_PATH, dllPath);
	}
	if (!PathFileExists(dllAbsolutePath)) {
		printf("Error: dll path incorrect.");
		return 7;
	}
	printf("Info: Payload file: %s\n", dllAbsolutePath);

	HANDLE pHandle = OpenProcess(PROCESS_ALL_ACCESS, false, pId);
	if (!pHandle)
	{
		printf("Error: Could not get process handle for pId: %d", pId);
		return 2;
	}

	LPVOID addr = (LPVOID)GetProcAddress(GetModuleHandle("kernel32.dll"), "LoadLibraryA");
	if (!addr)
	{
		printf("Error: the LoadLibraryA function was not found inside kernel32.dll library.");
		return 3;
	}

	LPVOID arg = (LPVOID)VirtualAllocEx(pHandle, NULL, strlen(dllAbsolutePath), MEM_RESERVE | MEM_COMMIT, PAGE_READWRITE);
	if (!arg)
	{
		printf("Error: the memory could not be allocated inside the chosen process.");
		return 4;
	}

	int n = WriteProcessMemory(pHandle, arg, dllAbsolutePath, strlen(dllAbsolutePath), NULL);
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

/**
	Return values:
		0    - Error
		!= 0 - Process Id
**/
DWORD scanForProcess(const char* processName) {
	// Get the list of process identifiers.
	std::vector<int> pIds;
	DWORD aProcesses[1024], cbNeeded, cProcesses;
	unsigned int i;

	if (!EnumProcesses(aProcesses, sizeof(aProcesses), &cbNeeded))
	{
		return 0;
	}

	cProcesses = cbNeeded / sizeof(DWORD);

	// Print the name and process identifier for each process.
	for (i = 0; i < cProcesses; i++)
	{
		DWORD processID = aProcesses[i];
		DWORD err;
		if (processID != 0)
		{
			TCHAR szProcessName[MAX_PATH] = TEXT("<unknown>");

			// Get a handle to the process.
			HANDLE hProcess = OpenProcess(PROCESS_QUERY_INFORMATION |
				PROCESS_VM_READ,
				FALSE, processID);

			// Get the process name.
			if (NULL != hProcess)
			{
				HMODULE hMod;
				DWORD cbNeeded;

				if (EnumProcessModules(hProcess, &hMod, sizeof(hMod),
					&cbNeeded))
				{
					GetModuleBaseName(hProcess, hMod, szProcessName,
						sizeof(szProcessName) / sizeof(TCHAR));
				}
				else
				{
					err = GetLastError();
				}
			}
			else
			{
				err = GetLastError();
			}

			// Populate process list
			std::string currentProcessName(szProcessName);
			if (strcmp(currentProcessName.c_str(), processName) == 0) {
				printf("[%d]. %d\n", pIds.size() + 1, processID);
				pIds.push_back(processID);
			}

			// Release the handle to the process.
			CloseHandle(hProcess);
		}
	}
	if (pIds.size() == 1) {
		return pIds.at(0);
	}
	if (pIds.size() > 1) {
		printf("Select process to inject to (1 - %d): ", pIds.size());
		unsigned int n;
		scanf_s("%d", &n);
		n--;
		if (n >= 0 && n < pIds.size())
			return pIds.at(n);
	}
	// error
	return 0;
}

int main(int argc, char **argv)
{

	// Wrap everything in a try block.  Do this every time,
	// because exceptions will be thrown for problems.
	try {
		TCLAP::CmdLine cmd("Dll injection tool. Jakub Trzebiatowski", ' ', "1.0");

		// Define a value argument and add it to the command line.
		// A value arg defines a flag and a type of value that it expects,
		// such as "-n Bishop".
		TCLAP::ValueArg<std::string> nameArg("n", "name", "Process name to look for (if no pId specified)",
			false, "Kasteria.exe", "string");
		cmd.add(nameArg);

		TCLAP::ValueArg<int> pIdArg("p", "pId", "Process id to attach to",
			false, 1337, "DWORD");
		cmd.add(pIdArg);

		TCLAP::ValueArg<std::string> dllArg("d", "dll", "dll payload to inject. Relative or absolute path.",
			false, "injector.exe", "string");
		cmd.add(dllArg);

		// Parse the argv array.
		cmd.parse(argc, argv);

		DWORD pId;
		if (pIdArg.isSet())
			pId = pIdArg.getValue();
		else if (nameArg.isSet())
			pId = scanForProcess(nameArg.getValue().c_str());
		else {
			printf("Error: Process id or Process name is required!");
			return 55;
		}

		const char* dllPath;
		if (dllArg.isSet())
			dllPath = dllArg.getValue().c_str();
		else
			dllPath = "payload.dll";

		inject(pId, dllPath);

		// Get the value parsed by each arg.
		//std::string processName = nameArg.getValue();
		//int pId = pIdArg.

	}
	catch (TCLAP::ArgException &e)  // catch any exceptions
	{
		std::cerr << "error: " << e.error() << " for arg " << e.argId() << std::endl;
	}


	
}