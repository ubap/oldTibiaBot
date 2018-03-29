#include "Inventory.h"

#include "MemoryReader.h"

Inventory::Inventory(MemoryReader& rMemoryReader)
{
	rMemoryReader.readStructure(ADDR::INVENTORY_BEGIN, sizeof(Inventory_t), &m_oInventory);
}

std::vector<BackpackWindow_t> Inventory::getOpenBackpacks()
{
	std::vector<BackpackWindow_t> openBackpacks;
	for (int i = 0; i < CONSTS::MAX_CONTAINER_WINDOWS; i++) {
		BackpackWindow_t backpackWindow = m_oInventory.BackpackWindows[i];
		if (backpackWindow.Open)
			openBackpacks.push_back(backpackWindow);
	}

	return openBackpacks;
}