package wj.bean;

public class ConfigBean {
    private String quantumHost;
    private String appKey;
    private String proxyUrl = "";
    private int helpCount = 150;
    private int startHelpIndex = 0;
    private long helpDelay = 200;

    private int threadCount = 6;

    private int selIndex = 0;

    public String taskHH = "00";
    public String taskMM = "00";
    public String taskSS = "00";

    private String jinBiInviteCode;

    public int getSelIndex() {
        return selIndex;
    }

    public void setSelIndex(int selIndex) {
        this.selIndex = selIndex;
    }

    public String getJinBiInviteCode() {
        return jinBiInviteCode;
    }

    public void setJinBiInviteCode(String jinBiInviteCode) {
        this.jinBiInviteCode = jinBiInviteCode;
    }

    public String getTaskHH() {
        return taskHH;
    }

    public void setTaskHH(String taskHH) {
        this.taskHH = taskHH;
    }

    public String getTaskMM() {
        return taskMM;
    }

    public void setTaskMM(String taskMM) {
        this.taskMM = taskMM;
    }

    public String getTaskSS() {
        return taskSS;
    }

    public void setTaskSS(String taskSS) {
        this.taskSS = taskSS;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public long getHelpDelay() {
        return helpDelay;
    }

    public void setHelpDelay(long helpDelay) {
        this.helpDelay = helpDelay;
    }

    public int getStartHelpIndex() {
        return startHelpIndex;
    }

    public void setStartHelpIndex(int startHelpIndex) {
        this.startHelpIndex = startHelpIndex;
    }

    public int getHelpCount() {
        return helpCount;
    }

    public void setHelpCount(int helpCount) {
        this.helpCount = helpCount;
    }

    public String getQuantumHost() {
        return quantumHost;
    }

    public void setQuantumHost(String quantumHost) {
        this.quantumHost = quantumHost;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getProxyUrl() {
        return proxyUrl;
    }

    public void setProxyUrl(String proxyUrl) {
        this.proxyUrl = proxyUrl;
    }
}
