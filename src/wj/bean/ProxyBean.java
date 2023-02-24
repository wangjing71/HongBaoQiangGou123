package wj.bean;

import java.util.ArrayList;

/**
 * author wangjing
 * Date 2022/3/25
 * Description
 */
public class ProxyBean {

    private String code;
    private String msg;
    private ArrayList<ProxyIp> obj;
    private int errno;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<ProxyIp> getObj() {
        return obj;
    }

    public void setObj(ArrayList<ProxyIp> obj) {
        this.obj = obj;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public static class ProxyIp {
        private String port;
        private String ip;

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }
    }
}
