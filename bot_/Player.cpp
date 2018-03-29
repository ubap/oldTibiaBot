#include "Player.h"

#include "MemoryReader.h"
#include "Battlelist.h"

Player::Player(MemoryReader& rMemoryReader, BattleListEntry_t* pBattleListEntry, uint32_t nBattleListPos)
	: Creature(pBattleListEntry, nBattleListPos)
{
	m_nHp = rMemoryReader.readUint32_t(ADDR::HP);
	m_nMp = rMemoryReader.readUint32_t(ADDR::MP);
	m_nCap = rMemoryReader.readUint32_t(ADDR::CAP);
}


Player::~Player()
{
}

