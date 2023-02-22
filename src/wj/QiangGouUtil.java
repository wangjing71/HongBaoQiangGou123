package wj;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static wj.MainPage.ckBeanList;

public class QiangGouUtil {
    public static void qiangHongbaoTask() {
        if (ClickUtil.isFastClick()) {
            return;
        }
        System.out.println("开始抢汪汪赛跑红包！");
        if (ckBeanList == null || ckBeanList.size() == 0) {
            System.out.println("ck为空 停止！");
            return;
        }

        for (int i = 0; i < ckBeanList.size(); i++) {
            HelpCkBean ckBean = ckBeanList.get(i);
            ckBean.setTag("false");
            String ck = ckBean.getCkStr();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ExecutorService pl = Executors.newFixedThreadPool(20);
                    for (int j = 0; j < 300; j++) {
                        pl.execute(new Runnable() {
                            @Override
                            public void run() {
                                if ("true".equals(ckBean.getTag())) {
                                    ckBean.setState("success");
                                    return;
                                }
                                String result = sendPost("https://api.m.jd.com/", "functionId=runningPrizeDraw&body={\"linkId\":\"L-sOanK_5RJCz7I314FpnQ\",\"type\":1,\"level\":3}&t=1676021936464&appid=activities_platform&client=android&clientVersion=4.8.2", ck);
                                System.out.println(CKUtil.getCkPtPin(ck) + ":" + result);
                                try {
                                    if ("true".equals(ckBean.getTag())) {
                                        ckBean.setState("success");
                                        return;
                                    }
                                    JSONObject job = new JSONObject(result);
                                    String errMsg = job.optString("errMsg");
                                    ckBean.setState(errMsg);
                                    if ("success".equals(errMsg)) {
                                        ckBean.setTag("true");
                                        MainPage.addJtaStr(CKUtil.getCkPtPin(ck) + "-" + "已抢到");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                    System.out.println("300次发送完毕" + ck);
                }
            }).start();
        }
    }

    public static String sendGet(String url, String ck) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();

            // 设置通用的请求属性
            connection.setRequestProperty("host", "api.m.jd.com");
            connection.setRequestProperty("Accept", "application/json, text/plain, */*");
            connection.setRequestProperty("origin", "https://bnzf.jd.com");
            connection.setRequestProperty("referer", "https://pushgold.jd.com/");
            connection.setRequestProperty("User-Agent", "jdapp;android;11.6.0;;;appBuild/98666;ef/1;ep/%7B%22hdid%22%3A%22JM9F1ywUPwflvMIpYPok0tt5k9kW4ArJEU3lfLhxBqw%3D%22%2C%22ts%22%3A1675845353224%2C%22ridx%22%3A-1%2C%22cipher%22%3A%7B%22sv%22%3A%22CJK%3D%22%2C%22ad%22%3A%22CtvwDzLtDtvuCNVwZQUnEG%3D%3D%22%2C%22od%22%3A%22CJrvYzCnDQSjZNuyCy00CzvrBWS4ZJGjZNq4DQOmEQC4YJvs%22%2C%22ov%22%3A%22Ctu%3D%22%2C%22ud%22%3A%22CtvwDzLtDtvuCNVwZQUnEG%3D%3D%22%7D%2C%22ciphertype%22%3A5%2C%22version%22%3A%221.2.0%22%2C%22appname%22%3A%22com.jingdong.app.mall%22%7D;jdSupportDarkMode/0;Mozilla/5.0 (Linux; Android 10; TAS-AN00 Build/HUAWEITAS-AN00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/89.0.4389.72 MQQBrowser/6.2 TBS/046141 Mobile Safari/537.36");
            connection.setRequestProperty("Cookie", ck);
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.connect();
            if (connection.getResponseCode() == 200) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            } else if (connection.getResponseCode() == 403) {
                return "403EXE";
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String sendPost(String url, String param, String ck) {
        OutputStreamWriter osw = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性user-agent
            conn.setRequestProperty("Host", "api.m.jd.com");
            conn.setRequestProperty("Accept", "application/json, text/plain, */*");
            conn.setRequestProperty("referer", "https://prodev.m.jd.com/wq/active/2TDRii4WtRauWxDMPDzXHcNmGxj1/index.html?isMiniProgram=1&taskId=oZ2KtScXx8Vpeig4Mtwkh8sR9Zi&inviteId=SZ3bSmKebINRo-IlgTGaT&activityId=cYJY7nfSWC5Rm9CvVdkZ79fTfse&appId=wx8830763b00c18ac3&cookie=%7B%22wxapp_type%22%3A%2214%22%7D&fissionFrom=1");
            conn.setRequestProperty("user-agent", "Mozilla/5.0 (Linux; Android 10; TAS-AN00 Build/HUAWEITAS-AN00; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/86.0.4240.99 XWEB/4343 MMWEBSDK/20221011 Mobile Safari/537.36 MMWEBID/2310 MicroMessenger/8.0.30.2260(0x28001E51) WeChat/arm64 Weixin NetType/WIFI Language/zh_CN ABI/arm64 miniProgram/wx8830763b00c18ac3");
            conn.setRequestProperty("Cookie", ck);
            conn.setReadTimeout(15000);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            osw = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
            // 发送请求参数
            osw.write(param);
            // flush输出流的缓冲
            osw.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (osw != null) {
                    osw.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    private static ExecutorService pools = null;

    public static ExecutorService getInstance() {
        if (pools == null) {
            synchronized (ExecutorService.class) {
                if (pools == null) {
                    pools = Executors.newFixedThreadPool(20);
                }
            }
        }
        return pools;
    }
}
