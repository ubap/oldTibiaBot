package remote;

import lombok.Getter;

import java.nio.ByteBuffer;

public class RemoteMethodArgument {
    private static Integer INT32 = 1;
    private static Integer BYTEARRAY = 2;

    @Getter
    private Integer type;
    @Getter
    private Object value;
    @Getter
    private Integer size;

    private RemoteMethodArgument() { }

    public static RemoteMethodArgument int32(Integer value) {
        RemoteMethodArgument remoteMethodArgument = new RemoteMethodArgument();
        remoteMethodArgument.type = INT32;
        remoteMethodArgument.value = value;
        remoteMethodArgument.size = 8;
        return remoteMethodArgument;
    }

    public static RemoteMethodArgument text(String text) {
        RemoteMethodArgument remoteMethodArgument = new RemoteMethodArgument();
        remoteMethodArgument.type = BYTEARRAY;
        remoteMethodArgument.value = text;
        // type, strlen, text, nullterminate
        remoteMethodArgument.size = 4 + 4 + text.length() + 1;
        return remoteMethodArgument;
    }

    public void writeBytes(ByteBuffer byteBuffer) {
        byteBuffer.putInt(this.type);
        if (this.type.equals(BYTEARRAY)) {
            String text = (String) this.value;
            byteBuffer.putInt(text.length());
            byteBuffer.put(text.getBytes());
            byteBuffer.put((byte) 0);
        } else if (this.type.equals(INT32)) {
            byteBuffer.putInt((Integer) this.value);
        }
    }


}
