#include "PipeMessage.h"

#include <Windows.h>

#include <string>

PipeMessage::PipeMessage(char* data, unsigned int dataLength)
{
	m_dataLength = dataLength;
	m_data = new char[dataLength];
	memcpy(m_data, data, dataLength);
	m_pos = 0;
	m_error = false;
}

PipeMessage::PipeMessage(bool error)
{
	m_data = nullptr;
	m_dataLength = 0;
	m_pos = 0;
	m_error = true;
}

PipeMessage::~PipeMessage()
{
	if (m_data != nullptr) {
		delete[] m_data;
	}
}

unsigned char PipeMessage::nextByte() {
	if (m_pos + 1 > m_dataLength) {
		m_error = true;
		return 0xFF;
	}

	char val = m_data[m_pos];
	m_pos++;
	return val;
}

DWORD PipeMessage::nextDWORD() {
	if (m_pos + 4 > m_dataLength) {
		m_error = true;
		return 0xFFFFFFFF;
	}

	DWORD val = *((DWORD*)(m_data + m_pos));
	m_pos += 4;
	return val;
}

const char* PipeMessage::nextText() {
	if (m_error) {
		return nullptr;
	}
	bool nullTerminated = false;
	int length = 0;
	for (int i = 0; i < 1024 && i + m_pos < m_dataLength; i++) {
		if (m_data[m_pos + i] == 0) {
			nullTerminated = true;
			length = i;
		}
	}
	if (!nullTerminated) {
		m_error = true;
		return nullptr;
	}

	char *val = m_data + m_pos;
	m_pos += length + 1;
	return val;
}

const char* PipeMessage::nextBytes(unsigned int count) {
	if (m_error) {
		return nullptr;
	}
	if ((unsigned int)(m_pos + count) > (unsigned int)(m_data + m_dataLength)) {
		m_error = true;
		return nullptr;
	}
	char *val = m_data + m_pos;
	m_pos += count;
	return val;
}