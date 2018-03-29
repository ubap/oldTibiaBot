#pragma once

#include "config.h"

#include <string>
#include <vector>

class Container
{
public:
	Container(BackpackWindow_t&, uint32_t nWindowId);

	uint32_t getWindowId() const { return m_nWindowId; }
	const std::string& getName() const { return m_sName; }
	uint32_t getItemCount() const { return m_nItemCount; }
	const std::vector<ItemEntry_t>& getItems() const { return m_vItems; }

private:
	uint32_t m_nWindowId;
	std::string m_sName;
	uint32_t m_nItemCount;
	std::vector<ItemEntry_t> m_vItems;
};