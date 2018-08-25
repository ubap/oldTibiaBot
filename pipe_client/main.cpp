#include <windows.h> 
#include <stdio.h>
#include <conio.h>
#include <tchar.h>

// https://msdn.microsoft.com/pl-pl/library/windows/desktop/aa365592(v=vs.85).aspx

#define BUFSIZE 512

char command_1[] = { 0, 1, 'k', 'o', 'k', 's' };
int  command_1_len = sizeof(command_1);
char command_2[] = { 0, 1, 'k', 'o', 'k', 's', '\0' };
int  command_2_len = sizeof(command_2);
char command_3[] = { 0, 2, 'k', 'o', 'k', 's' };
int  command_3_len = sizeof(command_3);
char command_4[] = { 0, 2, 'k', 'o', 'k', 's', '\0' };
int  command_4_len = sizeof(command_4);
char command_5[] = { 0, 3, 'k', 'o', 'k', 's' };
int  command_5_len = sizeof(command_5);
char command_6[] = { 0, 3, 'k', 'o', 'k', 's', '\0' };
int  command_6_len = sizeof(command_6);

char* commands[]  =    { command_1    , command_2    , command_3    , command_4    , command_5    , command_6    };
int command_sizes[]  = { command_1_len, command_2_len, command_3_len, command_4_len, command_5_len, command_6_len};

void read_bytes_from_pipe(HANDLE pipe, int count, char* buffer, int bufferSize)
{
	DWORD totalNumBytesRead = 0;

	while (totalNumBytesRead < count)
	{
		DWORD fSuccess, numBytesRead = 0;
		fSuccess = ReadFile(
			pipe,
			buffer + totalNumBytesRead, // the data from the pipe will be put here
			bufferSize, // number of bytes allocated
			&numBytesRead, // this will store number of bytes actually read
			NULL // not using overlapped IO
		);
		totalNumBytesRead += numBytesRead;

		if (!fSuccess)
		{
			_tprintf(TEXT("ReadFile to pipe failed. GLE=%d\n"), GetLastError());
			return;
		}
	}

}

void test_get_player_id(HANDLE pipe)
{
	_tprintf(TEXT("Executing test_get_player_id: "));
	// given
	char COMMAND[] = { 100 };
	DWORD LENGTH = sizeof(COMMAND) + 8;
	// when
	DWORD fSuccess, cbWritten;
	fSuccess = WriteFile(
		pipe, // pipe handle 
		(LPTSTR)&LENGTH, // message 
		4,              // message length 
		&cbWritten,             // bytes written 
		NULL);

	fSuccess = WriteFile(
		pipe, // pipe handle 
		(LPTSTR)COMMAND, // message 
		sizeof(COMMAND),              // message length 
		&cbWritten,             // bytes written 
		NULL);

	DWORD SELF_ID_ADDRESS = 0x00635F10;
	DWORD SELF_ID_SIZE = 4;

	fSuccess = WriteFile(
		pipe, // pipe handle 
		(LPTSTR)&SELF_ID_ADDRESS, // message 
		4,              // message length 
		&cbWritten,             // bytes written 
		NULL);

	fSuccess = WriteFile(
		pipe, // pipe handle 
		(LPTSTR)&SELF_ID_SIZE, // message 
		4,              // message length 
		&cbWritten,             // bytes written 
		NULL);

	char buffer[BUFSIZE];
	// The read operation will block until there is data to read
	DWORD numBytesRead = 0;
	read_bytes_from_pipe(pipe, 4, buffer, BUFSIZE);

	printf("0x%08x\n", *((unsigned int*) buffer));
}

void test_wsock_addr(HANDLE pipe)
{
	_tprintf(TEXT("Executing test_wsock_addr: "));
	// given
	char COMMAND[] = { 101 };
	DWORD LENGTH = sizeof(COMMAND);
	// when
	DWORD fSuccess, cbWritten;
	fSuccess = WriteFile(
		pipe, // pipe handle 
		(LPTSTR)&LENGTH, // message 
		4,              // message length 
		&cbWritten,             // bytes written 
		NULL);

	fSuccess = WriteFile(
		pipe, // pipe handle 
		(LPTSTR)COMMAND, // message 
		sizeof(COMMAND),              // message length 
		&cbWritten,             // bytes written 
		NULL);

	char buffer[BUFSIZE];
	// The read operation will block until there is data to read
	DWORD numBytesRead = 0;
	read_bytes_from_pipe(pipe, 4, buffer, BUFSIZE);

	printf("0x%08x\n", *((unsigned int*)buffer));
}

int _tmain(int argc, TCHAR *argv[])
{
	if (argc != 2)
	{
		printf("Usage: pipe");
		return 1;
	}

	HANDLE hPipe;
	TCHAR  chBuf[BUFSIZE];
	BOOL   fSuccess = FALSE;
	DWORD  cbRead, cbToWrite, cbWritten, dwMode;
	char pipename[1024];
	sprintf_s(pipename, "\\\\.\\pipe\\%s", argv[1]);
	LPTSTR lpszPipename = TEXT(pipename);

	printf("connecting to pipe %s\n", lpszPipename);

	// Try to open a named pipe; wait for it, if necessary. 

	while (1)
	{
		hPipe = CreateFile(
			lpszPipename,   // pipe name 
			GENERIC_READ | GENERIC_WRITE,
			0,              // no sharing 
			NULL,           // default security attributes
			OPEN_EXISTING,  // opens existing pipe 
			0,              // default attributes 
			NULL);          // no template file 

							// Break if the pipe handle is valid. 

		if (hPipe != INVALID_HANDLE_VALUE)
			break;

		// Exit if an error other than ERROR_PIPE_BUSY occurs. 

		if (GetLastError() != ERROR_PIPE_BUSY)
		{
			_tprintf(TEXT("Could not open pipe. GLE=%d\n"), GetLastError());
			return -1;
		}

		// All pipe instances are busy, so wait for 20 seconds. 

		if (!WaitNamedPipe(lpszPipename, 20000))
		{
			printf("Could not open pipe: 20 second wait timed out.");
			return -1;
		}
	}

	// The pipe connected; change to message-read mode. 

	dwMode = PIPE_READMODE_MESSAGE;
	fSuccess = SetNamedPipeHandleState(
		hPipe,    // pipe handle 
		&dwMode,  // new pipe mode 
		NULL,     // don't set maximum bytes 
		NULL);    // don't set maximum time 
	if (!fSuccess)
	{
		_tprintf(TEXT("SetNamedPipeHandleState failed. GLE=%d\n"), GetLastError());
		return -1;
	}

	// Send a message to the pipe server. 
	//for (int i = 0; i < sizeof(commands) / sizeof(char*); i++)
	//{
	//	_tprintf(TEXT("Executing command %d\n"), i+1);
	//	LPTSTR lpvMessage = commands[i];
	//	cbToWrite = command_sizes[i];

	//	fSuccess = WriteFile(
	//		hPipe,                  // pipe handle 
	//		lpvMessage,             // message 
	//		cbToWrite,              // message length 
	//		&cbWritten,             // bytes written 
	//		NULL);

	//	Sleep(2000);
	//}

	if (!fSuccess)
	{
		_tprintf(TEXT("WriteFile to pipe failed. GLE=%d\n"), GetLastError());
		return -1;
	}

	// separate test suites
	test_get_player_id(hPipe);
	test_wsock_addr(hPipe);

	printf("\nMessage sent to server");

	CloseHandle(hPipe);

	return 0;
}

