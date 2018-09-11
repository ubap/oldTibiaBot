package remote;

import java.io.IOException;

public interface PipeMessage {
    PipeResponse execute(Pipe pipe) throws IOException;

    int getResponseLength();
}
