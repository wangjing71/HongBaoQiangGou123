package wj.util;

public class ClickUtil {

    private static final long MIN_DELAY_TIME = 3000L;
    private static long lastClickTime;

    public ClickUtil() {
    }

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if (currentClickTime - lastClickTime >= MIN_DELAY_TIME) {
            flag = false;
        }

        lastClickTime = currentClickTime;
        return flag;
    }
}
