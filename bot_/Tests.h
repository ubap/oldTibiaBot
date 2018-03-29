#pragma once

#include <iostream>
#include <time.h>

#include "MemoryReader.h"
#include "Container.h"
#include "Inventory.h"
#include "Utils.h"

using namespace std;

void testEquipment(MemoryReader& memoryReader) {
	while (1) {
		cout << "testEquipment" << endl << endl;

		Inventory inventory = memoryReader.getInventory();
		Equipment_t equipment = inventory.getEquipment();

		cout << "head: " << Utils::itemString(equipment.Head) << endl;

		Sleep(100);
		system("cls");
	}
}

void testBackpacks(MemoryReader& memoryReader) {
	while (1) {
		cout << "testBackpacks" << endl << endl;


		Inventory inventory = memoryReader.getInventory();
		std::vector<Container> openContainers = inventory.getOpenContainers();

		for (int i = 0; i < openContainers.size(); i++)
		{
			Container containerWindow = openContainers.at(i);
			cout << containerWindow.getName() << ", item count: " << containerWindow.getItemCount() << ", windowid: " << containerWindow.getWindowId() << endl;
			const vector<ItemEntry_t> items = containerWindow.getItems();
			for (int j = 0; j < items.size(); j++) {
				cout << Utils::itemString(items[i]) << endl;
			}
		}

		Sleep(100);
		system("cls");
	}
}

void randomWalk(MemoryReader& memoryReader) {
	ActionExecutor actionExecutor(memoryReader);
	srand(time(NULL));
	while (1) {
		cout << "randomWalk" << endl << endl;
		Player p = memoryReader.getPlayer();

		int dir = rand() % 8;
		switch (dir) {
		case 0:
			actionExecutor.mapClick(p.getPosX() + 1, p.getPosY(), p.getPosZ());
			break;
		case 1:
			actionExecutor.mapClick(p.getPosX() - 1, p.getPosY(), p.getPosZ());
			break;
		case 2:
			actionExecutor.mapClick(p.getPosX(), p.getPosY() + 1, p.getPosZ());
			break;
		case 3:
			actionExecutor.mapClick(p.getPosX(), p.getPosY() - 1, p.getPosZ());
			break;
		case 4:
			actionExecutor.mapClick(p.getPosX() + 1, p.getPosY() + 1, p.getPosZ());
			break;
		case 5:
			actionExecutor.mapClick(p.getPosX() - 1, p.getPosY() + 1, p.getPosZ());
			break;
		case 6:
			actionExecutor.mapClick(p.getPosX() + 1, p.getPosY() - 1, p.getPosZ());
			break;
		case 7:
			actionExecutor.mapClick(p.getPosX() - 1, p.getPosY() - 1, p.getPosZ());
			break;
		}

		Sleep(1000);
		system("cls");
	}
}