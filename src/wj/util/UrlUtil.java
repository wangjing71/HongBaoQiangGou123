package wj.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class UrlUtil {
    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    //Unicode转中文方法
    public static String unicodeToCn(String unicode) {
        /** 以 \ u 分割，因为java注释也能识别unicode，因此中间加了一个空格*/
        String[] strs = unicode.split("\\\\u");
        String returnStr = "";
        // 由于unicode字符串以 \ u 开头，因此分割出的第一个字符是""。
        for (int i = 1; i < strs.length; i++) {
            returnStr += (char) Integer.valueOf(strs[i], 16).intValue();
        }
        return returnStr;
    }

    //中文转Unicode
    private static String cnToUnicode(String cn) {
        char[] chars = cn.toCharArray();
        String returnStr = "";
        for (int i = 0; i < chars.length; i++) {
            returnStr += "\\u" + Integer.toString(chars[i], 16);
        }
        return returnStr;
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     */
    public static String urlEncode(String input) {
        try {
            return URLEncoder.encode(input, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     */
    public static String urlDecode(String input) {
        try {
            return URLDecoder.decode(input, DEFAULT_URL_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Unsupported Encoding Exception", e);
        }
    }

    public static void main(String[] args) {
        String url = "https://wqs.jd.com/sns/202210/20/make-money-shop/bridge.html?type=sign&amp;activeId=63526d8f5fe613a6adb48f03&amp;shareId=c52fd97b6225825d0140f2187c1d7967&amp;bridgeType=sign&amp;sharefromapp=jdltapp&amp;channel=superjd-m-makemoneyking-hudong&amp;utm_user=plusmember&amp;ad_od=share&amp;utm_source=androidapp&amp;utm_medium=appshare&amp;utm_campaign=t_335139774&amp;utm_term=Wxfriends";
        System.out.println(urlDecode(url));
    }
}
