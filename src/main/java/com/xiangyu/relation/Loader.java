package com.xiangyu.relation;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Loader {

    /**
     * 用户属性Map（key：userId；value：属性）
     */
    private Map<Long, List<Object>> userFieldsMap;

    /**
     * 根据特征倒排的Map（Key：特征值；value：具有这个特征的用户set）。下同。
     */
    private Map<Long, Set<Long>> departmentIdUserIdMap;

    private Map<String, Set<Long>> hospitalNameUserIdMap;

    private Map<String, Set<Long>> provinceUserIdMap;

    private Map<String, Set<Long>> cityUserIdMap;

    private Map<String, Set<Long>> departmentNameUserIdMap;


    /**
     * 文件地址
     */
    private final String filePath;

    private static final Splitter COMMA_SPLITTER = Splitter.on(',');

    public Loader(String filePath) throws IOException {
        this.filePath = filePath;
        loadFormFile();
    }

    private void loadFormFile() throws IOException {
        departmentIdUserIdMap = new HashMap<>();
        hospitalNameUserIdMap = new HashMap<>();
        provinceUserIdMap = new HashMap<>();
        cityUserIdMap = new HashMap<>();
        departmentNameUserIdMap = new HashMap<>();
        userFieldsMap = new HashMap<>();

        List<String> userInfos = Files.readAllLines(Paths.get(this.filePath));
        for (String userInfo : userInfos) {
            if (!isNotEmpty(userInfo)) {
                continue;
            }
            List<String> fields = COMMA_SPLITTER.splitToList(userInfo);
            if (fields.size() < 6) {
                continue;
            }
            Long userId = Long.valueOf(fields.get(0));
            Long departmentId = Long.valueOf(fields.get(1));
            String hospitalName = fields.get(2);
            String province = fields.get(3);
            String city = fields.get(4);
            String departmentName = fields.get(5);

            List<Object> userFields = new ArrayList<>();
            userFields.add(departmentId);
            userFields.add(hospitalName);
            userFields.add(province);
            userFields.add(city);
            userFields.add(departmentName);
            userFieldsMap.put(userId, userFields);

            departmentIdUserIdMap.computeIfAbsent(departmentId, key -> new HashSet<>()).add(userId);
            if (isNotEmpty(hospitalName)) {
                hospitalNameUserIdMap.computeIfAbsent(hospitalName, key -> new HashSet<>()).add(userId);
            }

            if (isNotEmpty(province)) {
                provinceUserIdMap.computeIfAbsent(province, key -> new HashSet<>()).add(userId);
            }
            if (isNotEmpty(city)) {
                cityUserIdMap.computeIfAbsent(city, key -> new HashSet<>()).add(userId);
            }
            if (isNotEmpty(departmentName)) {
                departmentNameUserIdMap.computeIfAbsent(departmentName, key -> new HashSet<>()).add(userId);
            }
        }
    }

    private boolean isNotEmpty(String field) {
        return field != null && !field.equals("") && !field.equals("\"\"");
    }

    /**
     * 按亲密度，获取有关系的人
     *
     * @param searchUserId
     * @return
     */
    public Map<Long, Integer> getRelatives(Long searchUserId) {
        LocalDateTime startTime = LocalDateTime.now();
        List<Object> fields = userFieldsMap.get(searchUserId);
        // 分别获取每个fields里关联的人，并计算分数，得到前100，则返回
        Long departmentId = (Long) fields.get(0);
        String hospitalName = (String) fields.get(1);
        String province = (String) fields.get(2);
        String city = (String) fields.get(3);
        String departmentName = (String) fields.get(4);

        // 人员id与权重分map
        Map<Long, Integer> userIdPointMap = new HashMap<>();
        for (Long userId : departmentIdUserIdMap.getOrDefault(departmentId, ImmutableSet.of())) {
            userIdPointMap.merge(userId, 100, Integer::sum);
        }

        for (Long userId : hospitalNameUserIdMap.getOrDefault(hospitalName,ImmutableSet.of())) {
            userIdPointMap.merge(userId, 40, Integer::sum);
        }

        for (Long userId : provinceUserIdMap.getOrDefault(province,ImmutableSet.of())) {
            userIdPointMap.merge(userId, 3, Integer::sum);
        }

        for (Long userId : cityUserIdMap.getOrDefault(city, ImmutableSet.of())) {
            userIdPointMap.merge(userId, 5, Integer::sum);
        }

        for (Long userId : departmentNameUserIdMap.getOrDefault(departmentName, ImmutableSet.of())) {
            userIdPointMap.merge(userId, 5, Integer::sum);
        }

        Map<Long, Integer> sortedMap = new LinkedHashMap<>();
        userIdPointMap.entrySet().stream()
                .sorted(Map.Entry.<Long, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        LocalDateTime stopTime = LocalDateTime.now();
        long between = ChronoUnit.MILLIS.between(startTime, stopTime);
        System.out.println(between);
        return sortedMap;
    }
}
