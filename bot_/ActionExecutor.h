#pragma once
#include <Windows.h>

#include <string>

class MemoryReader;

class ActionExecutor
{
public:
	ActionExecutor(MemoryReader&);
	~ActionExecutor();

	void say(std::string message);
	void attack(uint32_t creatureId);
	void mapClick(uint32_t posX, uint32_t posY, uint32_t posZ);
private:
	HANDLE m_hPipe;
	void pipe_send(char* data, uint32_t size);

	MemoryReader* m_pRemoryReader;
};


