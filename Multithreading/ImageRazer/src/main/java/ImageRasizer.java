import org.imgscalr.Scalr;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageRasizer implements Runnable{
    String drcFolder;
    List<File> files;
    long start;

    public ImageRasizer(String drcFolder, List<File> files, long start) {
        this.drcFolder = drcFolder;
        this.files = files;
        this.start = start;
    }

    @Override
    public void run() {
        for (File file : files) {
            // Чтение изображения в буфер
            BufferedImage image = null;
            try {
                image = ImageIO.read(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            // Ширина и высота
            int targetSize = 500;

            // Создание нового изображения с размерами
            BufferedImage resizedImage = Scalr.resize(image, Scalr.Method.AUTOMATIC, targetSize);

            // Сохранение уменьшенного изображения

            File outputFile = new File(drcFolder, file.getName());
            try {
                ImageIO.write(resizedImage, "jpg", outputFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
