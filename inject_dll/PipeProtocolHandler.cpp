#include "PipeProtocolHandler.h"

#include <Windows.h>


PipeProtocolHandler::PipeProtocolHandler(char* pipeName, unsigned int bufferSize)
{
	// try to create the pipe for ever
	m_pipe = NULL;
	while (m_pipe == NULL || m_pipe = INVALID_HANDLE_VALUE)
	{
		// Create a pipe to send data
		m_pipe = CreateNamedPipe(
			pipeName, // name of the pipe
			PIPE_ACCESS_DUPLEX, // 2-way pipe
			PIPE_TYPE_MESSAGE, // send data as a byte stream
			1, // only allow 1 instance of this pipe
			0, // no outbound buffer
			0, // no inbound buffer
			0, // use default wait time
			NULL // use default security attributes
		);

		if (m_pipe == NULL || m_pipe = INVALID_HANDLE_VALUE)
		{
			// prvent eating whole CPU thread
			Sleep(100);
		}
	}

	m_buffer = new char[bufferSize];
}


/**
	The destructor will most likely not be called because the payload is designed to sit in the process for ever.
**/
PipeProtocolHandler::~PipeProtocolHandler()
{
	// close the pipe if valid
	if (m_pipe != NULL && m_pipe != INVALID_HANDLE_VALUE)
	{
		CloseHandle(m_pipe);
	}

	delete[] m_buffer;
}
