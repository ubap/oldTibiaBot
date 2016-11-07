#include "Creature.h"



Creature::Creature(BattleListEntry_t* pBattleListEntry, uint32_t battlelistPos)
{
	m_nBattlelistPos = battlelistPos;
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

std::string Creature::getName() const
{
	return m_sName;
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

uint32_t Creature::getBattlelistPos() const
{
	return m_nBattlelistPos;
}

uint32_t Creature::getIsWalking() const
{
	return m_bIsWalking;
}