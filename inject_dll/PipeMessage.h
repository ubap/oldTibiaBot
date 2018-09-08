#pragma once

typedef unsigned long DWORD;

class PipeMessage
{
private:
	char* m_data;
	unsigned int m_dataLength;
	unsigned int m_pos;
	bool m_error;
public:
	PipeMessage(bool error);
	PipeMessage(char* data, unsigned int dataLength);
	~PipeMessage();

	unsigned int length() { return m_dataLength; };
	bool error() { return m_error; };

	unsigned char nextByte();
	DWORD nextDWORD();
	const char* nextText();
	const char* nextBytes(unsigned int count);
};
