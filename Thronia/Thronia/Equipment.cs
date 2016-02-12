using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Thronia
{
    class Equipment
    {
        public const int MAX_OPENED_CONTAINERS = 20;
        Inventory inventory;
        Container[] containers;
        int openContainersCount;
        public Equipment(Byte[] data)
        {
            Byte[] subdata = new Byte[Inventory.SIZE()];
            Array.Copy(data, 0, subdata, 0, Inventory.SIZE());
            inventory = new Inventory(subdata);

            containers = new Container[MAX_OPENED_CONTAINERS];

            for (int i = 0; i < MAX_OPENED_CONTAINERS; i++)
            {
                subdata = new Byte[Container.SIZE];
                Array.Copy(data, Inventory.SIZE() + (i * Container.SIZE), subdata, 0, Container.SIZE);
                containers[i] = new Container(subdata, i);
            }

            countOpenContainers();
        }

        public Container[] getContainers()
        {
            return containers;
        }

        public Container[] getOpenContainers()
        {
            Container[] openContainers = new Container[openContainersCount];
            int i = 0;
            foreach(Container c in containers)
            {
                if (c.isOpen())
                {
                    openContainers[i] = c;
                    i++;
                }
            }
            return openContainers;
        }

        int countOpenContainers()
        {
            int counter = 0;
            foreach (Container c in containers)
            {
                if (c.isOpen())
                {
                    counter++;
                }
            }

            openContainersCount = counter;
            return counter;
        }

        public static int SIZE()
        {
            return Inventory.SIZE() + Container.SIZE * MAX_OPENED_CONTAINERS;
        }
    }

    class Inventory
    {
        public const int SLOT_COUNT = 10;
        ObjectData[] objects;
        public Inventory(Byte[] data)
        {
            objects = new ObjectData[SLOT_COUNT];
            for (int i = 0; i < SLOT_COUNT; i++)
            {
                Byte[] subdata = new Byte[ObjectData.OBJECT_DATA_SIZE];
                Array.Copy(data, ObjectData.OBJECT_DATA_SIZE * i, subdata, 0,
                    ObjectData.OBJECT_DATA_SIZE);

                objects[i] = new ObjectData(subdata);
            }

        }

        public ObjectData[] getObjects()
        {
            return objects;
        }


        static public int SIZE()
        {
            return SLOT_COUNT * ObjectData.OBJECT_DATA_SIZE;
        }

    }

    class Container
    {
        public const int SIZE = 492;
        int index;
        UInt32 open;
        ObjectData container;
        String Name;
        int itemCount;
        ObjectData[] items;

        const int CONTAINER_OBJECT_DATA_OFFSET = 4;
        const int CONTAINER_NAME_OFFSET = 16;
        const int CONTAINER_UNKNOWN_OFFSET = 48;
        const int CONTAINER_CAN_GO_UP_OFFSET = 52;
        const int CONTAINER_ITEM_COUNT_OFFSET = 56;
        const int CONTAINER_ITEMS_START = 60;

        public Container(Byte[] data, int _index)
        {
            index = _index;
            open = BitConverter.ToUInt32(data, 0);
            Byte[] subdata = new Byte[ObjectData.OBJECT_DATA_SIZE];
            Array.Copy(data, CONTAINER_OBJECT_DATA_OFFSET, subdata, 0,
                ObjectData.OBJECT_DATA_SIZE);
            container = new ObjectData(subdata);
            Name = Encoding.ASCII.GetString(data, CONTAINER_NAME_OFFSET, 32);
            itemCount = BitConverter.ToInt32(data, CONTAINER_ITEM_COUNT_OFFSET);
            items = new ObjectData[itemCount];
            for (int i = 0; i < itemCount; i++)
            {
                subdata = new Byte[ObjectData.OBJECT_DATA_SIZE];
                Array.Copy(data, CONTAINER_ITEMS_START + i * ObjectData.OBJECT_DATA_SIZE, subdata, 0,
                    ObjectData.OBJECT_DATA_SIZE);
                items[i] = new ObjectData(subdata);
            }
        }

        public int getIndex()
        {
            return index;
        }

        public bool isOpen()
        {
            if (open == 1)
            {
                return true;
            }
            if (open != 0)
            {
                throw new Exception("Backpack isOpen flag bad value.");
            }

            return false;
        
        }


        public ObjectData[] getItems()
        {
            return items;
        }

        public int getItemCount()
        {
            return itemCount;
        }
    }
}
