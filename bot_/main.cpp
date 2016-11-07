#include <iostream>

#include "ClientFinder.h"
#include "MemoryReader.h"
#include "Battlelist.h"
#include "ActionExecutor.h"
#include "Player.h"
#include "Utils.h"
#include "Injector.h"

int main(void)
{
	ClientFinder clientFinder;

	clientFinder.getClients();

	INJECTOR::inject(clientFinder.getClients()[0].getpId(), "payload.dll");
	MemoryReader memoryReader(clientFinder.getClients()[0].getpId());

	Battlelist battleList = memoryReader.getBattleList();

	Player p = memoryReader.getPlayer();

	ActionExecutor actionExecutor(memoryReader);
	//actionExecutor.say("blol xD");
	actionExecutor.mapClick(p.getPosX() + 1, p.getPosY(), p.getPosZ());

	const Creature* nearestCreature = Utils::findNearestCreature(p, memoryReader.getBattleList().getCreatures());

	//actionExecutor.attack(nearestCreature->getId());

	return 0;
}
