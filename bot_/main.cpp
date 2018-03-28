#include <iostream>
#include <algorithm>

#include "ClientFinder.h"
#include "MemoryReader.h"
#include "Battlelist.h"
#include "ActionExecutor.h"
#include "Player.h"
#include "Utils.h"
#include "Injector.h"

int main(void)
{
	ClientFinder clientFinder("Tibijka.exe");

	std::vector<ProcessDesc> vClients = clientFinder.getClients();

	if (vClients.size() == 0)
		return 1;

	std::cout << vClients[0].getCharacterName() << std::endl;

	INJECTOR::inject(vClients[0].getpId(), "payload.dll");
	MemoryReader memoryReader(clientFinder.getClients()[0].getpId());

	Battlelist battleList = memoryReader.getBattleList();

	Player p = memoryReader.getPlayer();

	ActionExecutor actionExecutor(memoryReader);
	actionExecutor.say("blol xD");
	actionExecutor.mapClick(p.getPosX() + 1, p.getPosY(), p.getPosZ());

	while (1)
	{
		p = memoryReader.getPlayer();

		std::vector<Creature> creatures = memoryReader.getBattleList().getCreatures();
		std::vector<std::string> names;
		for (int i = 0; i < creatures.size(); i++) {
			names.push_back(creatures.at(i).getName());
			int distance = Utils::calcDist(p, creatures.at(i));
			std::cout << creatures.at(i).getName() << " - distance: " << distance << std::endl;
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
