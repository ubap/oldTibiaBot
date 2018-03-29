#include "Container.h"

Container::Container(BackpackWindow_t& backpackWindow, uint32_t nWindowId)
{
	m_nWindowId = nWindowId;
	m_sName = std::string((char*)backpackWindow.WindowName);
	m_nItemCount = backpackWindow.ItemCount;
	for (int i = 0; i < backpackWindow.ItemCount; i++) {
		m_vItems.push_back(backpackWindow.BackpackItem[i]);
	}
}