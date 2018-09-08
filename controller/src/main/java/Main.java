import controller.PipeMessage;
import controller.Pipe;
import controller.PipeFactory;
import controller.PipeResponse;

public class Main {

    public static void main(String[] argv) {

        try {

            Pipe pipe = PipeFactory.openPipe("\\\\.\\pipe\\oldTibiaBot5556");
            PipeResponse pipeResponse;
            pipeResponse = pipe.send(PipeMessage.say("heh"));
            pipeResponse = pipe.send(PipeMessage.readMemory(0x00635F10, 4));

            System.out.println("a");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
