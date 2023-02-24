package wj.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MachineCodeUtil {
    public static final String LINUX_OS_NAME = "LINUX";
    public static final String SYSTEM_PROPERTY_OS_NAME = "os.name";


    public static void main(String[] args) throws IOException {
        System.out.println("解码:"+getThisMachineCodeMd5());
    }
    public static String  getThisMachineCodeMd5(){
        return encode(getThisMachineCode(),"$$$$****");
    }
    /**
     * java 实现Md5
     */
    /**
     * 直接加密
     *
     * @param data 要加密的数据
     * @return 加密后的结果
     */
    public static String encode(String data) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = messageDigest.digest();
            return new BigInteger(1, bytes).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 加盐或者密钥的MD5
     *
     * @param data 要加密的数据
     * @param salt 盐或者密钥
     * @return 加密后的结果
     */
    public static String encode(String data, String salt) {
        // TODO 此处可以加一些特殊的处理
        return encode(data + salt);
    }

    /**
     * 获取机器唯一识别码（CPU ID + BIOS UUID）
     *
     * @return 机器唯一识别码
     */
    public static String getThisMachineCode() {
        try {
            return getCpuId() + getBiosUuid();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获取当前系统CPU序列，可区分linux系统和windows系统
     */
    public static String getCpuId() throws IOException {
        String cpuId;
        // 获取当前操作系统名称
        String os = System.getProperty(SYSTEM_PROPERTY_OS_NAME);
        os = os.toUpperCase();

        if (LINUX_OS_NAME.equals(os)) {
            cpuId = getLinuxDmidecodeInfo("dmidecode -t processor | grep 'ID'", "ID", ":");
        } else {
            cpuId = getWindowsCpuId();
        }
        return cpuId.toUpperCase().replace(" ", "");
    }

    /**
     * 获取linux系统
     * dmidecode
     * 命令的信息
     */
    public static String getLinuxDmidecodeInfo(String cmd, String record, String symbol) throws IOException {
        String execResult = executeLinuxCmd(cmd);
        String[] infos = execResult.split("\n");
        for (String info : infos) {
            info = info.trim();
            if (info.contains(record)) {
                info.replace(" ", "");
                String[] sn = info.split(symbol);
                return sn[1];
            }
        }
        return null;
    }


    /**
     * 执行Linux 命令
     *
     * @param cmd Linux 命令
     * @return 命令结果信息
     * @throws IOException 执行命令期间发生的IO异常
     */
    public static String executeLinuxCmd(String cmd) throws IOException {
        Runtime run = Runtime.getRuntime();
        Process process;
        process = run.exec(cmd);
        InputStream processInputStream = process.getInputStream();
        StringBuilder stringBuilder = new StringBuilder();
        byte[] b = new byte[8192];
        for (int n; (n = processInputStream.read(b)) != -1; ) {
            stringBuilder.append(new String(b, 0, n));
        }
        processInputStream.close();
        process.destroy();
        return stringBuilder.toString();
    }

    /**
     * 获取windows系统CPU序列
     */
    public static String getWindowsCpuId() throws IOException {
        Process process = Runtime.getRuntime().exec(
                new String[]{"wmic", "cpu", "get", "ProcessorId"});
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        sc.next();
        String serial = sc.next();
        return serial;
    }

    /**
     * 获取 BIOS UUID
     *
     * @return BIOS UUID
     * @throws IOException 获取BIOS UUID期间的IO异常
     */
    public static String getBiosUuid() throws IOException {
        String cpuId;
        // 获取当前操作系统名称
        String os = System.getProperty("os.name");
        os = os.toUpperCase();

        if ("LINUX".equals(os)) {
            cpuId = getLinuxDmidecodeInfo("dmidecode -t system | grep 'UUID'", "UUID", ":");
        } else {
            cpuId = getWindowsBiosUUID();
        }
        return cpuId.toUpperCase().replace(" ", "");
    }

    /**
     * 获取windows系统 bios uuid
     *
     * @return
     * @throws IOException
     */
    public static String getWindowsBiosUUID() throws IOException {
        Process process = Runtime.getRuntime().exec(
                new String[]{"wmic", "path", "win32_computersystemproduct", "get", "uuid"});
        process.getOutputStream().close();
        Scanner sc = new Scanner(process.getInputStream());
        sc.next();
        String serial = sc.next();
        return serial;
    }
}

