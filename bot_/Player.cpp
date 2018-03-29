#include "Player.h"

#include "MemoryReader.h"
#include "Battlelist.h"

Player::Player(BattleListEntry_t* pBattleListEntry, uint32_t nBattleListPos)
	: Creature(pBattleListEntry, nBattleListPos)
{

}


Player::~Player()
{
}

