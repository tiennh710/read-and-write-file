import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WriteFileUtils {
    private final static String PATH_NAME = "440mb.txt";
    private final static String PATH = "440mb.txt";


    public static void main(String[] args) {
        createFile();
    }

    private static void createFile(){
        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam ullamcorper diam neque, a laoreet dui porta sit amet. Nulla facilisi. Nam congue laoreet lacus, vitae mattis ante accumsan in. Pellentesque at lacinia velit. Nunc et faucibus purus. Interdum et malesuada fames ac ante ipsum primis in faucibus. In hac habitasse platea dictumst. Morbi non nunc sit amet urna hendrerit lacinia. Phasellus neque nisl, porttitor eget elit vel, egestas molestie velit. Pellentesque ut mauris in lorem viverra rutrum et eget felis. Nunc nisl urna, tincidunt nec velit et, eleifend luctus nisi. Curabitur at sodales est. Mauris tristique interdum blandit. In dictum pellentesque ante sodales mollis. Donec consectetur, nunc ut elementum auctor, purus nunc placerat mauris, eu euismod arcu risus ac orci. Nam vel felis pulvinar, porta velit nec, rutrum mi.";
        File file = new File(PATH_NAME);
        Path filePath = Paths.get(PATH);
        long fileSize = 0;
        boolean valid = true;
        do {
            try {
                text = text + text + "\n\n";
                FileUtils.writeStringToFile(file, text, Charset.defaultCharset());
                fileSize = Files.size(filePath) / 1024;
                System.out.println(fileSize);
                valid = fileSize < 500000;
            } catch (OutOfMemoryError | IOException ex) {
                System.out.println("OutOfMemoryError ----> Stop!");
                return;
            }
        } while (valid);
    }

    private static void createFile2(){
        String text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam ullamcorper diam neque, a laoreet dui porta sit amet. Nulla facilisi. Nam congue laoreet lacus, vitae mattis ante accumsan in. Pellentesque at lacinia velit. Nunc et faucibus purus. Interdum et malesuada fames ac ante ipsum primis in faucibus. In hac habitasse platea dictumst. Morbi non nunc sit amet urna hendrerit lacinia. Phasellus neque nisl, porttitor eget elit vel, egestas molestie velit. Pellentesque ut mauris in lorem viverra rutrum et eget felis. Nunc nisl urna, tincidunt nec velit et, eleifend luctus nisi. Curabitur at sodales est. Mauris tristique interdum blandit. In dictum pellentesque ante sodales mollis. Donec consectetur, nunc ut elementum auctor, purus nunc placerat mauris, eu euismod arcu risus ac orci. Nam vel felis pulvinar, porta velit nec, rutrum mi.";
        String log = "";
        int line = 1;
        File file = new File(PATH_NAME);
        Path filePath = Paths.get(PATH);
        long fileSize = 0;
        boolean valid = true;
        do {
            try {
                log = log + line + ". " + text + "\n\n";
                line++;
                FileUtils.writeStringToFile(file, log, Charset.defaultCharset());

                fileSize = Files.size(filePath) / 1024;
                System.out.println(fileSize);
                valid = fileSize < 500000;
            } catch (OutOfMemoryError | IOException ex) {
                System.out.println("OutOfMemoryError ----> Stop!");
                return;
            }
        } while (valid);
    }

    private static final void logMemory() {
        System.out.println("Max Memory: " + Runtime.getRuntime().maxMemory() / 1048576);
        System.out.println("Total Memory: " + Runtime.getRuntime().totalMemory() / 1048576);
        System.out.println("Free Memory: " + Runtime.getRuntime().freeMemory() / 1048576);
    }
}

