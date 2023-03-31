import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String srcFolder = "\\img";
        String drcFolder = "\\drcImg";

        File srcDir = new File(srcFolder);
        File[] files = srcDir.listFiles();
        assert files != null;
        rasizer(drcFolder, files);
    }

    private static void rasizer(String drcFolder, File[] files) {
        long start = System.currentTimeMillis();
        int numberOfCores = Runtime.getRuntime().availableProcessors();
        int count = 0;
        List<File> fi = new ArrayList<>();
        while (true){
            fi.add(files[count]);
            count++;
            if (count > files.length - 1) {
                thread(drcFolder, start, fi);
                break;
            }
            if (count % numberOfCores == 0) {
                thread(drcFolder, start, fi);
                fi = new ArrayList<>();
            }
        }
    }

    private static void thread(String drcFolder, long start, List<File> fi) {
        ImageRasizer imageRasizer = new ImageRasizer(drcFolder, fi, start);
        new Thread(imageRasizer).start();
    }
}