package com.nottesla.roosight;

import org.opencv.core.Core;

import java.io.File;

/**
 * Created by tesla on 4/15/16.
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        Thread.sleep(5000);
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        File files[] = new File("/tmp/images").listFiles();
        for (int i = 0; i < files.length; ++i) {
            process(files[i]);
        }
    }

    public static void process(File file) {
        if (!file.getName().endsWith(".jpg") || file.getName().endsWith(".jpg.jpg")) {
            return;
        }
        System.out.println(file.getName());
        System.out.printf("Usage: %d/%d (%d%%)\n", Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory(), Runtime.getRuntime().totalMemory(), (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) * 100 / Runtime.getRuntime().totalMemory());
        RooColorImage colorImage = new RooColorImage(file.getAbsolutePath());
        RooConfig config = new RooConfig();
        config.setMinArea(400);
        config.setMinPerimeter(300);
        config.setRGB(0, 250, 100, 255, 20, 255);
        config.setHSL(10, 180, 0, 255, 50, 255);
        RooProcessor rooProcessor = new RooProcessor(config);
        RooBinaryImage thresh = rooProcessor.processImage(colorImage);
        RooContour[] contours = rooProcessor.findContours(thresh);
        colorImage.drawContours(contours, new RooColor(0, 0, 255), 2);
        colorImage.blur(1);
        colorImage.writeToFile(file.getAbsolutePath() + ".jpg");
    }
}