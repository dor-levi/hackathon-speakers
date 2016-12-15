package com.hackathon.speakers.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    public static String writeToFile(String text) {
        try {
            File temp = File.createTempFile("tempfile", ".tmp");
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
            bw.write("text");
            bw.close();
            return temp.getAbsolutePath();
        } catch (IOException e) {
            return null;
        }
    }
}
