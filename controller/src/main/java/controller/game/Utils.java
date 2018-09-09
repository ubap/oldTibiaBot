package controller.game;

public final class Utils {

    private Utils() { }

    public static String stringFromNullTerminatedBytes(byte[] bytes) {
        int i;
        for (i = 0; i < bytes.length; i++) {
            if (bytes[i] == 0) {
                break;
            }
        }
        return new String(bytes, 0, i);
    }
}
