# oldTibiaBot

Currently supports only 8.54. 

## modules
   - bot_ - C++ approach to create a bot using named pipe
   - controller - Java approach to create a bot using named pipe
   - inject_dll - The payload. Source code of the library which is injected into the process
   - injector - Standalone console application to help inject dll into a process
   - pipe_client - C++ based test suite for the payload
   
## dependencies
   - tclap - C++ library to handle command line arguments

## additional resources

   - To debug pipes you can use Microsoft software called PipeList https://docs.microsoft.com/en-us/sysinternals/downloads/pipelist.
   - ollyDbg
   - CheatEngine
