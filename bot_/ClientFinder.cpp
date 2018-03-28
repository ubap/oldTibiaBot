#include "ClientFinder.h"

void ClientFinder::findClients()
{
	// Get the list of process identifiers.
	DWORD aProcesses[1024], cbNeeded, cProcesses;
	unsigned int i;

	if (!EnumProcesses(aProcesses, sizeof(aProcesses), &cbNeeded))
	{
		return;
	}

	cProcesses = cbNeeded / sizeof(DWORD);

	// Print the name and process identifier for each process.
	for (i = 0; i < cProcesses; i++)
	{
		DWORD processID = aProcesses[i];
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
				} else
				{
					DWORD err = GetLastError();
				}
			} else
			{
				DWORD err = GetLastError();
			}

			// Populate process list
			std::string processName(szProcessName);
			if (processName == "Tibijka.exe")
			{
				std::string characterName = readCharacterName(processID);
				processList.push_back(ProcessDesc(processID, characterName));
			}

			// Release the handle to the process.
			CloseHandle(hProcess);
		}
	}
}

std::string ClientFinder::readCharacterName(DWORD pId)
{
	std::string characterName = "<unknown>";
	HANDLE pHandle = OpenProcess(PROCESS_ALL_ACCESS, false, pId);

	// read id of our player
	uint32_t selfId;
	ReadProcessMemory(pHandle, (LPCVOID)ADDR::SELF_ID, &selfId, sizeof(selfId), NULL);

	// search the battlelist for entry for our player
	const uint32_t bytesToRead = sizeof(BattleListEntry_t) * CONSTS::BATTLELIST_SIZE;
	uint8_t battleList[bytesToRead];
	ReadProcessMemory(pHandle, (LPCVOID)ADDR::BATTLELIST_BEGIN, battleList, bytesToRead, NULL);
	BattleListEntry_t* pBattleList = (BattleListEntry_t*)battleList;

	for (int i = 0; i < CONSTS::BATTLELIST_SIZE; i++)
	{
		if (pBattleList[i].id == selfId)
		{
			characterName = pBattleList[i].name;
			break;
		}

	}
	CloseHandle(pHandle);

	return characterName;
}

std::vector<ProcessDesc>& ClientFinder::getClients()
{
	findClients();
	return processList;
}