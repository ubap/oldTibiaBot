#pragma once

#include "config.h"

#include "Creature.h"

class MemoryReader;

class Player : public Creature
{
public:
	Player(BattleListEntry_t* pBattleListEntry, uint32_t nBattleListPos);
	~Player();


private:

};

