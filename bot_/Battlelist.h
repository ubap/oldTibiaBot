#pragma once

#include <vector>

#include "config.h"

#include "Creature.h"
class Battlelist
{
	friend class MemoryReader;
public:
	Battlelist(BattleListEntry_t*);
	~Battlelist();

	const std::vector<Creature>& getCreatures() { return m_vCreatures; };
	const Creature* getCreature(uint32_t creatureId);

private:
	std::vector<Creature> m_vCreatures;

	void init(BattleListEntry_t*);
};

