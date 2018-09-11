package controller;

import lombok.Getter;

import java.nio.ByteBuffer;

public class Argument {



    private static Integer INT32 = 1;
    private static Integer BYTEARRAY = 2;

    @Getter
    private Integer type;
    @Getter
    private Object value;
    @Getter
    private Integer size;

    private Argument() { }

    public static Argument int32(Integer value) {
        Argument argument = new Argument();
        argument.type = INT32;
        argument.value = value;
        argument.size = 8;
        return argument;
    }

//    public static Argument byteArray(byte[] bytes) {
//        Argument argument = new Argument();
//        argument.type = BYTEARRAY;
//        argument.value = Arrays.copyOf(bytes, bytes.length);
//        argument.size
//        return argument;
//    }

    public static Argument text(String text) {
        Argument argument = new Argument();
        argument.type = BYTEARRAY;
        argument.value = text;
        // type, strlen, text, nullterminate
        argument.size = 4 + 4 + text.length() + 1;
        return argument;
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
