package remote;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class RemoteCall implements PipeMessage {

    private ByteBuffer byteBuffer;

    public RemoteCall(Builder payloadCallBuilder) {
        List<RemoteMethodArgument> remoteMethodArgumentList = payloadCallBuilder.remoteMethodArgumentList;
        int sizeTotal = 16; // 4 byte header, 4 byte opcode, 4 byte address, 4 byte argCount
        for (RemoteMethodArgument remoteMethodArgument : remoteMethodArgumentList) {
            sizeTotal += remoteMethodArgument.getSize();
        }
        this.byteBuffer = ByteBuffer.allocate(sizeTotal);
        this.byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        this.byteBuffer.putInt(sizeTotal - 4); // without the header
        this.byteBuffer.putInt(1);   // opcode
        this.byteBuffer.putInt(payloadCallBuilder.address);

        this.byteBuffer.putInt(remoteMethodArgumentList.size());

        for (RemoteMethodArgument remoteMethodArgument : remoteMethodArgumentList) {
            remoteMethodArgument.writeBytes(this.byteBuffer);
        }
    }

    @Override
    public PipeResponse execute(Pipe pipe) throws IOException {
        return pipe.send(this.byteBuffer.array(), getResponseLength());
    }

    @Override
    public int getResponseLength() {
        // Todo: currently there is no support for return value from remote called method.
        // So the response length is fixed to 0 bytes.
        return 0;
    }

    public static class Builder {
        private Integer address;
        private List<RemoteMethodArgument> remoteMethodArgumentList;

        public Builder() {
            Builder.this.remoteMethodArgumentList = new ArrayList<>();
        }

        public Builder setMethodAddress(Integer address) {
            this.address = address;
            return Builder.this;
        }

        public Builder addArgument(RemoteMethodArgument remoteMethodArgument) {
            this.remoteMethodArgumentList.add(remoteMethodArgument);
            return Builder.this;
        }

        public RemoteCall build() {
            return new RemoteCall(Builder.this);
        }

    }

}
