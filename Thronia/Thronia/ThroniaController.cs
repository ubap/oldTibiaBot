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
        
        Thread autoEatFoodThread;

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
            if (autoEatFoodThread != null)
            {
                autoEatFoodThread.Abort();
            }
        }

        public void setFullLight()
        {
            BattleListEntry self = throniaMemory.getSelf();
            throniaMemory.setCreatureLight(self, 16, 0xD7);
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



        public void startAutoEatFood()
        {
            autoEatFoodThread = new Thread(new ThreadStart(this.AutoEatFood));
            autoEatFoodThread.Start();

        }

        public void stopAutoEatFood()
        {
            if (autoEatFoodThread != null)
            {
                autoEatFoodThread.Abort();
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

                    // this order because we dont want to hide "You can not throw there"
                    throniaMemory.SetStatus(20, "Using fishing rod. Tiles with fishes left: " + tiles_fish.Count.ToString() + ".");
                    if (!FindAndUseItemOnGround(2580, fish_x, fish_y))
                    {
                        throniaMemory.SetStatus(20, "Fishing rod not found.");
                    }

                }
                else
                {
                    throniaMemory.SetStatus(20, "No tiles with fish found.");
                }
                Thread.Sleep(1000);
            }
        }

        public void AutoEatFood()
        {
            while (true)
            {
                EatFood();
                Thread.Sleep(15000);
            }
        }

        public bool EatFood()
        {
            // todo: add food list and unpack it
            // 2667 - fish
            int containerIndex = 0;
            int slotIndex = 0;
            if (FindItemInContainers(2667, ref containerIndex, ref slotIndex))
            {
                UseContainerItem(containerIndex, slotIndex);
                return true;
            }
            return false;
        }

        public void BurnMana()
        {
            if (throniaMemory.getMana() > 300)
            {
                throniaSender.Say("exura");
            }
        }

        public bool FindItemInInventory(int itemid, ref int slotIndex)
        {
            Inventory inv = throniaMemory.getInventory();
            for (int i = 0; i < Inventory.SLOT_COUNT; i++)
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
            Container[] openContainers = throniaMemory.getEquipment().getOpenContainers();
            foreach (Container c in openContainers)
            {
                foreach (ObjectData o in c.getItems())
                {
                    if (o.getObjectId() == itemId)
                    {
                        containerIndex = c.getIndex();
                        slotIndex = o.getSlotIndex();
                        return true;
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
            int dstStackPos = throniaMemory.getMap().getTileAbsolute(posX, posY).getStackedObjectCount() - 1;
            throniaSender.UseEqItemWithOnGround(slot, itemId, posX, posY, posZ, dstId, dstStackPos);
        }

        public void UseContainerItem(int containerIndex, int slotIndex)
        {
            Container container = throniaMemory.getEquipment().getContainers()[containerIndex];
            int itemId = container.getItems()[slotIndex].getObjectId();
            throniaSender.UseItemInContainer(containerIndex, slotIndex, itemId, containerIndex);
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
