package com.xiangyu.relation;

import org.junit.Test;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class LoaderTest {
    @Test
    public void test() throws IOException, InterruptedException {
        Loader loader = new Loader("C:\\Users\\chenjing\\Documents\\WeChat Files\\hxy91819\\FileStorage\\File\\2020-07\\select_a_id__max_c_scheduling_department.csv");
        Map<Long, Integer> relatives = loader.getRelatives(2545579735129358339L);
        TimeUnit.SECONDS.sleep(60);
    }

}