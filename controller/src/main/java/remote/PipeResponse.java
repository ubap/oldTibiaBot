package remote;

import java.nio.ByteBuffer;

/**
 * Hold the result and response of a pipe sent message.
 */
public class PipeResponse {
    private boolean error;
    private ByteBuffer data;

    private static PipeResponse RESPONSE_NO_DATA;
    static {
        RESPONSE_NO_DATA = new PipeResponse();
        RESPONSE_NO_DATA.error = false;
        RESPONSE_NO_DATA.data = null;
    }

    private PipeResponse() {
    }

    public static PipeResponse error() {
        // todo: error codes
        PipeResponse pipeResponse = new PipeResponse();
        pipeResponse.error = true;
        return pipeResponse;
    }

    public static PipeResponse responseWithData(ByteBuffer data) {
        PipeResponse pipeResponse = new PipeResponse();
        pipeResponse.error = false;
        pipeResponse.data = data;
        return pipeResponse;
    }

    public static PipeResponse responseNoData() {
        return RESPONSE_NO_DATA;
    }

    public boolean isError() {
        return error;
    }

    public ByteBuffer getData() {
        return data;
    }
}
