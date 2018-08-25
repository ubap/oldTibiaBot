#pragma once
class PipeProtocolHandler
{
private:
	HANDLE m_pipe;
	char* m_buffer;
public:
	PipeProtocolHandler(char* pipeName, unsigned int bufferSize = 1024);
	~PipeProtocolHandler();
};

