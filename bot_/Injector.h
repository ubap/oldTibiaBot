#pragma once

#include <stdint.h>
#include <Windows.h>

#include <string>

namespace INJECTOR
{ 
	uint32_t inject(DWORD pId, const std::string dllPath);
}