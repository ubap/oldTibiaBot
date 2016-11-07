#pragma once
#include <windows.h>

#include <vector>

#include "config.h"

class Battlelist;
class Player;

class MemoryReader
{
public:
	MemoryReader(DWORD pId);
	~MemoryReader();

	Battlelist& getBattleList();
	Player& getPlayer();
	uint32_t getSelfId();


	uint32_t getPId();
	void writeSelfIsWalking(bool);
	void writeData(uint32_t address, char* buff, uint32_t size);
private:
	DWORD m_pId;
	HANDLE m_pHandle;

	uint8_t m_battleListBuffer[sizeof(BattleListEntry_t) * CONSTS::BATTLELIST_SIZE];

	Battlelist* m_pBattleList;
	Player* m_pPlayer;

	uint32_t getSelfBattlelistAddress();
};

