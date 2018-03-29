#pragma once

#include "config.h"

#include <vector>

class Container;
class MemoryReader;

class Inventory
{
public:
	Inventory(MemoryReader& rMemoryReader);
	~Inventory() {};

	const Equipment_t& getEquipment() const { return m_oInventory.Equipment; }
	std::vector<Container> getOpenContainers();

private:
	Inventory_t m_oInventory;
};