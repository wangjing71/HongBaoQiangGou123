package wj;

public class HelpCkBean {
    private String ckStr;
    private String state; //状态 0白号 1帮砍机会用完 2火爆 未登录 verify
    private boolean isUsed = false;
    private String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public HelpCkBean(String ckStr, String state) {
        this.ckStr = ckStr;
        this.state = state;
    }

    public HelpCkBean(String ckStr) {
        this(ckStr, "0");
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }

    public String getCkStr() {
        return ckStr;
    }

    public void setCkStr(String ckStr) {
        this.ckStr = ckStr;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
