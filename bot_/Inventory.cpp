#include "Inventory.h"

#include "MemoryReader.h"

Inventory::Inventory(MemoryReader& rMemoryReader)
{
	rMemoryReader.readStructure(ADDR::INVENTORY_BEGIN, sizeof(Equipment_t), &m_oEquipment);
}
