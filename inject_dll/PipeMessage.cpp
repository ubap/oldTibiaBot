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

std::string PipeMessage::nextString() {
	if (m_error) {
		return std::string("error");
	}
	bool nullTerminated = false;
	for (int i = 0; i < 1024 && i + m_pos < m_dataLength; i++) {
		if (m_data[m_pos + i] == 0) {
			nullTerminated = true;
		}
	}
	if (!nullTerminated) {
		m_error = true;
		return std::string("error");
	}

	std::string val = std::string(m_data + m_pos);
	m_pos += val.length() + 1;
	return val;
}