using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Net.Sockets;
using System.IO.Pipes;

namespace Thronia
{
    class ThroniaSender
    {
        NamedPipeClientStream pipe;

        public ThroniaSender(int pId)
        {
            pipe = new NamedPipeClientStream(".", "thronia" + pId.ToString(), PipeDirection.Out);
            pipe.Connect(2000);
        }

        public void Say(String message)
        {
            SendMessage("say(1," + message + ")");
        }

        public void UseItemInContainer(int containerIndex, int slotIndex, int itemId, int dstContainerIndex)
        {
            UseItem(0xFFFF, containerIndex+0x40, slotIndex, itemId, slotIndex, containerIndex);
        }

        public void UseEqItemWithOnGround(int slot, int itemId, int dstX, int dstY, int dstZ, int dstId, int dstStackPos)
        {
            UseWith(0xffff, slot+1, 0, itemId, 0, dstX, dstY, dstZ, dstId, dstStackPos);
        }

        public void UseContainerItemWithOnGround(int containerIndex, int slot, int itemId, int dstX, int dstY, int dstZ, 
            int dstId, int dstStackPos)
        {
            UseWith(0xffff, containerIndex + 0x40, slot, itemId, slot, dstX, dstY, dstZ, dstId, dstStackPos);
        }

        public void UseWith(int flag1, int srcContainer, int srcSlot, int useId, 
            int srcslot2, int flag2, int dstContainer, int dstSlot, int dstId, int dstStackPos)
        {
            SendMessage("usewith(" + flag1.ToString() + "," + srcContainer.ToString() + "," 
                + srcSlot.ToString() + "," + useId.ToString() + "," + srcslot2.ToString() + "," + flag2.ToString() 
                + "," + dstContainer.ToString() + "," + dstSlot.ToString() 
                + "," + dstId.ToString() + "," + dstStackPos.ToString() + ")" );
        }


        public void UseItem(int flag1, int src, int slotSrc, int itemId, int dstFlag, int dstbpid)
        {
            SendMessage("useitem(" + flag1.ToString() + "," + src.ToString() + "," + slotSrc.ToString() +","+
                itemId.ToString() + "," + slotSrc.ToString() + "," + dstbpid.ToString() + ")");
        }

        void SendMessage(String data)
        {      
            Byte[] sendBytes = Encoding.ASCII.GetBytes(data);
            pipe.Write(sendBytes, 0, sendBytes.Length);
        }
    }
}
