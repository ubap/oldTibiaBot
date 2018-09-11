package remote;

import controller.Argument;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class RemoteMethod implements PipeMessage {

    private ByteBuffer byteBuffer;

    public RemoteMethod(Builder payloadCallBuilder) {
        List<Argument> argumentList = payloadCallBuilder.argumentList;
        int sizeTotal = 16; // 4 byte header, 4 byte opcode, 4 byte address, 4 byte argCount
        for (Argument argument : argumentList) {
            sizeTotal += argument.getSize();
        }
        this.byteBuffer = ByteBuffer.allocate(sizeTotal);
        this.byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        this.byteBuffer.putInt(sizeTotal - 4); // without the header
        this.byteBuffer.putInt(1);   // opcode
        this.byteBuffer.putInt(payloadCallBuilder.address);

        this.byteBuffer.putInt(argumentList.size());

        for (Argument argument : argumentList) {
            argument.writeBytes(this.byteBuffer);
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
        private List<Argument> argumentList;

        public Builder() {
            Builder.this.argumentList = new ArrayList<>();
        }

        public Builder setMethodAddress(Integer address) {
            this.address = address;
            return Builder.this;
        }

        public Builder addArgument(Argument argument) {
            this.argumentList.add(argument);
            return Builder.this;
        }

        public RemoteMethod build() {
            return new RemoteMethod(Builder.this);
        }

    }

}
