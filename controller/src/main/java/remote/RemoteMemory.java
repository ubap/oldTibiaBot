package remote;

import java.io.IOException;
import java.nio.ByteBuffer;

public class RemoteMemory implements PipeMessage {
    private ByteBuffer byteBuffer;
    private int responseLength;

    public RemoteMemory(ByteBuffer byteBuffer, int responseLength) {
        this.byteBuffer = byteBuffer;
        this.responseLength = responseLength;
    }

    @Override
    public PipeResponse execute(Pipe pipe) throws IOException {
        return pipe.send(this.byteBuffer.array(), getResponseLength());
    }

    @Override
    public int getResponseLength() {
        return this.responseLength;
    }
}
