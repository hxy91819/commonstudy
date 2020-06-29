package com.xiangyu.duanwu;

/**
 * @author chenjing
 * @date 2020-06-17 11:54
 */
public class RandomNickName {
    public static void main(String[] args) {
        String template = "(SELECT\n" +
                "            nick_name\n" +
                "        FROM\n" +
                "            `t_hc_suite_global_user` AS t1\n" +
                "        JOIN (\n" +
                "            SELECT\n" +
                "                ROUND(\n" +
                "                    RAND() * (\n" +
                "                        (\n" +
                "                            SELECT\n" +
                "                                MAX(id)\n" +
                "                            FROM\n" +
                "                                `t_hc_suite_global_user`\n" +
                "                        ) - (\n" +
                "                            SELECT\n" +
                "                                MIN(id)\n" +
                "                            FROM\n" +
                "                                `t_hc_suite_global_user`\n" +
                "                        )\n" +
                "                    ) + (\n" +
                "                        SELECT\n" +
                "                            MIN(id)\n" +
                "                        FROM\n" +
                "                            `t_hc_suite_global_user`\n" +
                "                    )\n" +
                "                ) AS id\n" +
                "        ) AS t2 ON t1.id < t2.id\n" +
                "        AND t1.nick_name != ''\n" +
                "        AND avatar != ''\n" +
                "        ORDER BY\n" +
                "            t1.id DESC\n" +
                "        LIMIT 1)";

        StringBuilder sql = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            sql.append(template).append(" UNION ALL");
        }
        System.out.println(sql);
    }
}
