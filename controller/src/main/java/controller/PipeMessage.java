package controller;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class PipeMessage {

    private ByteBuffer byteBuffer;
    // defines if this message should have answer, and what size, in bytes
    private int responseLength;

    private PipeMessage() {
        this.byteBuffer = ByteBuffer.allocate(1024);
        this.byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
    }

    public static PipeMessage say(String text) {
        PipeMessage pipeMessage = new PipeMessage();
        pipeMessage.byteBuffer.putInt(1); // size placeholder
        pipeMessage.byteBuffer.putInt(1);   // opcode
        pipeMessage.byteBuffer.putInt(0x00407310); // addr
        pipeMessage.byteBuffer.putInt(2); // arg count

        // mode
        pipeMessage.byteBuffer.putInt(1); // arg type
        pipeMessage.byteBuffer.putInt(1); // arg

        // text
        pipeMessage.byteBuffer.putInt(2); // arg type
        pipeMessage.byteBuffer.putInt(text.length() + 1); // arg length
        pipeMessage.byteBuffer.put(text.getBytes());
        pipeMessage.byteBuffer.put((byte) 0);

        pipeMessage.byteBuffer.putInt(0,
                pipeMessage.byteBuffer.position() - 4);

        pipeMessage.responseLength = 0;
        return pipeMessage;
    }

    public static PipeMessage attack(Integer creatureId) {
        PipeMessage pipeMessage = new PipeMessage();
        pipeMessage.byteBuffer.putInt(1); // size placeholder
        pipeMessage.byteBuffer.putInt(1);   // opcode
        pipeMessage.byteBuffer.putInt(0x00408E40); // addr
        pipeMessage.byteBuffer.putInt(1); // arg count

        // cid
        pipeMessage.byteBuffer.putInt(1); // arg type
        pipeMessage.byteBuffer.putInt(creatureId); // arg

        pipeMessage.byteBuffer.putInt(0,
                pipeMessage.byteBuffer.position() - 4);

        pipeMessage.responseLength = 0;
        return pipeMessage;
    }

    public static PipeMessage writeInt(Integer address, Integer val) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(val);
        return writeMemory(address, 4, byteBuffer.array());
    }

    public static PipeMessage writeMemory(Integer address, Integer size, byte[] data) {
        PipeMessage pipeMessage = new PipeMessage();
        pipeMessage.byteBuffer.putInt(12 + size);
        pipeMessage.byteBuffer.putInt(3);//  CMD_WRITE_MEM
        pipeMessage.byteBuffer.putInt(address);
        pipeMessage.byteBuffer.putInt(size);
        pipeMessage.byteBuffer.put(data, 0, size);
        pipeMessage.responseLength = 0;
        return pipeMessage;
    }

    /**
     * Create Pipe Message for read Memory opcode.
     *
     * @param address 32 bit unsigned
     * @param size 32 bit unsigned
     * @return
     */
    public static PipeMessage readMemory(Integer address, Integer size) {
        PipeMessage pipeMessage = new PipeMessage();
        pipeMessage.byteBuffer.putInt(12);
        pipeMessage.byteBuffer.putInt(2); // CMD_READ_MEM opcode
        pipeMessage.byteBuffer.putInt(address);
        pipeMessage.byteBuffer.putInt(size);
        pipeMessage.responseLength = size;
        return pipeMessage;
    }

    public static PipeMessage call() {
        PipeMessage pipeMessage = new PipeMessage();
        pipeMessage.byteBuffer.putInt(1); // size placeholder
        pipeMessage.byteBuffer.putInt(1);   // opcode
        pipeMessage.byteBuffer.putInt(0x00407310); // addr
        pipeMessage.byteBuffer.putInt(2); // arg count

        // mode
        pipeMessage.byteBuffer.putInt(1); // arg type
        pipeMessage.byteBuffer.putInt(1); // arg

        // text
        pipeMessage.byteBuffer.putInt(2); // arg type
        String text = "hahah";
        pipeMessage.byteBuffer.putInt(text.length() + 1); // arg length
        pipeMessage.byteBuffer.put(text.getBytes());
        pipeMessage.byteBuffer.put((byte) 0);

        pipeMessage.byteBuffer.putInt(0,
                pipeMessage.byteBuffer.position() - 4);

        pipeMessage.responseLength = 0;
        return pipeMessage;
    }

    public byte[] array() {
        return Arrays.copyOf(this.byteBuffer.array(), this.byteBuffer.getInt(0) + 4);
    }

    public int getResponseLength() {
        return responseLength;
    }
}
