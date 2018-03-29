#pragma once
#include <windows.h>

#include <vector>

#include "config.h"

class Battlelist;
class Player;
class Inventory;

class MemoryReader
{
public:
	MemoryReader(DWORD pId);
	~MemoryReader();

	Battlelist& getBattleList();
	Player& getPlayer();
	uint32_t getSelfId();

	Inventory getInventory();


	uint32_t getPId();
	void writeSelfIsWalking(bool);
	void writeData(uint32_t address, char* buff, uint32_t size);

	uint32_t readUint32_t(uint32_t address);
	void* readStructure(uint32_t address, uint32_t size, void* buffer);
private:
	DWORD m_pId;
	HANDLE m_pHandle;

	uint8_t m_battleListBuffer[sizeof(BattleListEntry_t) * CONSTS::BATTLELIST_SIZE];

	Battlelist* m_pBattleList;
	Player* m_pPlayer;

};

