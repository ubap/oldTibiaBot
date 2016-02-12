# oldTibiaBot
Used for thronia.org

inject dump_packets_c.dll into Thronia.exe<br \>
it exports some functions of Thronia via local UDP server on port 8888.

Controller written in C#
Reads/writes data from Thronia exe using ReadProcessMemory/WriteProcessMemory.
Sends commands to thronia on localhost:8888


It's a mess!

Good luck.
