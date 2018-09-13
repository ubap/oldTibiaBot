package os;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessListUtil {
    private static final Logger log = LoggerFactory.getLogger(ProcessListUtil.class);

    private ProcessListUtil() { }

    public static List<Integer> getProcessList(String processName) {
        List<Integer> processIds = new ArrayList<>();
        List<String> linesList = new ArrayList<>();
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec(
                    String.format("tasklist /FI \"IMAGENAME  eq %s\"", processName));
            input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            {
                int lineNumber = 0;
                String line;
                while ((line = input.readLine()) != null) {
                    if (line.isEmpty()) {
                        continue;
                    }
                    lineNumber++;
                    if (lineNumber > 2) {
                        linesList.add(line);
                    }
                }
            }

            for (String line : linesList) {
                String[] tokensArray = line.split("\\s+");
                if (tokensArray.length > 2) {
                    processIds.add(Integer.valueOf(tokensArray[1]));
                }
            }
        } catch (IOException | NumberFormatException e) {
            log.error("Exception getting process list", e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    log.error("Exception closing input", e);
                }
            }
        }

        return processIds;
    }
}
