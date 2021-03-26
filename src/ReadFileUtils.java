import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.input.ReversedLinesFileReader;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadFileUtils {
    final static String FILEPATH = "./440mb.txt";

    public static void main(String[] args) {
        measureTime("LineNumberReader into ArrayList", ReadFileUtils::lineNumberReaderArrayList, FILEPATH);
        measureTime("BufferedReader.readLine() into ArrayList", ReadFileUtils::bufferReaderToArrayList, FILEPATH);
        measureTime("Stream into ArrayList", ReadFileUtils::streamArrayList, FILEPATH);
        measureTime("ApacheCommonIO into ArrayList", ReadFileUtils::apacheCommonIOArrayList, FILEPATH);
        measureTime("Scanner.nextLine() into ArrayList", ReadFileUtils::scannerArrayList, FILEPATH);
        measureTime("RandomAccessFile.readLine() into ArrayList", ReadFileUtils::randomAccessFileArrayList, FILEPATH);
        measureTime("Files.readAllLines()", ReadFileUtils::readAllLines, FILEPATH);
    }

    private static void measureTime(String name, Function<String, List<String>> fn, String path) {
        System.out.println("-----------------------------------------------------------");
        System.out.println("run: " + name);
        long startTime = System.nanoTime();
        List<String> l = fn.apply(path);
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("lines: " + l.size());
        System.out.println("estimatedTime: " + estimatedTime / 1_000_000_000);
    }

    public static List<String> readAllLines(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<String> bufferReaderToLinkedList(String path) {
        return bufferReaderToList(path, new LinkedList<>());
    }

    public static List<String> bufferReaderToArrayList(String path) {
        return bufferReaderToList(path, new ArrayList<>());
    }

    private static List<String> bufferReaderToList(String path, List<String> list) {
        try {
            final BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null) {
                list.add(line);
            }
            in.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> randomAccessFileLinkedList(String path) {
        return randomAccessFile(path, new LinkedList<>());
    }

    public static List<String> randomAccessFileArrayList(String path) {
        return randomAccessFile(path, new ArrayList<>());
    }

    private static List<String> randomAccessFile(String path, List<String> list) {
        try {
            RandomAccessFile file = new RandomAccessFile(path, "r");
            String str;
            while ((str = file.readLine()) != null) {
                list.add(str);
            }
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> scannerLinkedList(String path) {
        return scanner(path, new LinkedList<>());
    }

    public static List<String> scannerArrayList(String path) {
        return scanner(path, new ArrayList<>());
    }

    private static List<String> scanner(String path, List<String> list) {
        try {
            Scanner scanner = new Scanner(new File(path));
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> streamLinkedList(String path) {
        return stream(path, new ArrayList<>());
    }

    public static List<String> streamArrayList(String path) {
        return stream(path, new LinkedList<>());
    }

    private static List<String> stream(String path, List<String> list) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            list = stream.collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> lineNumberReaderLinkedList(String path) {
        return lineNumberReader(path, new LinkedList<>());
    }

    public static List<String> lineNumberReaderArrayList(String path) {
        return lineNumberReader(path, new ArrayList<>());
    }

    private static List<String> lineNumberReader(String filename, List<String> list) {
        try (LineNumberReader lnr = new LineNumberReader(new FileReader(filename))) {
            String line = "";
            while ((line = lnr.readLine()) != null) {
                list.add(line);
//                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<String> apacheCommonIOLinkedList(String path) {
        return lineNumberReader(path, new LinkedList<>());
    }

    public static List<String> apacheCommonIOArrayList(String path) {
        return lineNumberReader(path, new ArrayList<>());
    }

    private static List<String> apacheCommonIO(File file, List<String> list) throws IOException {
        final LineIterator it = FileUtils.lineIterator(file, "UTF-8");
        try {
            while (it.hasNext()) {
                final String line = it.nextLine();
                list.add(line);
            }
        } finally {
            LineIterator.closeQuietly(it);
        }
        return list;
    }

    //    ---------------------------------------------------------
    //    ---------------------------------------------------------
    //    ---------------------------------------------------------

    public static List<String> usingStreams(String filePath, int startLine, int limitLine) {
        List<String> lines = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            lines = stream
                    .skip(startLine)
                    .limit(limitLine)
                    .collect(Collectors.toList());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return lines;
    }

    public static List<String> lineNumberReader(String filename, int from, int to, String keyword) {
        List<String> list = new ArrayList<>();
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(filename));
            String line = null;
            while ((line = lineNumberReader.readLine()) != null) {
                if (lineNumberReader.getLineNumber() <= to && lineNumberReader.getLineNumber() >= from && line.contains(keyword)) {
                    list.add(lineNumberReader.getLineNumber() + " - " + line);
//                    System.out.println(lineNumberReader.getLineNumber() + " - " + line);
                }
            }
            lineNumberReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> lineNumberReader(String filename, String keyword) {
        List<String> list = new ArrayList<>();
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(filename));
            String line = null;
            while ((line = lineNumberReader.readLine()) != null) {
                if (line.contains(keyword)) {
                    list.add(lineNumberReader.getLineNumber() + " - " + line);
//                    System.out.println(lineNumberReader.getLineNumber() + " - " + line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> lineNumberReader(String filename, int from, int to) {
        List<String> list = new ArrayList<>();
        try {
            LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(filename));
            String line = null;
            while ((line = lineNumberReader.readLine()) != null) {
                if (lineNumberReader.getLineNumber() <= to && lineNumberReader.getLineNumber() >= from) {
                    list.add(lineNumberReader.getLineNumber() + " - " + line);
                    System.out.println(lineNumberReader.getLineNumber() + " - " + line);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public static List<String> ApacheCommonIO(File file, int numLastLineToRead) {
        List<String> result = new ArrayList<>();
        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(file, StandardCharsets.UTF_8)) {
            String line = "";
            while ((line = reader.readLine()) != null && result.size() < numLastLineToRead) {
                result.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<String> ApacheCommonIO(File file, int numLastLineToRead, String keyword) {
        List<String> result = new ArrayList<>();
        try (ReversedLinesFileReader reader = new ReversedLinesFileReader(file, StandardCharsets.UTF_8)) {
            String line = "";
            while ((line = reader.readLine()) != null && result.size() < numLastLineToRead) {
                if (line.contains(keyword)) {
                    result.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
