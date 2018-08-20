#include <iostream>
#include <algorithm>

#include "ClientFinder.h"
#include "MemoryReader.h"
#include "Battlelist.h"
#include "ActionExecutor.h"
#include "Player.h"
#include "Inventory.h"
#include "Utils.h"
#include "Injector.h"

#include "Tests.h";

int main(void)
{
	ClientFinder clientFinder("Kasteria.exe");

	std::vector<ProcessDesc> vClients = clientFinder.getClients();

	if (vClients.size() == 0)
		return 1;

	std::cout << vClients[0].getCharacterName() << std::endl;

	INJECTOR::inject(vClients[0].getpId(), "payload.dll");
	MemoryReader memoryReader(clientFinder.getClients()[0].getpId());

	// randomWalk(memoryReader);
	//testEquipment(memoryReader);
	// testBackpacks(memoryReader);


	Battlelist battleList = memoryReader.getBattleList();

	Player p = memoryReader.getPlayer();

	ActionExecutor actionExecutor(memoryReader);
	actionExecutor.say("blol xD");
	actionExecutor.mapClick(p.getPosX() + 1, p.getPosY(), p.getPosZ());

	while (1)
	{
		Inventory inventory = memoryReader.getInventory();

		p = memoryReader.getPlayer();
		std::cout << "Self HP: " << p.getHp() << ", self MP: " << p.getMp() << ", cap: " << p.getCap() << std::endl << std::endl;

		std::vector<Creature> creatures = memoryReader.getBattleList().getCreatures();
		std::vector<std::string> names;
		for (int i = 0; i < creatures.size(); i++) {
			Creature creature = creatures.at(i);
			names.push_back(creature.getName());
			int distance = Utils::calcDist(p, creature);
			std::cout << creature.getName() << " - distance: " << distance << ", hppc: " << creature.getHPpc() << std::endl;
		}


		// check if there are any duplicates
		auto it = std::unique(names.begin(), names.end());
		std::cout << ((it == names.end()) ? "Unique\n" : "Duplicate(s)\n");

		//const Creature* nearestCreature = Utils::findNearestCreature(p, creatures);
		//actionExecutor.attack(nearestCreature->getId());

		Sleep(50);
		system("cls");
	}

	system("pause");
	return 0;
}
