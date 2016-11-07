#include "ActionExecutor.h"

#include "MemoryReader.h"
#include "BattleList.h"
#include "Creature.h"

ActionExecutor::ActionExecutor(MemoryReader& memoryReader)
{
	m_pRemoryReader = &memoryReader;
	uint32_t pId = memoryReader.getPId();
	std::string m_sPipeName = "\\\\.\\pipe\\thronia";
	m_sPipeName += std::to_string((uint32_t)pId);

	bool fSuccess = false;
	SYSTEMTIME start, curr;
	GetSystemTime(&start);
	do {
		GetSystemTime(&curr);

		m_hPipe = CreateFile(
			TEXT(m_sPipeName.c_str()),   // pipe name 
			GENERIC_WRITE,
			0,              // no sharing 
			NULL,           // default security attributes
			OPEN_EXISTING,  // opens existing pipe 
			0,              // default attributes 
			NULL);          // no template file 

		if (m_hPipe == INVALID_HANDLE_VALUE)
		{

			// Exit if an error other than ERROR_PIPE_BUSY occurs. 
			if (GetLastError() != ERROR_PIPE_BUSY)
				continue;

			// All pipe instances are busy, so wait for 20 seconds. 

			if (!WaitNamedPipe(TEXT(m_sPipeName.c_str()), 20000))
				continue;
		}

		DWORD dwMode = PIPE_READMODE_MESSAGE;
		fSuccess = SetNamedPipeHandleState(
			m_hPipe,    // pipe handle 
			&dwMode,  // new pipe mode 
			NULL,     // don't set maximum bytes 
			NULL);    // don't set maximum time 
		if (fSuccess)
			break;
	} while (curr.wMilliseconds - start.wMilliseconds < 5000);
	if (!fSuccess)
		throw;
}


ActionExecutor::~ActionExecutor()
{
}

void ActionExecutor::pipe_send(char* data, uint32_t size)
{
	LPTSTR lpvMessage = TEXT(data);
	DWORD cbWritten;
	bool fSuccess = WriteFile(
		m_hPipe,                  // pipe handle 
		lpvMessage,             // message 
		size,              // message length 
		&cbWritten,             // bytes written 
		NULL);

	if (cbWritten != size || !fSuccess)
		throw;
}

void ActionExecutor::say(std::string message)
{
	char data[256];
	data[0] = 0;
	data[1] = 1;
	memcpy(&data[2], message.c_str(), message.size());
	pipe_send(data, 2 + message.size());
}


void ActionExecutor::attack(uint32_t creatureId)
{
	char data[5];
	data[0] = 1;
	memcpy(&data[1], &creatureId, 4);
	pipe_send(data, 5);
}

void ActionExecutor::mapClick(uint32_t posX, uint32_t posY, uint32_t posZ)
{
	const Creature* c = m_pRemoryReader->getBattleList().getCreature(m_pRemoryReader->getSelfId());
	if (!c)
		throw;
	
	m_pRemoryReader->writeData(ADDR::GOTO_X, (char*)&posX, 4);
	m_pRemoryReader->writeData(ADDR::GOTO_Y, (char*)&posY, 4);
	m_pRemoryReader->writeData(ADDR::GOTO_Z, (char*)&posZ, 4);

	uint32_t battleListOffset = c->getBattlelistPos() * sizeof(BattleListEntry_t) + CONSTS::BATTELIST_WALKING_OFFESET;
	uint32_t data = 1;
	m_pRemoryReader->writeData(ADDR::BATTLELIST_BEGIN + battleListOffset, (char*)&data, 4);
}