import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        String rootPath = System.getProperty("user.home") + File.separator + "Games";
        StringBuilder log = new StringBuilder("=== Start Installation ===\n");

        createDirectory(rootPath, "src", log);
        createDirectory(rootPath, "res", log);
        createDirectory(rootPath, "savegames", log);
        createDirectory(rootPath, "temp", log);

        String srcPath = rootPath + File.separator + "src";
        createDirectory(srcPath, "main", log);
        createDirectory(srcPath, "test", log);

        String mainPath = srcPath + File.separator + "main";
        createFile(mainPath, "Main.java", log);
        createFile(mainPath, "Utils.java", log);

        String resPath = rootPath + File.separator + "res";
        createDirectory(resPath, "drawables", log);
        createDirectory(resPath, "vectors", log);
        createDirectory(resPath, "icons", log);

        String tempPath = rootPath + File.separator + "temp";
        createFile(tempPath, "temp.txt", log);

        log.append("=== Installation Complete ===\n");
        writeLog(tempPath + File.separator + "temp.txt", log.toString());

        System.out.println("Done. Check logs in temp.txt");
    }

    private static void createDirectory(String parent, String name, StringBuilder log) {
        File dir = new File(parent, name);
        boolean created = dir.mkdir();
        log.append("Dir: ").append(name).append(" -> ").append(created ? "OK" : "Exists/Error").append("\n");
    }

    private static void createFile(String parent, String name, StringBuilder log) {
        File file = new File(parent, name);
        try {
            boolean created = file.createNewFile();
            log.append("File: ").append(name).append(" -> ").append(created ? "OK" : "Exists").append("\n");
        } catch (IOException e) {
            log.append("File: ").append(name).append(" -> ERROR: ").append(e.getMessage()).append("\n");
        }
    }

    private static void writeLog(String path, String content) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }
}