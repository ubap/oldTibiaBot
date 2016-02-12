using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Thronia
{
    class Battlelist
    {
        public const int BATTLE_LIST_SIZE = 150;
        public const int BATTLE_LIST_ENTRY_SIZE = 156;
        BattleListEntry[] battleListEntry;

        public Battlelist(Byte[] data)
        {
            battleListEntry = new BattleListEntry[BATTLE_LIST_SIZE];
            for(int i=0; i<BATTLE_LIST_SIZE; i++)
            {
                Byte[] subdata = new Byte[BATTLE_LIST_ENTRY_SIZE];
                Array.Copy(data, BATTLE_LIST_ENTRY_SIZE*i, subdata, 0,
                     BATTLE_LIST_ENTRY_SIZE);

                battleListEntry[i] = new BattleListEntry(subdata, i);
            }
        }

        public Int32 CreatureIdOffset(UInt32 id)
        {
            foreach (BattleListEntry e in battleListEntry)
            {
                if (e.getId() == id)
                {
                    int index = e.getIndex();
                    return index * BATTLE_LIST_ENTRY_SIZE;
                }
            }
            return -1;
        }

        public BattleListEntry getCreature(UInt32 id)
        {
            foreach (BattleListEntry e in battleListEntry)
            {
                if (e.getId() == id)
                {
                    return e;
                }
            }
            return null;
        }

    }

    class BattleListEntry
    {
        int index;
        UInt32 Id;
        String Name;
        Byte LightLevel;
        Byte LightColor;
        int Pos_x;
        int Pos_y;
        int Pos_z;
        public const int NAME_OFFSET = 4;
        public const int POS_X_OFFSET = 36;
        public const int POS_Y_OFFSET = 40;
        public const int POS_Z_OFFSET = 44;
        public const int WALK_OFFSET = 76;
        public const int FACING_OFFSET = 80;
        public const int LIGHT_OFFSET = 116;
        public const int LIGHT_COLOR_OFFSET = 120;





        
        public BattleListEntry(Byte[] data, int _index)
        {
            index = _index;
            Id = BitConverter.ToUInt32(data, 0);
            Name = Encoding.ASCII.GetString(data, NAME_OFFSET, 32);
            Pos_x = BitConverter.ToInt32(data, POS_X_OFFSET);
            Pos_y = BitConverter.ToInt32(data, POS_Y_OFFSET);
            Pos_z = BitConverter.ToInt32(data, POS_Z_OFFSET);
            LightLevel = data[LIGHT_OFFSET];
            LightColor = data[LIGHT_COLOR_OFFSET];
        }

        public int getIndex() { return index; }
        public int getOffsetMemory() { return index * Battlelist.BATTLE_LIST_ENTRY_SIZE;  }
        public UInt32 getId() { return Id; }
        public String getName() { return Name; }
        public int getPos_x() { return Pos_x; }
        public int getPos_y() { return Pos_y; }
        public int getPos_z() { return Pos_z; }

    }
}
