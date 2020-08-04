package com.xiangyu.tongyici;

import com.google.common.base.Splitter;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author chenjing
 * @date 2020-07-24 09:13
 */
public class RemoveDirect {
    @Test
    public void test() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("E:\\gzhc-dev\\angle-schedule-miniapp-doc\\update\\386\\es同义词.txt"));
        Splitter splitter = Splitter.on("\\t");
        for (String line : lines) {
            List<String> params = splitter.splitToList(line);
            long directCount = params.stream().filter(param -> param.contains("辖")).count();
            if (directCount > 0) {
                continue;
            }
            System.out.println(line);
        }
    }

    @Test
    public void testReplace() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("E:\\gzhc-dev\\angle-schedule-miniapp-doc\\update\\386\\自定义同义词.txt"));
        Splitter splitter = Splitter.on("\t");
        for (String line : lines) {
            List<String> params = splitter.splitToList(line);
            if (params.get(0).equals(params.get(1))) {
                continue;
            }
            System.out.println(line);
        }
    }

    @Test
    public void nsDic() throws IOException {
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

    @Test
    public void nDic() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get("E:\\gzhc-dev\\angle-schedule-miniapp-doc\\update\\386\\自定义同义词.txt"));
        Set<String> dics = new TreeSet<>();
        Splitter splitter = Splitter.on("\t");
        for (String line : lines) {
            List<String> params = splitter.splitToList(line);
            dics.addAll(params);
        }
        for (String dic : dics) {
            System.out.println(String.format("%s\tn\t20", dic));
        }
    }

}
