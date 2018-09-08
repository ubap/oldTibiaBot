package controller;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public final class PipeFactory {

    private PipeFactory() { }

    public static Pipe openPipe(String name) throws FileNotFoundException {

        RandomAccessFile file = new RandomAccessFile(name, "rw");

        return new Pipe(file);
    }
}
