using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Diagnostics;

namespace Thronia
{
    class ThroniaInjector
    {
        public static void Inject(int pId)
        {
            String LibPath = "C:\\rev\\inject.dll";
            Process firstProc = new Process();
            firstProc.StartInfo.FileName = "injector.exe";
            firstProc.StartInfo.Arguments = pId.ToString() + " " + LibPath;
            firstProc.EnableRaisingEvents = true;

            firstProc.Start();
            firstProc.WaitForExit();
            int result = firstProc.ExitCode;

            if (result != 0)
                throw new Exception("Could not inject dll. Error=" + result.ToString());
        }


        public static ThroniaClientDescription[] getClients()
        {
            Process[] localByName = Process.GetProcessesByName("Thronia");
            int processCount = localByName.Length;
            ThroniaMemory[] throniaMemory = new ThroniaMemory[processCount];
            List<ThroniaClientDescription> results = new List<ThroniaClientDescription>();
            for (int i = 0; i < processCount; i++)
            {
                int pId = localByName[i].Id;
                throniaMemory[i] = new ThroniaMemory(pId);
                ThroniaMemory t = throniaMemory[i];

                ThroniaClientDescription proc = new ThroniaClientDescription(pId, t.getCharacterName());
                results.Add(proc);
            }

            return results.ToArray();
        }
    }

        class ThroniaClientDescription
        {
            int pId;
            String Name;

            public ThroniaClientDescription(int _pId, String _Name)
            {
                pId = _pId;
                Name = _Name;
            }

            public int getpId() { return pId; }
            public String getName() { return Name; }
        }

   
}
