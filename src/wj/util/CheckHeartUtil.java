package wj.util;

import org.json.JSONObject;
import wj.safe.Des3Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckHeartUtil {
    public static String VERSION = "1.6";
    private static long lastTime = 0;


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
            connection.setReadTimeout(5000);
            connection.setConnectTimeout(5000);
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
