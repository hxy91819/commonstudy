package util;

/**
 * @author chenjing
 * @date 2020-07-21 09:38
 */
public class CommonUtil {
    public static boolean isNotEmpty(String string) {
        return string != null && !"".equals(string);
    }

    public static boolean isEmpty(String string) {
        return !isNotEmpty(string);
    }
}
