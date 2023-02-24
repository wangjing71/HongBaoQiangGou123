package wj.util;

import com.google.gson.Gson;
import wj.MainPage;
import wj.bean.ProxyBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * 标准代理工具类
 * */
public class ProxyUtil {
    public static ArrayList<ProxyBean.ProxyIp> proxyList = new ArrayList<>();
    public static Gson gson = new Gson();
    public static HashMap<String, ProxyBean.ProxyIp> ProxyIpMapList = new HashMap<>();

    public static long lastGetTime;

    public static HttpURLConnection getHttpURLConnectionProxy(String url) throws IOException {
        if (MainPage.configBean.getProxyUrl() == null || MainPage.configBean.getProxyUrl().length() == 0 || !MainPage.configBean.getProxyUrl().startsWith("http")) {
            return getHttpURLConnection(url);
        }

        ProxyBean.ProxyIp obj = ProxyUtil.getProxyIp();
        if (obj == null) {
            return getHttpURLConnection(url);
        } else {
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(obj.getIp().trim(), Integer.parseInt(obj.getPort().trim())));
            return (HttpURLConnection) new URL(url).openConnection(proxy);
        }
    }

    public static HttpURLConnection getHttpURLConnection(String url) throws IOException {
        URL realUrl = new URL(url);
        return (HttpURLConnection) realUrl.openConnection();
    }

    public static ProxyBean.ProxyIp getProxyIp() {
        String threadName = Thread.currentThread().getName();
        ProxyBean.ProxyIp proxyIp = ProxyIpMapList.get(threadName);
        if (proxyIp == null) {
            System.out.println("代理为空 开始获取代理。");
            if (proxyList.size() == 0) {
                getIpFromServer();
            }
            try {
                ProxyBean.ProxyIp obj = proxyList.remove(0);
                System.out.println("proxyList:size->" + proxyList.size());
                System.out.println("代理IP:" + obj.getIp() + ":" + obj.getPort());
                ProxyIpMapList.put(threadName, obj);
                return obj;
            } catch (Exception e) {
//                e.printStackTrace();
            }
            return null;
        } else {
            return proxyIp;
        }
    }

    public synchronized static void getIpFromServer() {
        if (MainPage.configBean.getProxyUrl() == null || MainPage.configBean.getProxyUrl().length() == 0 || !MainPage.configBean.getProxyUrl().startsWith("http")) {
            System.out.println("http代理地址未填写！");
            return;
        }
        if (proxyList.size() == 0) {
            if (System.currentTimeMillis() - lastGetTime < 1000) {
                System.out.println("距离上次获取代理间隔没有超过一秒！休眠一秒后在获取！");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("[类型1]正在获取代理IP,代理IP地址为:" + MainPage.configBean.getProxyUrl());
            String proxyResult = get(MainPage.configBean.getProxyUrl());
            System.out.println(proxyResult);
            lastGetTime = System.currentTimeMillis();
            try {
                ProxyBean proxyBean = gson.fromJson(proxyResult, ProxyBean.class);
                ArrayList<ProxyBean.ProxyIp> objList = proxyBean.getObj();
                proxyList.addAll(objList);
                System.out.println("获取代理成功,代理ip池大小为:->" + proxyList.size());
                MainPage.addJtaStr("获取代理成功,代理ip池大小为:->" + proxyList.size());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("获取代理发生异常");
            }
        } else {
            System.out.println("[类型1]代理池不为空,跳过获取代理！");
        }
    }

    public static String get(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("User-Agent", UserAgentUtil.randomUA());
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(10000);
            connection.connect();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println(url + "发送GET请求出现异常1！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    public static void removeCurrentProxy() {
        try {
            String threadName = Thread.currentThread().getName();
            ProxyBean.ProxyIp bean = ProxyIpMapList.remove(threadName);
            System.out.println("请求数据异常移除当前代理IP->" + bean.getIp());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
