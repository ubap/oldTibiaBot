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
        pipeMessage.byteBuffer.putInt(3 + text.length());
        pipeMessage.byteBuffer.put((byte) 0); // CMD_SAY opcode
        pipeMessage.byteBuffer.put((byte) 1); // default channel
        pipeMessage.byteBuffer.put(text.getBytes());
        pipeMessage.byteBuffer.put((byte) 0); // null terminate
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
        pipeMessage.byteBuffer.putInt(9);
        pipeMessage.byteBuffer.put((byte) 100); // CMD_READ_MEM opcode
        pipeMessage.byteBuffer.putInt(address);
        pipeMessage.byteBuffer.putInt(size);
        pipeMessage.responseLength = size;
        return pipeMessage;
    }

    public byte[] array() {
        return Arrays.copyOf(this.byteBuffer.array(), this.byteBuffer.getInt(0) + 4);
    }

    public int getResponseLength() {
        return responseLength;
    }
}
