package src.main.java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileSearch {

    protected static List<String> listJson = new ArrayList<>();
    protected static List<String> listCsv = new ArrayList<>();

    public static List<String> getListJson() {
        return listJson;
    }

    public static List<String> getListCsv() {
        return listCsv;
    }

    public FileSearch(File file) {
        getFolderSize(file);
    }
    private static void getFolderSize(File file) {
        if (file.isFile()) {
            if (file.getPath().contains(".json"))
                listJson.add(file.getPath());

            if (file.getPath().contains(".csv"))
                listCsv.add(file.getPath());

        }
        if (file.listFiles() != null) {
            File[] folders = file.listFiles();
            assert folders != null;
            for (File f : folders) {
                getFolderSize(f);
            }
        }
    }
}


