package controller;

import java.nio.ByteBuffer;

/**
 * Hold the result and response of a pipe sent message.
 */
public class PipeResponse {
    private boolean error;
    private ByteBuffer data;

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
        PipeResponse pipeResponse = new PipeResponse();
        pipeResponse.error = false;
        pipeResponse.data = null;
        return pipeResponse;
    }

    public boolean isError() {
        return error;
    }
}
