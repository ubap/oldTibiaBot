#include "Player.h"

#include "MemoryReader.h"
#include "Battlelist.h"

Player::Player(MemoryReader& memoryReader)
{
	uint32_t selfId = memoryReader.getSelfId();
	const Creature* self = memoryReader.getBattleList().getCreature(selfId);
	if (self == NULL)
		throw;

	m_nId = self->getId();
	m_nPosX = self->getPosX();
	m_nPosY = self->getPosY();
	m_nPosZ = self->getPosZ();
	m_bIsWalking = self->getIsWalking();
}


Player::~Player()
{
}

uint32_t Player::getId() const
{
	return m_nId;
}

uint32_t Player::getPosX() const
{
	return m_nPosX;
}

uint32_t Player::getPosY() const
{
	return m_nPosY;
}

uint32_t Player::getPosZ() const
{
	return m_nPosZ;
}

bool Player::getIsWalking() const
{
	return m_bIsWalking;
}