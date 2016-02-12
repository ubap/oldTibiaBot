using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Thronia
{

    class Map
    {
        const int MAP_TILE_COUNT = 2016;
        Tile[] tiles;
        int selfPos;
        int posX;
        int posY;
        int posZ;

        public Map(Byte[] data, BattleListEntry self)
        {
            posX = self.getPos_x();
            posY = self.getPos_y();
            posZ = self.getPos_z();

            tiles = new Tile[MAP_TILE_COUNT];

            for (int i = 0; i < MAP_TILE_COUNT; i++)
            {
                Byte[] subdata = new Byte[Tile.MAP_TILE_SIZE];
                Array.Copy(data, Tile.MAP_TILE_SIZE * i, subdata, 0,
                    Tile.MAP_TILE_SIZE);

                tiles[i] = new Tile(subdata, i, this);
            }

            CenterMap(self.getId());
        }

        void CenterMap(UInt32 SelfId)
        {
            selfPos = SearchCreatureId(SelfId);
        }

        public int getTileOffsetX(int tilePos)
        {
            return (tilePos - getTileOffsetZ(tilePos) * 14 * 18 - getTileOffsetY(tilePos) * 18);
        }

        public int getTileOffsetY(int tilePos)
        {
            return (tilePos - getTileOffsetZ(tilePos) * 14 * 18) / 18;
        }

        public int getTileOffsetZ(int tilePos)
        {
            return tilePos / (14 * 18);
        }

        public int getCenterOffsetX()
        {
            return getTileOffsetX(selfPos);
        }

        public int getCenterOffsetY()
        {
            return getTileOffsetY(selfPos);
        }

        public int getCenterOffsetZ()
        {
            return getTileOffsetZ(selfPos);
        }

        public Tile getTile(int offsetX, int offsetY)
        {
            int z = getCenterOffsetZ();
            int y = getCenterOffsetY();
            int x = getCenterOffsetX();

            y = (y + offsetY +14) % 14;
            x = (x + offsetX +18) % 18;

            int pos = x + (z * 14 * 18) + (y * 18);

            return tiles[pos];
        }

        public Tile getTileAbsolute(int _posX, int _posY)
        {
            return getTile(_posX - posX, _posY - posY);
        }


        int SearchCreatureId(UInt32 Id)
        {

            for(int i=0; i<MAP_TILE_COUNT; i++)
            {
                Tile tile = tiles[i];
                ObjectData[] objectsData = tile.getObjectsData();
                if (objectsData == null)
                    continue;
                foreach(ObjectData objectData in objectsData)
                {
                    if (objectData.getObjectId() == 99
                        && objectData.getData1() == Id)
                    {
                        return i;
                    }
                }
            }
            return -1;
        }

        public static int SIZE()
        {
            return MAP_TILE_COUNT * Tile.MAP_TILE_SIZE;
        }
    }

    class ObjectData
    {
        public const int OBJECT_DATA_SIZE = 12;
        int objectId;
        int data1;
        int data2;
        int slotIndex;

        public ObjectData(Byte[] data, int _slotIndex)
        {
            objectId = BitConverter.ToInt32(data, 0);
            data1 = BitConverter.ToInt32(data, 4);
            data2 = BitConverter.ToInt32(data, 8);
            slotIndex = _slotIndex;
        }
        public ObjectData(Int32 _objectId, Int32 _data1, Int32 _data2, int _slotIndex)
        {
            objectId = _objectId;
            data1 = _data1;
            data2 = _data2;
            slotIndex = _slotIndex;
        }

        public int getSlotIndex() { return slotIndex;  }
        public int getObjectId() { return objectId; }
        public int getData1() { return data1; }
        public int getData2() { return data2; }
    }

    class Tile
    {
        public const int MAP_TILE_SIZE = 172;
        public const int MAP_OBJECTS_MAX_COUNT = 13;
        int index;
        Map map;
        int stackedObjectCount;
        ObjectData[] objectsData;
        int padding1;
        int padding2;

        public Tile(Byte[] data, int _index, Map _map)
        {
            index = _index;
            map = _map;
            stackedObjectCount = BitConverter.ToInt32(data, 0);
            if (stackedObjectCount<0 || stackedObjectCount > MAP_OBJECTS_MAX_COUNT)
                throw new Exception("MAP stackedObjectCount Incorrect");
            objectsData = new ObjectData[MAP_OBJECTS_MAX_COUNT];
            for (int i = 0; i < stackedObjectCount; i++)
            {
                Byte[] subdata = new Byte[ObjectData.OBJECT_DATA_SIZE];
                Array.Copy(data, 4 + (ObjectData.OBJECT_DATA_SIZE * i), subdata, 0, 
                    ObjectData.OBJECT_DATA_SIZE);

                objectsData[i] = new ObjectData(subdata, i);
            }
            for(int i=stackedObjectCount; i<MAP_OBJECTS_MAX_COUNT; i++)
            {
                objectsData[i] = new ObjectData(0,0,0, i);
            }
        }

        public int getOffsetMemory()
        {
            return index * MAP_TILE_SIZE;
        }

        public int getOffsetX()
        {
            int offsetx = map.getTileOffsetX(index) - map.getCenterOffsetX();

            if (offsetx > 7)
                offsetx -= 18;
            if (offsetx < -7)
                offsetx += 18;

            return offsetx;
        }

        public int getOffsetY()
        {
            int offsety = map.getTileOffsetY(index) - map.getCenterOffsetY();

            if (offsety > 6)
                offsety -= 14;
            if (offsety < -6)
                offsety += 14;

            return offsety;
        }

        public ObjectData getTopItem()
        {
            if (stackedObjectCount > 0)
            {
                return objectsData[stackedObjectCount - 1];
            }
            else
            {
                return objectsData[0];
            }
        }
       
        public ObjectData[] getObjectsData()
        {
            if (objectsData != null)
                return objectsData;
            else
                return null;
        }

        public int getStackedObjectCount()
        {
            return stackedObjectCount;
        }
        // todo parse padding1, padding2
    }
}
