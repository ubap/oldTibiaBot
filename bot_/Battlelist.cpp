#include "Battlelist.h"



Battlelist::Battlelist(BattleListEntry_t* pBattleList)
{
	init(pBattleList);
}


Battlelist::~Battlelist()
{
}

void Battlelist::init(BattleListEntry_t* pBattleList)
{
	for (int i = 0; i < CONSTS::BATTLELIST_SIZE; i++)
	{
		if (pBattleList[i].Visible)
		{
			Creature creature(&pBattleList[i], i);
			m_vCreatures.push_back(creature);
		}

	}
}

const Creature* Battlelist::getCreature(uint32_t creatureId)
{
	for (auto it = m_vCreatures.begin(); it != m_vCreatures.end(); it++)
	{
		if (it->getId() == creatureId)
			return &(*it);
	}
	return NULL;
}