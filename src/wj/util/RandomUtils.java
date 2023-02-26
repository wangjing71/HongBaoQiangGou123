package wj.util;

import java.util.Random;

/**
 * @author ZhangSan_Plus
 * @version 1.0
 * @className RandomUtils
 * @description TODO  随机字符工具类
 * @date 2020/10/26 19:13
 **/
public class RandomUtils {

    public static Random random = new java.util.Random();

    public static void main(String[] args) {
    }


    private static final String MATCHES_NO = ".*[a-z]{1,}.*";
    private static final String MATCHES_TWO = ".*[A-Z]{1,}.*";
    private static final String MATCHES_THREE = ".*[0-9]{1,}.*";
    private static final String REGULAR_CHAR = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final String NO_COUNT_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final String UP_LETTER_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBER_CHAR = "1234567890";

    /**
     * 根据length来生成随机长度字符
     *
     * @param length
     * @return java.lang.String
     * @throws
     * @author ZhangSan_Plus
     * @date 19:15 2020/10/26
     **/
    public static String generatePassword(int length) {
        String result;
        result = getRandomPassword(length);
        if (result.matches(MATCHES_NO) && result.matches(MATCHES_TWO) && result.matches(MATCHES_THREE)) {
            return result;
        }
        return generatePassword(length);
    }

    public static String getRandomPassword(int length) {
        char[] charBuffer = REGULAR_CHAR.toCharArray();
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int x = 0; x < length; ++x) {
            sb.append(charBuffer[r.nextInt(charBuffer.length)]);
        }
        return sb.toString();
    }

    public static String getRandomPasswordToLowerCase(int length) {
        char[] charBuffer = REGULAR_CHAR.toCharArray();
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int x = 0; x < length; ++x) {
            sb.append(charBuffer[r.nextInt(charBuffer.length)]);
        }
        return sb.toString().toLowerCase();
    }

    public static String getRandomNo(int numberLength) {
        return getRandomNo(numberLength, 0);
    }

    public static String getRandomNo() {
        return random.nextInt(999) + "";
    }

    public static String getRandomNo(int numberLength, int letterLength) {
        int nums = numberLength;
        int letter = letterLength;
        char[] charBuffer = NO_COUNT_CHAR.toCharArray();
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int x = 0; x < numberLength + letterLength; x++) {
            int i = r.nextInt(charBuffer.length);
            sb.append(charBuffer[i]);
            if (48 <= charBuffer[i] && charBuffer[i] <= 57) {
                nums--;
            } else {
                letter--;
            }
            if (nums == 0) {
                charBuffer = UP_LETTER_CHAR.toCharArray();
            } else if (letter == 0) {
                charBuffer = NUMBER_CHAR.toCharArray();
            }
        }
        return sb.toString();
    }
}