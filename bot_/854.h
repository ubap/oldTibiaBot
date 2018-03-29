#pragma once

#include <stdint.h>

namespace ADDR
{
	static const uint32_t BATTLELIST_BEGIN	= 0x00635F70;
	static const uint32_t SELF_ID			= 0x00635F10;
	static const uint32_t LOGGED_IN			= 0x72846050; // relative to wsock.dll
	static const uint32_t GOTO_X			= 0x00635F54;
	static const uint32_t GOTO_Y			= 0x00635F50;
	static const uint32_t GOTO_Z			= 0x00635F4C;

	static const uint32_t HP				= 0x00635F0C;
	static const uint32_t MP				= 0x00635EF0;
	static const uint32_t CAP				= 0x00635EE0;

	static const uint32_t INVENTORY_BEGIN   = 0x00642BC8;
}

namespace CONSTS
{
	enum LOGGED_IN { NO = 1, YES = 2};
	static const uint32_t BATTLELIST_SIZE = 250;
	static const uint32_t BATTELIST_WALKING_OFFESET = 76;
}

#pragma pack(push)
#pragma pack(1)
struct BattleListEntry_t
{
	uint32_t id;			// 0
	char name[32];			// 4
	uint32_t posx;			// 36
	uint32_t posy;			// 40
	uint32_t posz;			// 44
	uint32_t screenx;		// 48
	uint32_t screeny;		// 52
	uint8_t unknowData1[20]; // 56
	uint32_t walking;		// 76
	uint32_t Direction;		// 80
	uint8_t UnknowData2[12];// 92
	uint32_t Outfit;	// 96
	uint32_t HeadColor;	// 100
	uint32_t BodyColor; // 104
	uint32_t LegsColor; // 108
	uint32_t FeetColor; // 112
	uint32_t Addons; // 116
	uint32_t Light; // 120
	uint32_t LightColor; // 124
	uint32_t UnknowData3; // 128
	uint32_t BlackSquare; // 132
	uint32_t HPBar; // 136
	uint32_t Speed; // 140
	uint32_t Visible; // 144

	uint32_t Skull; // 148
	uint32_t Party; // 152
	uint32_t UnknowData4; // 156
	uint8_t UnknowData6[4]; // 160
	uint32_t UnknowData7; // 163
	// 163
};
#pragma pack(pop)
static_assert(sizeof(BattleListEntry_t) == 168, "Bad battlelist structure size, the structure is probably corrupted");

#pragma pack(push)
#pragma pack(1)
struct ItemEntry_t
{
	uint32_t id;
	uint32_t extraData1;
	uint32_t extraData2;
};
#pragma pack(pop)
static_assert(sizeof(ItemEntry_t) == 12, "error item entry");

#pragma pack(push)
#pragma pack(1)
struct Equipment_t
{
	ItemEntry_t slot[10];
};
#pragma pack(pop)
static_assert(sizeof(Equipment_t) == 120, "error inventory");

