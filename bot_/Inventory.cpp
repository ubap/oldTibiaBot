#include "Inventory.h"

#include "Container.h"
#include "MemoryReader.h"

Inventory::Inventory(MemoryReader& rMemoryReader)
{
	rMemoryReader.readStructure(ADDR::INVENTORY_BEGIN, sizeof(Inventory_t), &m_oInventory);
}

std::vector<Container> Inventory::getOpenContainers()
{
	std::vector<Container> openContainers;
	for (int i = 0; i < CONSTS::MAX_CONTAINER_WINDOWS; i++) {
		BackpackWindow_t backpackWindow = m_oInventory.BackpackWindows[i];
		if (backpackWindow.Open) {
			Container container(backpackWindow, i);
			openContainers.push_back(container);
		}
	}

	return openContainers;
}