package wj.util;

public class CKUtil {
    /*
     * 截图CK的 pt_pin字段
     * */
    public static String getCkPtPin(String ck) {
        try {
            String pin = ck.substring(ck.indexOf("pt_pin=") + 7);
            String pinReal = pin.substring(0, pin.indexOf(";"));
            return pinReal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "getPinExe";
    }

    public String getPin(String ck) {
        try {
            String pin = ck.substring(ck.indexOf("pt_pin=") + 7);
            String pinReal = pin.substring(0, pin.indexOf(";"));
            return pinReal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "getPinExe";
    }
}
