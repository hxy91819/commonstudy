package com.xiangyu.duanwu;

/**
 * @author chenjing
 * @date 2020-06-17 11:48
 */
public class PrizeSQLGene {
    public static void main(String[] args) {
        System.out.println("INSERT INTO hc_hiwork_exam.t_hc_duanwu_prize (id, prize_type_id, url, is_awarded, create_time, update_time) VALUES ");

        String template = "(%s, %s, '%s', 0, '2020-06-16 16:02:29', '2020-06-16 16:02:29')";
        int id = 87;
        System.out.println(String.format(template, id++, 1, "packages/user/coupon/detail/index?id=9346080&alias=l8bp9no8&type=promocard") + ",");
        System.out.println(String.format(template, id++, 1, "packages/user/coupon/detail/index?id=9345786&alias=110nv002a&type=promocard") + ",");
        System.out.println(String.format(template, id++, 1, "packages/user/coupon/detail/index?id=9345286&alias=aepcn2uj&type=promocard") + ",");
        System.out.println(String.format(template, id++, 1, "packages/user/coupon/detail/index?id=9345287&alias=87z96ihd&type=promocard") + ",");

        for (int i = 0; i < 200; i++) {
            System.out.println(String.format(template, id++, 2, "packages/user/coupon/detail/index?id=9347961&alias=f5z7l7zx&type=promocard") + ",");
        }

        for (int i = 0; i < 400; i++) {
            System.out.println(String.format(template, id++, 3, "packages/user/coupon/detail/index?id=9345289&alias=58pf8o7h&type=promocard") + ",");
        }

        int type4Count = 600;
        for (int i = 0; i < type4Count; i++) {
            String suffix = i == type4Count - 1 ? ";" : ",";
            System.out.println(String.format(template, id++, 4, "packages/user/coupon/detail/index?id=9346168&alias=r3wxe0ic&type=promocard") + suffix);
        }

        System.out.println("INSERT INTO hc_hiwork_exam.t_hc_duanwu_prize (id, prize_type_id, url, is_awarded, create_time, update_time) VALUES ");

        int type5Count = 1300;
        for (int i = 0; i < type5Count; i++) {
            String suffix = i == type5Count - 1 ? ";" : ",";
            System.out.println(String.format(template, id++, 5, "packages/user/coupon/detail/index?id=9345975&alias=i3tsebxl&type=promocard") + suffix);
        }
    }
}
