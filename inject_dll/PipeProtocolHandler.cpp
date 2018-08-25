#include "PipeProtocolHandler.h"

#include <Windows.h>

#include "PipeMessage.h"


PipeProtocolHandler::PipeProtocolHandler(char* pipeName, unsigned int bufferSize) {
	
	m_pipe = NULL;
	while (m_pipe == NULL || m_pipe == INVALID_HANDLE_VALUE) {
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

		if (m_pipe == NULL || m_pipe == INVALID_HANDLE_VALUE) {
			Sleep(200);
		}
	}

	m_buffer = new char[bufferSize];
}


/**
	The destructor will most likely not be called because the payload is designed to sit in the process for ever.
**/
PipeProtocolHandler::~PipeProtocolHandler() {
	// close the pipe if valid
	CloseHandle(m_pipe);
	delete[] m_buffer;
}

bool PipeProtocolHandler::connect() {
	return ConnectNamedPipe(m_pipe, NULL);
}

PipeMessage PipeProtocolHandler::readMessage() {
	DWORD totalNumBytesRead = 0, numBytesRead = 0, payloadLength = 0;
	BOOL result;
	// read header first
	result = ReadFile(
		m_pipe,
		&payloadLength, // the data from the pipe will be put here
		4, // number of bytes allocated
		&numBytesRead, // this will store number of bytes actually read
		NULL // not using overlapped IO
	);
	if (!result || numBytesRead != 4) {
		return PipeMessage(true);
	}

	numBytesRead = 0;
	while (totalNumBytesRead != payloadLength) {
		result = ReadFile(
			m_pipe,
			m_buffer + totalNumBytesRead, // the data from the pipe will be put here
			payloadLength - totalNumBytesRead, // number of bytes to read
			&numBytesRead, // this will store number of bytes actually read
			NULL // not using overlapped IO
		);
		if (!result) {
			return PipeMessage(true);
		}
		totalNumBytesRead += numBytesRead;
	}

	// at this point we should have all the message in m_buffer field
	return PipeMessage(m_buffer, payloadLength);
}

bool PipeProtocolHandler::sendData(char* src, unsigned int size) {
	DWORD totalBytesWritten = 0, bytesWritten = 0;
	while (totalBytesWritten != size) {
		BOOL result = WriteFile(
			m_pipe, // pipe handle 
			src + totalBytesWritten, // message 
			size - totalBytesWritten,              // message length 
			&bytesWritten,             // bytes written 
			NULL);
		if (!result)
			return false;
		totalBytesWritten += bytesWritten;
	}
	return true;
}