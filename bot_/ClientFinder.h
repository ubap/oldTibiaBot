#pragma once
#include <windows.h>
#include <stdio.h>
#include <tchar.h>
#include <psapi.h>

#include <vector>
#include <string>

#include "config.h"

class ProcessDesc {
public:
	ProcessDesc(DWORD pId, std::string characterName) : m_pID(pId), m_CharacterName(characterName) {};
	~ProcessDesc() {};

	DWORD getpId() { return m_pID; };
	std::string getCharacterName() { return m_CharacterName; };
private:
	DWORD m_pID;
	std::string m_CharacterName;
};

class ClientFinder
{
public:
	ClientFinder() {};
	~ClientFinder() {};
	std::vector<ProcessDesc>& getClients();

private:
	void findClients();
	std::string readCharacterName(DWORD pId);

	std::vector<ProcessDesc> processList;
};

