package com.xiangyu;

import java.util.ArrayList;
import java.util.Random;

/**
 * Hello world!
 */
public class App {
    private static int range = 16;
    private static ArrayList<Integer> originalList = new ArrayList<Integer>();
    private static ArrayList<Integer> result = new ArrayList<Integer>();

    static {
        for (int i = 1; i <= range; i++) {
            originalList.add(i);
        }
    }

    public static void main(String args[]) {
        for (int i = 0; i < range; i++) {
            int j = range - i;
            int r = (int) (new Random().nextInt(j));

            result.add(originalList.get(r));

            String template = "update SUTTEXMOPT set opt_num = %s where EXM_ID = '201912311828258908' and qus_num = 2 and opt_num = %s;";

            System.out.println(String.format(template, originalList.get(r) + 100, i + 1));
            originalList.remove(r);
        }
    }
}
