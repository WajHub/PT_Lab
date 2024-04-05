package org.example;

import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        List<Path> files;
        Path source = Path.of("src/main/resources/image");
        Path output = Path.of("src/main/resources/new");
        try(Stream<Path> stream = Files.list(source)) {
            files = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Wystąpił błąd podczas odczytu plików: " + e.getMessage());
            return ;
        }

        files.parallelStream()
                .map(Main::loadImage)
                .map(Main::modifyImage)
                .forEach(pair -> saveNewImage(pair, output));
    }

    public static Pair<String, BufferedImage> loadImage(Path path){
        try {
            BufferedImage image = ImageIO.read(path.toFile());
            return Pair.of(path.getFileName().toString(), image);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Pair<String, BufferedImage> modifyImage(Pair<String, BufferedImage> source){
        BufferedImage original = source.getRight();
        BufferedImage image = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                int rgb = original.getRGB(i, j);
                Color color = new Color(rgb);
                int red = color.getRed();
                int blue = color.getBlue();
                int green = color.getGreen();
                Color outColor = new Color(red, blue, green);
                int outRgb = outColor.getRGB();
                image.setRGB(i, j, outRgb);
            }
        }
        return Pair.of(source.getLeft(), image);
    }

    public static void saveNewImage(Pair<String, BufferedImage> source, Path output){
        try{
            if(!Files.exists(output)){
                Files.createDirectories(output);
            }
            ImageIO.write(source.getRight(), "jpg", output.resolve(source.getLeft()).toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
