package controller;

import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;


public class Pipe {

    private RandomAccessFile file;
    // for diagnosis purposes
    @Getter private long hits;

    public static Pipe forName(String name) throws FileNotFoundException {
        RandomAccessFile file = new RandomAccessFile(name, "rw");
        return new Pipe(file);
    }

    private Pipe(RandomAccessFile file) {
        this.file = file;
        this.hits = 0;
    }

    public synchronized PipeResponse send(PipeMessageInterface pipeMessage) throws IOException {
        this.hits++;
        this.file.write(pipeMessage.array());
        int responseLength = pipeMessage.getResponseLength();
        if (responseLength > 0) {
            byte[] responseData = new byte[responseLength];
            this.file.read(responseData,0, responseLength);
            ByteBuffer byteBufferResponseData = ByteBuffer.wrap(responseData);
            byteBufferResponseData.order(ByteOrder.LITTLE_ENDIAN);
            return PipeResponse.responseWithData(byteBufferResponseData);
        }

        return PipeResponse.responseNoData();
    }
}
