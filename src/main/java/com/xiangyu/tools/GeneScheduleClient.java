package com.xiangyu.tools;

/**
 * @author chenjing
 * @date 2019-10-18 09:46
 */
public class GeneScheduleClient {
    public static void main(String[] args) {

        // 类名|cron expression|name|group
        // String input = "com.gzhc365.hiwork.schedule.task.scheduling.report.MiniappDailyReportJob|0 0 4 * * ?|schedulingReportGroup";
//        String input = "com.gzhc365.hiwork.schedule.task.bbs.UpdateIndexJob|0 0/5 * * * ?|bbsGroup";
        String input = "com.gzhc365.hiwork.schedule.task.scheduling.remind.NextPeriodNotScheduleRemindJob|0 0 15 * * ?|schedulingRemindGroup";

        String[] inputs = input.split("[|]");

        String packageName = inputs[0];
        String cronExpress = inputs[1];
        String group = inputs[2];

        String[] packageNames = packageName.split("[.]");
        int packageNamesLength = packageNames.length;
        String className = packageNames[packageNamesLength - 1];
        String element = className.substring(0, className.length() - 3);

        String xmlTemplate = "<bean id=\"%s\" class=\"%s\" /><bean id=\"%s\" class=\"org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean\"><property name=\"targetObject\" ref=\"%s\" /><property name=\"targetMethod\" value=\"execute\" /><property name=\"concurrent\" value=\"false\" /></bean><bean id=\"%s\" class=\"org.springframework.scheduling.quartz.CronTriggerFactoryBean\"><property name=\"jobDetail\" ref=\"%s\" /><property name=\"cronExpression\" value=\"%s\" /><property name=\"misfireInstructionName\" value=\"MISFIRE_INSTRUCTION_DO_NOTHING\" /><property name=\"name\" value=\"%s\" /><property name=\"group\" value=\"%s\" /></bean>";

        String xml = String.format(xmlTemplate, element + "Job", packageName, element, element + "Job", element + "Trigger",
                element, cronExpress, element, group);

        System.out.println(xml);
        System.out.println();

        System.out.println("<ref bean=\"" + element + "Trigger" + "\" />");
    }
}
