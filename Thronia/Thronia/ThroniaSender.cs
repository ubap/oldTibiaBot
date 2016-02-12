using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Sockets;

namespace Thronia
{
    class ThroniaSender
    {
        public void Say(String message)
        {
            SendMessage("say(1," + message + ")");
        }

        public void UseEqItemWithOnGround(int slot, int itemId, int dstX, int dstY, int dstZ, int dstId, int dstStackPos)
        {
            UseWith(0xffff, slot+1, 0, itemId, 0, dstX, dstY, dstZ, dstId, dstStackPos);
        }

        public void UseContainerItemWithOnGround(int containerIndex, int slot, int itemId, int dstX, int dstY, int dstZ, int dstId, int dstStackPos)
        {
            UseWith(0xffff, containerIndex + 0x40, slot, itemId, slot, dstX, dstY, dstZ, dstId, dstStackPos);
        }

        public void UseWith(int flag1, int srcContainer, int srcSlot, int useId, 
            int srcslot2, int flag2, int dstContainer, int dstSlot, int dstId, int dstStackPos)
        {
            SendMessage("usewith(" + flag1.ToString() + "," + srcContainer.ToString() + "," + srcSlot.ToString() +
                "," + useId.ToString() + "," + srcslot2.ToString() + "," + flag2.ToString() + "," + dstContainer.ToString() + "," +
                dstSlot.ToString() + "," + dstId.ToString() + "," + dstStackPos.ToString() + ")" );
        }

        void SendMessage(String data)
        {
            UdpClient udpClient = new UdpClient(8887);
            udpClient.Connect("localhost", 8888);

            // Sends a message to the host to which you have connected.
            Byte[] sendBytes = Encoding.ASCII.GetBytes(data);
            udpClient.Send(sendBytes, sendBytes.Length);
            udpClient.Close();

        }
    }
}
