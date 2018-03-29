#include "Creature.h"



Creature::Creature(BattleListEntry_t* pBattleListEntry, uint32_t nBattleListPos)
{
	m_pBattleListEntry = pBattleListEntry;
	m_nBattleListPos = nBattleListPos;
	m_nCreatureId = pBattleListEntry->id;
	m_sName = pBattleListEntry->name;
	m_nHPpc = pBattleListEntry->HPBar;
	m_nPosX = pBattleListEntry->posx;
	m_nPosY = pBattleListEntry->posy;
	m_nPosZ = pBattleListEntry->posz;
	m_bIsWalking = (pBattleListEntry->walking == 1);
}


Creature::~Creature()
{
}

BattleListEntry_t* Creature::getBattleListEntry() const
{
	return m_pBattleListEntry;
}

uint32_t Creature::getBattleListPos() const
{
	return m_nBattleListPos;
}

std::string Creature::getName() const
{
	return m_sName;
}

uint32_t Creature::getHPpc() const
{
	return m_nHPpc;
}

uint32_t Creature::getPosX() const
{
	return m_nPosX;
}

uint32_t Creature::getPosY() const
{
	return m_nPosY;
}

uint32_t Creature::getPosZ() const
{
	return m_nPosZ;
}

uint32_t Creature::getId() const
{
	return m_nCreatureId;
}

uint32_t Creature::getIsWalking() const
{
	return m_bIsWalking;
}