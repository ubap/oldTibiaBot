using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;

namespace Thronia
{
    public class ThroniaBot
    {
        ThroniaMemory throniaMemory;
        ThroniaSender throniaSender;
        ThroniaController throniaController;

        Thread fullLightThread;
        Thread fishingThread;
        Thread eatFoodThread;

        public bool FullLight
        {
            set
            {
                if (value == true)
                    startFullLight();
                else
                    stopFullLight();
            }
        }

        public bool Fish
        {
            set
            {
                if (value == true)
                    startAutoFish();
                else
                    stopAutoFish();
            }
        }

        public bool EatFood
        {
            set
            {
                if (value == true)
                    startAutoEatFood();
                else
                    stopAutoEatFood();
            }
        }

        public ThroniaBot(int pId)
        {
            throniaMemory = new ThroniaMemory(pId);
            throniaSender = new ThroniaSender(pId);
            throniaController = new ThroniaController(throniaMemory, throniaSender);
        }

        public void startFullLight()
        {
            fullLightThread = new Thread(AutoFullLight);
            fullLightThread.Start((object)(int)10);
        }

        public void stopFullLight()
        {
            if (fullLightThread != null)
            {
                fullLightThread.Abort();
            }
        }

        public void startAutoFish()
        {
            fishingThread = new Thread(AutoFish);
            fishingThread.Start((object)(int)2000);
        }

        void stopAutoFish()
        {
            if (fishingThread != null)
            {
                fishingThread.Abort();
            }
        }

        public void startAutoEatFood()
        {
            eatFoodThread = new Thread(new ThreadStart(this.AutoEatFood));
            eatFoodThread.Start();

        }

        public void stopAutoEatFood()
        {
            if (eatFoodThread != null)
            {
                eatFoodThread.Abort();
            }
        }

        public void AutoEatFood()
        {
            while (true)
            {
                throniaController.EatFood();
                Thread.Sleep(15000);
            }
        }

        void AutoFullLight(object frequency)
        {
            int freq = (int)frequency;
            while (true)
            {
                throniaController.setFullLight();
                Thread.Sleep(freq);
            }
        }

        void AutoFish(object frequency)
        {
            int freq = (int)frequency;
            while (true)
            {
                throniaController.Fish();
                Thread.Sleep(freq);
            }
        }

        public void stopAll()
        {
            //if (fishingThread != null)
            //{
            //    fishingThread.Abort();
            //}
            if (fullLightThread != null)
            {
                fullLightThread.Abort();
            }
            //if (autoEatFoodThread != null)
            //{
            //    autoEatFoodThread.Abort();
            //}
        }


    }
}
