package controller.game;

import remote.PipeMessage;
import remote.RemoteMemory;
import remote.RemoteMemoryFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class RemoteMemoryFactoryImpl implements RemoteMemoryFactory {

    @Override
    public PipeMessage writeBytes(Integer address, Integer size, byte[] data) {
        // 4 byte size, 4 byte opcode, 4 byte address, 4 byte size
        ByteBuffer byteBuffer = ByteBuffer.allocate(16 + size);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        byteBuffer.putInt(12 + size);
        byteBuffer.putInt(3);//  CMD_WRITE_MEM
        byteBuffer.putInt(address);
        byteBuffer.putInt(size);
        byteBuffer.put(data, 0, size);

        return new RemoteMemory(byteBuffer, 0);
    }

    /**
     * Write 32bit int.
     * @param address address to write to.
     * @param data Integer data to write.
     */
    @Override
    public PipeMessage writeInt(Integer address, Integer data) {
        // 4 byte size, 4 byte opcode, 4 byte address, 4 byte size
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        byteBuffer.putInt(16);
        byteBuffer.putInt(3);//  CMD_WRITE_MEM
        byteBuffer.putInt(address);
        byteBuffer.putInt(4);
        byteBuffer.putInt(data);

        return new RemoteMemory(byteBuffer, 0);
    }

    @Override
    public PipeMessage readBytes(Integer address, Integer size) {
        // 4 byte size, 4 byte opcode, 4 byte address, 4 byte size
        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        byteBuffer.putInt(12);
        byteBuffer.putInt(2); // CMD_READ_MEM opcode
        byteBuffer.putInt(address);
        byteBuffer.putInt(size);

        return new RemoteMemory(byteBuffer, size);
    }

    @Override
    public PipeMessage readInt(Integer address) {
        return readBytes(address, 4);
    }


}
