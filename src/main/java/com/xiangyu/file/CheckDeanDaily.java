package com.xiangyu.file;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author chenjing
 * @date 2020-07-14 16:12
 */
public class CheckDeanDaily {
    public static void main(String[] args) throws IOException {
        Set<String> deptNames = new HashSet<>();
        String folder = "C:\\Users\\chenjing\\Documents\\Tencent Files\\120123362\\FileRecv";
        List<String> keys = Files.readAllLines(Paths.get(folder + "\\2(1).txt"));
        String json = keys.get(0);
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray items = jsonObject.getJSONArray("item");
        for (int i = 0; i < items.size(); i++) {
            JSONObject item = items.getJSONObject(i);
            String deptName = item.getString("deptName");
            if (deptNames.contains(deptName)) {
                System.err.println("存在重名的科室");
                System.out.println(item.toJSONString());
                return;
            }
            if (item.getString("deptType") == null) {
                System.err.println("科室的type不存在");
                System.out.println(item.toJSONString());
                return;
            }
            deptNames.add(deptName);
        }
    }
}
