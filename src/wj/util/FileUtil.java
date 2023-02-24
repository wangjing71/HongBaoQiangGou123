package wj.util;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author wangjing
 * Date 2022/1/18
 * Description
 */
public class FileUtil {
    public static String userLogPath = "/root/userLog/";

    public synchronized static void appendKeyToFile(String path, String ck) {
        FileUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                FileWriter writer = null;
                try {
                    // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                    writer = new FileWriter(path, true);
                    writer.write(ck + "\r\n");
                } catch (Exception e) {
//                    e.printStackTrace();
                } finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (Exception e) {
//                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public synchronized static void appendKeyToFile1(String path, String ck) {
        FileUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                FileWriter writer = null;
                try {
                    // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
                    writer = new FileWriter(path, true);
                    writer.write(ck);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (writer != null) {
                            writer.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public static ArrayList<String> startReadFile(String path) {

        ArrayList<String> ckList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            String ck = null;
            while ((ck = br.readLine()) != null) {//使用readLine方法，一次读一行
                ckList.add(ck);
            }
            br.close();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return ckList;
    }

    private static ExecutorService fixedThreadPool = null;

    // 双重检查
    public static ExecutorService getInstance() {
        if (fixedThreadPool == null) {
            synchronized (ExecutorService.class) {
                if (fixedThreadPool == null) {
                    fixedThreadPool = Executors.newFixedThreadPool(2);
                }
            }
        }
        return fixedThreadPool;
    }

    public static String readJsonFile(String qlConfigPath) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(qlConfigPath));

            String line = null;
            while ((line = br.readLine()) != null) {//使用readLine方法，一次读一行
                result = result + line.trim();
            }
            br.close();
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return result;
    }

    public synchronized static void reWriteFile(String path, String ck) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path, false);
            writer.write(ck);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
