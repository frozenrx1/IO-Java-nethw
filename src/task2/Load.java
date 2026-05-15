package task2;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Load {

    private static final String SAVE_DIR = System.getProperty("user.home") + File.separator + "Games" + File.separator + "savegames";

    public static void main(String[] args) {
        String zipPath = SAVE_DIR + File.separator + "saves.zip";
        openZip(zipPath, SAVE_DIR);

        String savePath = SAVE_DIR + File.separator + "save1.dat";
        GameProgress progress = openProgress(savePath);

        if (progress != null) {
            System.out.println(progress);
        }
    }

    public static void openZip(String zipPath, String destDir) {
        try (FileInputStream fis = new FileInputStream(zipPath);
             ZipInputStream zis = new ZipInputStream(fis)) {

            ZipEntry entry;
            byte[] buffer = new byte[1024];
            while ((entry = zis.getNextEntry()) != null) {
                File outFile = new File(destDir, entry.getName());
                if (entry.isDirectory()) {
                    outFile.mkdirs();
                } else {
                    outFile.getParentFile().mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(outFile)) {
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameProgress openProgress(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (GameProgress) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}