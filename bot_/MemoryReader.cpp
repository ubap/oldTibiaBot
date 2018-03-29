#include "MemoryReader.h"

#include "Battlelist.h"
#include "Player.h"
#include "Inventory.h"

MemoryReader::MemoryReader(DWORD pId) : m_pId(pId)
{
	m_pHandle = OpenProcess(PROCESS_ALL_ACCESS, false, pId);
	if (m_pHandle == NULL)
		throw;
}


MemoryReader::~MemoryReader()
{
}

uint32_t MemoryReader::readUint32_t(uint32_t address)
{
	uint32_t value;
	ReadProcessMemory(m_pHandle, (LPCVOID)address, &value, sizeof(uint32_t), NULL);
	return value;
}

void* MemoryReader::readStructure(uint32_t address, uint32_t size, void* buffer)
{
	ReadProcessMemory(m_pHandle, (LPCVOID)address, buffer, size, NULL);
	return buffer;
}

Battlelist& MemoryReader::getBattleList()
{

	ReadProcessMemory(m_pHandle, (LPCVOID)ADDR::BATTLELIST_BEGIN, m_battleListBuffer, sizeof(m_battleListBuffer), NULL);
	BattleListEntry_t* pBattleList = (BattleListEntry_t*)m_battleListBuffer;

	if (!m_pBattleList)
		m_pBattleList = new Battlelist(pBattleList);
	else
		m_pBattleList->init(pBattleList);

	return *m_pBattleList;
}

Player& MemoryReader::getPlayer()
{
	uint32_t selfId = getSelfId();
	const Creature* self = getBattleList().getCreature(selfId);

	m_pPlayer = new Player(*this, self->getBattleListEntry(), self->getBattleListPos());

	return *m_pPlayer;
}

uint32_t MemoryReader::getSelfId()
{
	uint32_t selfId;
	ReadProcessMemory(m_pHandle, (LPCVOID)ADDR::SELF_ID, &selfId, 4, NULL);
	return selfId;
}

void MemoryReader::writeData(uint32_t address, char* buff, uint32_t size)
{
	WriteProcessMemory(m_pHandle, (LPVOID)address, buff, size, NULL);
}

void MemoryReader::writeSelfIsWalking(bool)
{

}

uint32_t MemoryReader::getPId()
{
	return m_pId;
}

Inventory MemoryReader::getInventory()
{
	return Inventory(*this);
}
