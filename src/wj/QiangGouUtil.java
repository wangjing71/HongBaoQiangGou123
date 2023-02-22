package wj;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static wj.MainPage.ckBeanList;

public class QiangGouUtil {
    public static void qiangHongbaoTask() {
        if (ClickUtil.isFastClick()) {
            return;
        }
        System.out.println("开始抢大赢家红包！");
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
                    ExecutorService pl = Executors.newFixedThreadPool(1);
                    for (int j = 0; j < 300; j++) {
                        pl.execute(new Runnable() {
                            @Override
                            public void run() {
                                if ("true".equals(ckBean.getTag())) {
                                    ckBean.setState("success");
                                    return;
                                }
                                String result = sendGet("https://api.m.jd.com/api?functionId=jxPrmtExchange_exchange&appid=cs_h5&t=1677031591387&channel=jxh5&cv=1.2.5&clientVersion=1.2.5&client=jxh5&uuid=83161358157305&cthr=1&loginType=2&h5st=&body={\"bizCode\":\"makemoneyshop\",\"ruleId\":\"da3fc8218d2d1386d3b25242e563acb8\",\"sceneval\":2,\"buid\":325,\"appCode\":\"ms2362fc9e\",\"time\":1994345945,\"signStr\":\"12ff2fa38d51f26a09eb4fa4f6ac2803\"}"
                                                .replaceAll("1677031591387", System.currentTimeMillis() + "")
                                                .replaceAll("83161358157305", RandomUtils.getRandomPassword(14)),
                                        ck);
                                System.out.println(result);
                                if (result.length() == 0) {
                                    System.out.println(CKUtil.getCkPtPin(ck) + ":" + "返回空数据");
                                    ckBean.setState("空数据");
                                } else {
                                    try {
                                        JSONObject job = new JSONObject(result);
                                        String errMsg = job.optString("msg");
                                        System.out.println(CKUtil.getCkPtPin(ck) + ":" + errMsg);

                                        ckBean.setState(errMsg);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        });
                    }
                    System.out.println("300次发送完毕" + ck);
                }
            }).start();
        }
    }

    public static String getScore(String url, String ck) {
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
            connection.setRequestProperty("User-Agent", UserAgentUtil.randomUserAgent());
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

    public static String sendGet(String url, String ck) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();

            // 设置通用的请求属性
            connection.setRequestProperty("Host", "api.m.jd.com");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Accept-Language", "zh-cn");
            connection.setRequestProperty("Referer", "https://wqs.jd.com");
            connection.setRequestProperty("User-Agent", UserAgentUtil.randomUserAgent());
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
