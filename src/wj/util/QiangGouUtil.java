package wj.util;

import org.json.JSONObject;
import wj.bean.HelpCkBean;
import wj.MainPage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static wj.MainPage.*;

public class QiangGouUtil {
    public static int THREAD_COUNT = 5;
    public static int empty = 0;
    public static int notEmpty = 0;

    public static void main(String[] args) {

    }

    public static void qiangHongbaoTask() {
        if (ClickUtil.isFastClick()) {
            return;
        }
        addJtaStr("开始抢大赢家红包！");
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
                    System.out.println("线程数:" + THREAD_COUNT);
                    ExecutorService pl = Executors.newFixedThreadPool(THREAD_COUNT);
                    for (int j = 0; j < 100; j++) {
                        pl.execute(new Runnable() {
                            @Override
                            public void run() {
                                if ("true".equals(ckBean.getTag())) {
                                    ckBean.setState("success");
                                    return;
                                }
                                String result = sendGet("https://api.m.jd.com/api?functionId=jxPrmtExchange_exchange&appid=cs_h5&t=1677031591387&channel=jxh5&cv=1.2.5&clientVersion=1.2.5&client=jxh5&uuid=83161358157305&cthr=1&loginType=2&h5st=&body={\"bizCode\":\"makemoneyshop\",\"ruleId\":\"da3fc8218d2d1386d3b25242e563acb8\",\"sceneval\":2,\"buid\":325,\"appCode\":\"ms2362fc9e\",\"time\":1994345945,\"signStr\":\"12ff2fa38d51f26a09eb4fa4f6ac2803\"}".replaceAll("1677031591387", System.currentTimeMillis() + "").replaceAll("da3fc8218d2d1386d3b25242e563acb8", moneys.get(selIndex).getId()).replaceAll("83161358157305", RandomUtils.getRandomPassword(14)), ck).trim();
                                if ("403EXE".equals(result)) {
                                    System.out.println(CKUtil.getCkPtPin(ck) + ":" + "IP已黑");
                                    return;
                                }
                                if ("true".equals(ckBean.getTag())) {
                                    ckBean.setState("success");
                                    return;
                                }
                                if (result.contains("success")) {
                                    MainPage.addJtaStr(CKUtil.getCkPtPin(ck) + "--已抢到" + moneys.get(selIndex).getTitle());
                                    ckBean.setTag("true");
                                    ckBean.setState("success");
                                    FileUtil.appendKeyToFile(CURRENT_PATH + "/log.txt", TimeUtil.getTime() + ":" + CKUtil.getCkPtPin(ck) + "---" + moneys.get(selIndex).getTitle());
                                    return;
                                }
                                if (result.length() == 0) {
                                    empty++;
                                    System.out.println(CKUtil.getCkPtPin(ck) + ":" + "空数据");
                                    System.out.println("【返回状态】" + "[非空]" + notEmpty + "[空]" + empty);
                                    ckBean.setState("空数据");
                                } else {
                                    try {
                                        JSONObject job = new JSONObject(result);
                                        String errMsg = job.optString("msg");
                                        System.out.println(CKUtil.getCkPtPin(ck) + ":" + errMsg);
                                        if (errMsg.length() == 0) {
                                            empty++;
                                            System.out.println("【返回状态】" + "[非空]" + notEmpty + "[空]" + empty);
                                        } else {
                                            notEmpty++;
                                            System.out.println("【返回状态】" + "[非空]" + notEmpty + "[空]" + empty);
                                        }
                                        ckBean.setState(errMsg);
                                    } catch (Exception e) {
//                                        e.printStackTrace();
                                        empty++;
                                        System.out.println("【返回状态】" + "[非空]" + notEmpty + "[空]" + empty);
                                        System.out.println(CKUtil.getCkPtPin(ck) + ":" + "解析异常");
                                        ckBean.setState("解析异常");
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
            connection.setRequestProperty("referer", "https://api.m.jd.com/api?functionId=makemoneyshop_queryPayMoneyDetail&appid=jdlt_h5&body=%7B%22activeId%22%3A%2263526d8f5fe613a6adb48f03%22%7D");
            connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1)");

            connection.setRequestProperty("Cookie", ck + ";  __jda=95931165.167725822481711580324048.1677258224817.1677258224817.1677258224817.148");
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
            System.out.println("发送GET请求出现异常2！" + e);
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
            HttpURLConnection connection = ProxyUtil.getHttpURLConnectionProxy(url);

            // 设置通用的请求属性
            connection.setRequestProperty("Host", "api.m.jd.com");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("Accept-Language", "zh-cn");
            connection.setRequestProperty("Referer", "https://api.m.jd.com/api?functionId=jxPrmtExchange_exchange&appid=cs_h5&t=1675701475905&channel=jxh5&cv=1.2.5&clientVersion=1.2.5&client=jxh5&uuid=13729923124741231&cthr=1&loginType=2&h5st=20230207003755902%3B95yyiy66t0jc1dt9%3Baf89e%3Btk02w72d91b3218n6So3TTfM7G3AhJgIiceg5avMqH4rT7a5ybQ%2F201r%2BwRk1u9SX2Ic0zWGifV9FOW2bxnT%2BwCInMzm%3Bd68fff75dcf3399fb39d6257b486855abd5f8888ccba7e8739508b4eae0ba6cf%3B400%3B1675701475902%3B22fac0cfc748d13d11407640ec6b0f4fb2e9076d1de03ca91db431dd1289786d1ff17236c40ebc9e7c008832ae688019a957059e30884cb09ec4e0608989c06e1e637f55dd7b66afbd256c5762949e3c83bbe0179a7fdaa6c2d27103305d510eb3409bd26c9575b31489243e4e8a7873d8cfc69a553fee569e3674d10673131c03212b989ff47b110fd383475ba6fca899302730f633c43b16492a77d46d9671794bbd621c13e51ad0f2f6e9873b149c6ffa4d0f695162609fe0992ab8c07c97&body=%7B%22bizCode%22%3A%22makemoneyshop%22%2C%22ruleId%22%3A%22b141ddd915d20f078d69f6910b02a60a%22%2C%22sceneval%22%3A2%2C%22buid%22%3A325%2C%22appCode%22%3A%22ms2362fc9e%22%2C%22time%22%3A1675701475905%2C%22signStr%22%3A%22a3bb877d89bcce0b3b86021ad2391913%22%7D");
            connection.setRequestProperty("User-Agent", "jdltapp;");
            connection.setRequestProperty("Cookie", ck + ";  __jda=95931165.167725822481711580324048.1677258224817.1677258224817.1677258224817.148".replaceAll("258224817", RandomUtils.getRandomNo(9)));
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
//            e.printStackTrace();
            System.out.println("发送GET请求出现异常3！" + e);
            if (e.toString().contains("Unable to tunnel through proxy")) {
                ProxyUtil.removeCurrentProxy();
            }
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString().trim().replaceAll(" ", "");
    }


    public static String get(String url) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            HttpURLConnection connection = ProxyUtil.getHttpURLConnection(url);

            // 设置通用的请求属性
            connection.setRequestProperty("Host", "api.m.jd.com");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("User-Agent", UserAgentUtil.randomUA());
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
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            }
        }
        return result.toString().trim().replaceAll(" ", "");
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
                    pools = Executors.newFixedThreadPool(1);
                }
            }
        }
        return pools;
    }
}
