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

        public ThroniaBot(int pId)
        {
            throniaMemory = new ThroniaMemory(pId);
            throniaSender = new ThroniaSender();
            throniaController = new ThroniaController(throniaMemory, throniaSender);

        }

        public void startFullLight()
        {
            fullLightThread = new Thread(new ThreadStart(this.AutoFullLight));
            fullLightThread.Start();

        }

        public void stopFullLight()
        {
            if (fullLightThread != null)
            {
                fullLightThread.Abort();
            }
        }

        public void AutoFullLight()
        {
            while (true)
            {
                throniaController.setFullLight();
                Thread.Sleep(1);
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
