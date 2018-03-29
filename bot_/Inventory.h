#pragma once

#include "config.h"

class MemoryReader;

class Inventory
{
public:
	Inventory(MemoryReader& rMemoryReader);
	~Inventory() {};

	const Equipment_t& getEquipment() const { return m_oEquipment; }

private:
	Equipment_t m_oEquipment;
};