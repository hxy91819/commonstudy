package com.xiangyu.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenjing
 * @date 2020-06-29 10:43
 */
public class ReadAndSort {
    public static void main(String[] args) throws IOException {
        String folder = "C:\\Users\\chenjing\\Desktop";
        List<String> keys = Files.readAllLines(Paths.get(folder +"\\hiwork永久key.txt"));
        List<String> sortKeys = keys.stream().sorted().collect(Collectors.toList());
        Files.write(Paths.get(folder + "\\排序key.txt"), sortKeys);
    }
}
