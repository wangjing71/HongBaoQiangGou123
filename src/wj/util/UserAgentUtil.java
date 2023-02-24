package wj.util;

import wj.MainPage;

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

    public static String randomUA() {
        return "jdltapp;iPhone;3.7.0;14.2;c71b599e9a0bcbd8d1ad924d0909398315efad06;network/wifi;hasUPPay/0;pushNoticeIsOpen/0;lang/zh_CN;model/iPhone11,8;addressid/0;hasOCPay/0;appBuild/1017;supportBestPay/0;pv/263.8;apprpd/;ref/JDLTSubMainPageViewController;psq/2;ads/;psn/c71b599e9a0bcbd8d1ad924d0909398315efad06|481;adk/;app_device/IOS;pap/JA2020_3112531|3.7.0|IOS 14.2;Mozilla/5.0 (iPhone; CPU iPhone OS 14_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/15E148;supportJDSHWK/1".replaceAll("0909398315", RandomUtils.getRandomNo(10));
    }
}
