#pragma once

#include "config.h"

#include <stdint.h>
#include <vector>

class Player;
class Creature;

class Utils
{
public:
	Utils();
	~Utils();

	static uint32_t calcDist(const Player, const Creature);
	static const Creature* findNearestCreature(const Player& p, const std::vector<Creature>& creatures);

	static std::string itemString(const ItemEntry_t& itemEntry);
};

