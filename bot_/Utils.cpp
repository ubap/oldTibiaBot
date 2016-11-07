#include "Utils.h"

#include <math.h>

#include "Player.h"
#include "Creature.h"

Utils::Utils()
{
}


Utils::~Utils()
{
}

uint32_t Utils::calcDist(const Player p, const Creature c)
{
	uint32_t offX = p.getPosX() - c.getPosX();
	uint32_t offY = p.getPosY() - c.getPosY();

	uint32_t distance = sqrt(offX*offX + offY*offY);

	return distance;
}

const Creature* Utils::findNearestCreature(const Player& p, const std::vector<Creature>& creatures)
{
	uint32_t minDistance = 999;
	const Creature* nearestCreature = nullptr;
	for (auto it = creatures.begin(); it != creatures.end(); it++)
	{
		uint32_t distance = Utils::calcDist(p, *it);
		if (distance < minDistance && it->getId() != p.getId() && it->getPosZ() == p.getPosZ())
		{
			minDistance = distance;
			nearestCreature = &(*it);
		}
	}

	return nearestCreature;
}