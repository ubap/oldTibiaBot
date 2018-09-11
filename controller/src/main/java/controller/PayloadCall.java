package controller;

import java.nio.ByteBuffer;

public class PayloadCall implements PipeMessageInterface {

    private ByteBuffer byteBuffer;

    public PayloadCall(ByteBuffer byteBuffer) {

    }

    @Override
    public byte[] array() {
        return new byte[0];
    }

    @Override
    public int getResponseLength() {
        // Todo: currently there is no support for return value from remote called method.
        // So the response length is fixed to 0 bytes.
        return 0;
    }
}
