package wj;

import java.util.ArrayList;
import java.util.Random;

public class UserAgentUtil {
    private static ArrayList<String> uaList;
    private static int listSize = 0;
    private static Random random = new Random();

    static {
        uaList = FileUtil.startReadFile(MainPage.CURRENT_PATH + "/ua.txt");
        listSize = uaList.size();
    }

    public static String randomUserAgent() {
        return uaList.get(random.nextInt(listSize));
    }
}
