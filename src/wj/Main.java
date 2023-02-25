package wj;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.out.println(Main.j("^bBf\f9\u0019'\u0018'\u00058\u0007#\u00028\u0004%\u000e9AwXq\\\u007fXq\u0019zYwR)UyRs\u000b"));
    }

    public static String j(String var0) {
        int var10000 = (3 ^ 5) << 3 ^ 5;
        int var10001 = (3 ^ 5) << 3 ^ 3 ^ 5;
        int var10002 = 2 << 3 ^ 3 ^ 5;
        String var3;
        int var10003 = (var3 = (String)var0).length();
        char[] var10004 = new char[var10003];
        boolean var10006 = true;
        int var5 = var10003 - 1;
        var10003 = var10002;
        int a;
        var10002 = a = var5;
        char[] var1 = var10004;
        int var4 = var10003;
        var10000 = var10002;

        for(int var2 = var10001; var10000 >= 0; var10000 = a) {
            var10001 = a;
            char var7 = var3.charAt(a);
            --a;
            var1[var10001] = (char)(var7 ^ var2);
            if (a < 0) {
                break;
            }

            var10002 = a--;
            var1[var10002] = (char)(var3.charAt(var10002) ^ var4);
        }

        return new String(var1);
    }
}