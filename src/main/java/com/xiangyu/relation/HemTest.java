package com.xiangyu.relation;

import util.CommonUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HemTest {

    public static void main(String[] args) throws Exception {

        class Info {
            String uid;
            String depId;
            String hosName;
            String proName;
            String city;
            String depName;

            String[] ts;

            public Info(String line) {
                ts = line.split("\\,", -1);

                if (ts.length < 6) {
                    System.err.println("skip invalid line: " + line);
//                    throw new RuntimeException("invalid line data: " + line);
                }
                uid = ts[0].trim();
                depId = ts[1].trim();
                hosName = ts[2].trim();
                proName = ts[3].trim();
                city = ts[4].trim();
                depName = ts[5].trim();
            }
        }

        Map<String, Info> infoMap = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader("D:\\select_a_id__max_c_scheduling_department.csv"));

        do {
            String line = br.readLine();
            if (line == null) {
                break;
            }

            try {
                Info info = new Info(line);
                infoMap.put(info.uid, info);
            } catch (Exception e) {
                // ignore
            }

        } while (true);


        Map<String, Set<String>> relationMap = new HashMap<>();

        AtomicInteger ai = new AtomicInteger();

        infoMap.values().forEach(info -> {


            for (int i = 1; i < info.ts.length; i++) {

                for (int end = i; end < info.ts.length; end++) {


                    List<String> tokens = new ArrayList<>();
                    for (int j = i; j <= end; j++) {

                        String d = info.ts[j];
                        if (CommonUtil.isNotEmpty(d)) {
                            tokens.add(d);
                        }
                    }

                    Collections.sort(tokens);

                    String key = String.join(".", tokens);
                    relationMap.computeIfAbsent(key, k -> new HashSet<>()).add(info.uid);
                }

            }

            //relationMap.keySet().forEach(System.out::println);

            //System.exit(-1);

            System.out.println(ai.incrementAndGet() + ", " + relationMap.size());

        });

        try {
            System.in.read();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
