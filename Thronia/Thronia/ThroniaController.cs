using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;

namespace Thronia
{

    class ThroniaController
    {
        ThroniaMemory throniaMemory;
        ThroniaSender throniaSender;
        Thread fishingThread;
        Thread fullLightThread;

        public ThroniaController(ThroniaMemory _throniaMemory, ThroniaSender _throniaSender)
        {
            throniaMemory = _throniaMemory;
            throniaSender = _throniaSender;
        }

        // abort all threads here
        public void stopAll()
        {
            if (fishingThread != null)
            {
                fishingThread.Abort();
            }
            if (fullLightThread != null)
            {
                fullLightThread.Abort();
            }
        }

        public void FullLight()
        {
            while (true)
            {
                BattleListEntry self = throniaMemory.getSelf();
                throniaMemory.setCreatureLight(self, 16, 0xD7);
                Thread.Sleep(100);
            }
        }

        public void startAutoFish()
        {
            fishingThread = new Thread(new ThreadStart(this.AutoFish));
            fishingThread.Start();

        }

        public void stopAutoFish()
        {
            if (fishingThread != null)
            {
                fishingThread.Abort();
            }
        }

        public void startFullLight()
        {
            fullLightThread = new Thread(new ThreadStart(this.FullLight));
            fullLightThread.Start();

        }

        public void stopFullLight()
        {
            if (fullLightThread != null)
            {
                fullLightThread.Abort();
            }
        }

        public void AutoFish()
        {
            Random random = new Random();
            while (true)
            {
                List<Tile> tiles_fish = new List<Tile>();
                Map map = throniaMemory.getMap();
                for (int x = -7; x <= 7; x++)
                {
                    for (int y = -5; y <= 5; y++)
                    {
                        Tile tile = map.getTile(x, y);
                        if (tile.getTopItem().getObjectId() == 490)
                        {
                            tiles_fish.Add(tile);
                        }

                    }
                }
                
  
                if (tiles_fish.Count > 0)
                {
                    int tile_index = random.Next() % tiles_fish.Count;
                    BattleListEntry self = throniaMemory.getSelf();
                    int fish_x = (int)self.getPos_x() + tiles_fish[tile_index].getOffsetX();
                    int fish_y = (int)self.getPos_y() + tiles_fish[tile_index].getOffsetY();
                    int fish_z = (int)self.getPos_z();
                    throniaMemory.SetStatus(20, "Using fishing rod. Tiles with fishes left: " + tiles_fish.Count.ToString() + ".");
                    //throniaSender.UseWith(65535, 10, 0, 2580, 0, fish_x, fish_y, fish_z, 490, 0);
                    FindAndUseItemOnGround(2580, fish_x, fish_y);
                }
                else
                {
                    throniaMemory.SetStatus(20, "No tiles with fish found.");
                }
                Thread.Sleep(1000);
            }
        }

        public bool FindItemInInventory(int itemid, ref int slotIndex)
        {
            Inventory inv = throniaMemory.getInventory();
            for(int i=0; i<Inventory.SLOT_COUNT; i++)
            {
                if (inv.getObjects()[i].getObjectId() == itemid)
                {
                    slotIndex = i;
                    return true;
                }
            }
            return false;
        }

        public bool FindItemInContainers(int itemId, ref int containerIndex, ref int slotIndex)
        {
            Equipment eq = throniaMemory.getEquipment();
            for (int j = 0; j < Equipment.MAX_OPENED_CONTAINERS; j++)
            {
                Container c = eq.getContainers()[j];
                if (c.isOpen())
                {
                    for (int i = 0; i < c.getItemCount(); i++)
                    {
                        if (c.getItems()[i].getObjectId() == itemId)
                        {
                            containerIndex = j;
                            slotIndex = i;
                            return true;
                        }
                    }
                }
            }
            return false;
        }


        public void UseEqItemOnGround(int slot, int posX, int posY)
        {
            Inventory inv = throniaMemory.getInventory();
            int itemId = inv.getObjects()[slot].getObjectId();
            int posZ = throniaMemory.getSelf().getPos_z();
            int dstId = throniaMemory.getMap().getTileAbsolute(posX, posY).getTopItem().getObjectId();
            int dstStackPos = throniaMemory.getMap().getTileAbsolute(posX, posY).getStackedObjectCount() -1;
            throniaSender.UseEqItemWithOnGround(slot, itemId, posX, posY, posZ, dstId, dstStackPos);
        }

        public void UseContainerItemOnGround(int containerIndex, int slotIndex, int posX, int posY)
        {
            Container container = throniaMemory.getEquipment().getContainers()[containerIndex];
            int itemId = container.getItems()[slotIndex].getObjectId();
            int posZ = throniaMemory.getSelf().getPos_z();
            int dstId = throniaMemory.getMap().getTileAbsolute(posX, posY).getTopItem().getObjectId();
            int dstStackPos = throniaMemory.getMap().getTileAbsolute(posX, posY).getStackedObjectCount() - 1;
            throniaSender.UseContainerItemWithOnGround(containerIndex, slotIndex, itemId, posX, posY, posZ, dstId, dstStackPos);
        }

        public bool FindAndUseItemOnGround(int itemId, int posX, int posY)
        {
            int containerIndex = 0;
            int slotIndex = 0;
            if (FindItemInInventory(itemId, ref slotIndex))
            {
                UseEqItemOnGround(slotIndex, posX, posY);
                return true;
            }
            else if (FindItemInContainers(itemId, ref containerIndex, ref slotIndex))
            {
                UseContainerItemOnGround(containerIndex, slotIndex, posX, posY);
                return true;
            }
            return false;
        }
    }
}
