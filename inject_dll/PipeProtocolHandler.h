#pragma once

class PipeMessage;
typedef void* HANDLE;

class PipeProtocolHandler
{
private:
	HANDLE m_pipe;
	char* m_buffer;
public:
	PipeProtocolHandler(char* pipeName, unsigned int bufferSize = 1024);
	~PipeProtocolHandler();

	bool connect();
	PipeMessage readMessage();
	bool sendData(char* src, unsigned int size);
};

