package wj.util;

import org.json.JSONObject;
import wj.safe.Des3Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckHeartUtil {
    private static long lastTime = 0;

    public static boolean pass() {
        String result = CheckHeartUtil.get("http://43.142.100.135/wangjing/update?type=1&deviceId=" + MachineCodeUtil.getThisMachineCodeMd5() + "&appVersion=1.0");
        String realData = Des3Util.decode(result);
        System.out.println(realData);
//        realData = "{\"isUpdate\":\"0\",\"content_url\":\"http://106.55.196.225:8080/wangjing/file/1011.apk\",\"content\":\"版本更新V10.1.1\\n\\n修复wskey频繁失效问题！！！\\n\\n如果无法在线更新请从Q群置顶文件下载最新安装包\\n\\n如果显示异常，请移除小组件后重新添加\",\"widgetTip\":\"\",\"mainImg\":\"http://106.55.196.225:8080/wangjing/file/splash_bg5.jpg\",\"release\":\"10.1.1\",\"hideSafe\":\"0\",\"isDark\":\"0\",\"showGuaDou\":\"1\",\"qqGroupLink\":\"oYUSH-IEmLKUDRnpVZAvMMUHGwUXmWlk\",\"serverTime\":1677227964262,\"serverType\":\"1\",\"showHelp\":\"0\",\"color\":\"#ff00ff\",\"loadType\":\"1\",\"uploadSucTip\":\"呆瓜交流群：753359460\\n需要农场种豆萌宠满助力请加群！！！\",\"uploadFailTip\":\"\"}";
        try {
            JSONObject job = new JSONObject(realData);
            String hideSafe = job.optString("hideSafe");
            long serverTime = job.optLong("serverTime");
            if ("0".equals(hideSafe) && serverTime > lastTime) {
                System.out.println("检查通过");
                lastTime = serverTime;
                return true;
            } else {
                lastTime = serverTime;
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        System.out.println("检查未通过");
        return false;
    }

    private static String get(String url) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            connection.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOiIxNjY2ODcxMDk5IiwiZXhwIjoxNjY2OTU3NDk5LCJOYW1lIjoiMzk3MzgzNTIzIiwiSVAiOiIyMjEuMjI1LjE5NS4xNjkiLCJMb2dpblRpbWUiOiIxNjY2ODcxMDk5IiwiaXNzIjoiaHR0cC8vOnF1YW50dW0tYXNzaXN0YW50IiwiYXVkIjoiaHR0cC8vOnF1YW50dW0tYXNzaXN0YW50In0.KUjLI5soeaUahhDVquYnvFHVXAmLyY1a4QO96ik89kk");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.connect();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            }
        }
        return result.toString();
    }

}
