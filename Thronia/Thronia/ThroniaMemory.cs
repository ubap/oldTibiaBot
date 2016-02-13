using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Runtime.InteropServices;
using System.Diagnostics;
using System.ComponentModel;

namespace Thronia
{
    class ThroniaMemory
    {
        const int PROCESS_ALL_ACCESS = 0x1F0FFF;
        IntPtr processHandle;
        
        const int BATTLELIST_START = 0x005C68B0;
        const int MAP_GOTO_Z = 0x005C6888;
        const int MAP_GOTO_Y = 0x005C688C;
        const int MAP_GOTO_X = 0x005C6890;
        const int MAP_POINTER = 0x005D4C20;
        const int STATUS_MESSAGE_TIME = 0x0071DBDC;
        const int STATUS_MESSAGE = 0x0071DBE0;
        const int EQUIPMENT_START = 0x005CED60;
        const int MANA_POINTER = 0x44b979;
        const int SELF_ID_PTR_ADDR = 0x0044d6d1;
        const int IS_ONLINE = 0x0071C588;

        [DllImport("kernel32.dll")]
        static extern IntPtr OpenProcess(int dwDesiredAccess, bool bInheritHandle, int dwProcessId);

        [DllImport("kernel32.dll")]
        static extern bool ReadProcessMemory(int hProcess,
          int lpBaseAddress, byte[] lpBuffer, int dwSize, ref int lpNumberOfBytesRead);

        [DllImport("kernel32.dll")]
        static extern bool WriteProcessMemory(int hProcess,
          int lpBaseAddress, byte[] lpBuffer, int dwSize, ref int lpNumberOfBytesWritten);

        public ThroniaMemory(int pId)
        {
            //Inject(pId);
            processHandle = OpenProcess(PROCESS_ALL_ACCESS, false, pId);
        }

        

        public String getCharacterName()
        {
            int isOnline = ReadInt32(IS_ONLINE);
            if (isOnline == 8)
            {
                return getSelf().getName();
            }
            return "Not logged in.";
        }



        public void revealFish()
        {
            Map map = getMap();
            for (int x = -7; x <= 7; x++)
            {
                for (int y = -5; y <= 5; y++)
                {
                    Tile tile = map.getTile(x, y);
                    if (tile.getTopItem().getObjectId() == 490)
                    {
                        int address = getMapAddress() + tile.getOffsetMemory();
                        WriteInt32(101, address + 4);
                    }

                }
            }
        }

        public void SetStatus(int time, String message)
        {
            WriteString(message, STATUS_MESSAGE);
            WriteInt32(time, STATUS_MESSAGE_TIME);
        }

        public void MapClick(int x, int y, int z)
        {
            WriteInt32(x, MAP_GOTO_X);
            WriteInt32(y, MAP_GOTO_Y);
            WriteInt32(z, MAP_GOTO_Z);

            int address = BATTLELIST_START + getSelf().getOffsetMemory();
            WriteByte(1, address + BattleListEntry.WALK_OFFSET);
        }

        public int getMana()
        {
            return ReadInt32(ReadInt32(MANA_POINTER));
        }

        public BattleListEntry getSelf()
        {
            Battlelist battlelist = getBattlelist();
            return battlelist.getCreature(getSelfId());
        }

        public UInt32 getSelfId()
        {
            int selfIdAddress = ReadInt32(SELF_ID_PTR_ADDR);
            return ReadUInt32(selfIdAddress);
        }

        public Map getMap()
        {
            Byte[] data = ReadBytes(getMapAddress(), Map.SIZE());
            Map map = new Map(data, getSelf());
            return map;
        }

        public int getMapAddress()
        {
            return ReadInt32(MAP_POINTER);
        }

        public Inventory getInventory()
        {
            Byte[] data = ReadBytes(EQUIPMENT_START, Inventory.SIZE());
            Inventory map = new Inventory(data);
            return map;
        }

        public Equipment getEquipment()
        {
            Byte[] data = ReadBytes(EQUIPMENT_START, Equipment.SIZE());
            Equipment map = new Equipment(data);
            return map;
        }

        public void setCreatureLight(BattleListEntry creature, byte level, byte color)
        {
            int address = ThroniaMemory.BATTLELIST_START + creature.getOffsetMemory();
            WriteByte(level, address + BattleListEntry.LIGHT_OFFSET);
            WriteByte(color, address + BattleListEntry.LIGHT_COLOR_OFFSET);
        }

        void WriteString(String val, Int32 address)
        {
            Byte[] buffer = System.Text.Encoding.ASCII.GetBytes(val + "\0");
            WriteBytes(buffer, address);
        }

        void WriteInt32(Int32 val, Int32 address)
        {
            Byte[] buffer = BitConverter.GetBytes(val);
            WriteBytes(buffer, address);
        }

        void WriteByte(Byte val, Int32 address)
        {
            Byte[] buffer = new Byte[1];
            buffer[0] = val;
            WriteBytes(buffer, address);
        }

        void WriteBytes(Byte[] data, Int32 address)
        {
            int bytesWritten = 0;
            WriteProcessMemory((int)processHandle, address, data, data.Length, ref bytesWritten);
            if (bytesWritten != data.Length)
            {
                throw new Exception("Could not write data to process");
            }
        }

        Int32 ReadInt32(Int32 address)
        {
            byte[] buffer = ReadBytes(address, 4);

            return BitConverter.ToInt32(buffer, 0);
        }

        UInt32 ReadUInt32(Int32 address)
        {
            byte[] buffer = ReadBytes(address, 4);

            return BitConverter.ToUInt32(buffer, 0);
        }

        Byte[] ReadBytes(int addr, int count)
        {
            int bytesRead = 0;
            byte[] buffer = new byte[count];

            ReadProcessMemory((int)processHandle, addr, buffer, buffer.Length, ref bytesRead);
            return buffer;
        }

        public Battlelist getBattlelist()
        {
            byte[] buffer = ReadBytes(BATTLELIST_START, 
                Battlelist.BATTLE_LIST_ENTRY_SIZE*Battlelist.BATTLE_LIST_SIZE);

            return new Battlelist(buffer);
        }
    }
}
