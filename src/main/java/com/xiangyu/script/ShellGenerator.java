package com.xiangyu.script;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * Shell脚本生成器
 * @author chenjing
 * @date 2020-01-02 15:40
 */
public class ShellGenerator {
    private static final List<String> dubboServices = ImmutableList.of("hwassembly", "hwautoschedule", "hwbbs",
            "hwmanage", "hwrank", "hwscheduling", "hwsuite", "hwwebsite");

    private static final List<String> webServices = ImmutableList.of("hwassembly", "hwintercom", "hwmanage", "hwminiapp", "hwschedule", "hwwebsite");

    private static final String replateShellTemplate = "cp /home/dubbo/service/%s-center/bin/service-* /tmp\n" +
            "sed -i \"s/LOGS_LEVE=DEBUG/LOGS_LEVE=INFO/g\"  `grep LOGS_LEVE=DEBUG -rl /home/dubbo/service/%s-center/bin/service-*`";

    public static final String startServiceTemplate = "sh hip.sh %s-center";

    public static final String startWebTemplate = "sh hip.sh web-%s";

    /**
     * 替换的文本中有“-”符号的需要进行转译
     * @param args
     */
    public static void main(String[] args) {
        for (String dubboService : dubboServices) {
            System.out.println(String.format(startServiceTemplate, dubboService));
        }
    }
}
