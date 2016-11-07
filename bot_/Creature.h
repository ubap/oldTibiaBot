#pragma once

#include <string>

#include "config.h"

class Creature
{
public:
	Creature(BattleListEntry_t*, uint32_t battlelistPos);
	~Creature();

	uint32_t getBattlelistPos() const;
	uint32_t getId() const;
	std::string getName() const;
	uint32_t getPosX() const;
	uint32_t getPosY() const;
	uint32_t getPosZ() const;
	uint32_t getIsWalking() const;

private:
	uint32_t m_nBattlelistPos;
	uint32_t m_nCreatureId;
	std::string m_sName;
	uint32_t m_nHPpc;
	uint32_t m_nPosX;
	uint32_t m_nPosY;
	uint32_t m_nPosZ;
	uint32_t m_bIsWalking;
};

