#pragma once

#include "config.h"

#include "Creature.h"

class MemoryReader;

class Player : public Creature
{
public:
	Player(MemoryReader&, BattleListEntry_t* pBattleListEntry, uint32_t nBattleListPos);
	~Player();

	uint32_t getHp() const { return m_nHp; }
	uint32_t getMp() const { return m_nMp; }
	uint32_t getCap() const { return m_nCap; }

private:
	uint32_t m_nHp;
	uint32_t m_nMp;
	uint32_t m_nCap;
};

