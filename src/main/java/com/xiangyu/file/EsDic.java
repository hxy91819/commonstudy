package com.xiangyu.file;

import com.google.common.base.Splitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author chenjing
 * @date 2020-07-23 17:27
 */
public class EsDic {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("E:\\gzhc-dev\\angle-schedule-miniapp-doc\\update\\386\\自定义同义词.txt"));
        Set<String> dics = new TreeSet<>();
        Splitter splitter = Splitter.on("\t");
        for (String line : lines) {
            List<String> params = splitter.splitToList(line);
            dics.add(params.get(1));
        }
        for (String dic : dics) {
            System.out.println(String.format("%s\tns\t20", dic));
        }
    }
}
