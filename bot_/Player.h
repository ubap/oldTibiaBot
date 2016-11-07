#pragma once

#include "config.h"

class MemoryReader;

class Player
{
public:
	Player(MemoryReader& memoryReader);
	~Player();

	uint32_t getId() const;
	uint32_t getPosX() const;
	uint32_t getPosY() const;
	uint32_t getPosZ() const;
	bool getIsWalking() const;
private:
	uint32_t m_nId;
	uint32_t m_nPosX;
	uint32_t m_nPosY;
	uint32_t m_nPosZ;
	bool m_bIsWalking;

};

