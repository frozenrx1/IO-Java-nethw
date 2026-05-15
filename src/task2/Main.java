package task2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    private static final String SAVE_DIR = System.getProperty("user.home") + File.separator + "Games" + File.separator + "savegames";

    public static void main(String[] args) {
        GameProgress gp1 = new GameProgress(100, 5, 1, 10.5);
        GameProgress gp2 = new GameProgress(50, 3, 2, 25.0);
        GameProgress gp3 = new GameProgress(10, 1, 3, 50.75);

        String f1 = SAVE_DIR + File.separator + "save1.dat";
        String f2 = SAVE_DIR + File.separator + "save2.dat";
        String f3 = SAVE_DIR + File.separator + "save3.dat";

        saveGame(f1, gp1);
        saveGame(f2, gp2);
        saveGame(f3, gp3);

        List<String> files = new ArrayList<>();
        files.add(f1);
        files.add(f2);
        files.add(f3);

        String zipPath = SAVE_DIR + File.separator + "saves.zip";
        zipFiles(zipPath, files);

        for (String path : files) {
            new File(path).delete();
        }
    }

    public static void saveGame(String path, GameProgress gp) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(String zipPath, List<String> filePaths) {
        try (FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {
            byte[] buffer = new byte[1024];
            for (String path : filePaths) {
                try (FileInputStream fis = new FileInputStream(path)) {
                    ZipEntry entry = new ZipEntry(new File(path).getName());
                    zos.putNextEntry(entry);
                    int len;
                    while ((len = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, len);
                    }
                    zos.closeEntry();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}