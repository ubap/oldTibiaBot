package remote;

public interface RemoteMemoryFactory {
    PipeMessage readBytes(Integer address, Integer size);

    PipeMessage readInt(Integer address);

    PipeMessage writeInt(Integer address, Integer data);

    PipeMessage writeBytes(Integer address, Integer size, byte[] data);

}
