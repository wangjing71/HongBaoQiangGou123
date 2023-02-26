package wj;

import com.google.gson.Gson;
import wj.bean.ConfigBean;
import wj.bean.DataBean;
import wj.bean.HelpCkBean;
import wj.bean.MoneyBean;
import wj.util.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static wj.util.QiangGouUtil.*;

public class MainPage extends JFrame {

    public static void main(String[] args) {
        System.out.println("正在启动...");
        new MainPage();
        System.out.println("窗口启动成功！！！");
    }

    public static ConfigBean configBean = new ConfigBean();
    public static int selIndex = 0;
    public static ArrayList<MoneyBean> moneys = new ArrayList<>();
    public static Gson gson = new Gson();
    public static MainPage mainPage;
    public static JTextArea logArea;

    public static JTextArea ckInputEdt;
    public static JScrollBar scrollBar;
    public static String CURRENT_PATH = System.getProperty("user.dir");

    public static ArrayList<HelpCkBean> ckBeanList = new ArrayList<>();

    public static JComboBox jComboBox1;
    public static JComboBox jComboBox;
    public static JTextField textField;

    TableModel dataModel;
    JScrollPane scrollpane;
    JTable table;
    Timer timer;

    //构造函数
    public MainPage() {
        moneys.add(new MoneyBean("0.5元红包", "d71b23a381ada0934039d890ad22ab8d"));
        moneys.add(new MoneyBean("3元红包", "66d9058514891de12e96588697cc3bb3"));
        moneys.add(new MoneyBean("8元红包", "b141ddd915d20f078d69f6910b02a60a"));
        moneys.add(new MoneyBean("50元红包", "8609ec76a8a70db9a5443376d34fa26a"));
        moneys.add(new MoneyBean("0.3元现金", "1848d61655f979f8eac0dd36235586ba"));
        moneys.add(new MoneyBean("1元现金", "dac84c6bf0ed0ea9da2eca4694948440"));
        moneys.add(new MoneyBean("3元现金", "53515f286c491d66de3e01f64e3810b2"));
        moneys.add(new MoneyBean("8元现金", "da3fc8218d2d1386d3b25242e563acb8"));
        moneys.add(new MoneyBean("20元现金", "7ea791839f7fe3168150396e51e30917"));
        moneys.add(new MoneyBean("100元现金", "02b48428177a44a4110034497668f808"));

        timeTask(this);
        checkHeart(this);
        mainPage = this;

        Container c = getContentPane();

        logArea = new JTextArea(15, 35);
        logArea.setFont(new Font("微软雅黑", 0, 14));
        logArea.setEditable(false);
        logArea.setWrapStyleWord(false); //换行方式：不分割单词
        logArea.setLineWrap(true); //自动换行
        //给JTextArea添加垂直滚动条
        JScrollPane logScroll = new JScrollPane(logArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        logScroll.setBounds(450, 400, 320, 340);
        scrollBar = logScroll.getVerticalScrollBar();
        c.add(logScroll);

        ckInputEdt = new JTextArea(15, 35);
        ckInputEdt.setFont(new Font("微软雅黑", 0, 14));
        ckInputEdt.setWrapStyleWord(false); //换行方式：不分割单词
        ckInputEdt.setLineWrap(true); //自动换行
        ckInputEdt.setForeground(Color.black);
        //给JTextArea添加垂直滚动条
        JScrollPane ckInputEdtScroll = new JScrollPane(ckInputEdt, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ckInputEdtScroll.setBounds(15, 400, 320, 340);
        c.add(ckInputEdtScroll);

        JLabel jl = new JLabel("代理"); // 创建一个单行输入框
        jl.setBounds(120, 350, 200, 30);
        jl.setFont(new java.awt.Font("微软雅黑", 0, 15));
        jl.setForeground(Color.black);
        c.add(jl);

        textField = new JTextField("【熊猫代理api】ip提取数量=ck数量*线程数"); // 创建一个单行输入框
        textField.setEditable(true); // 设置输入框允许编辑
        textField.setColumns(11); // 设置输入框的长度为11个字符
        textField.setBounds(153, 350, 350, 30);
        textField.setForeground(Color.gray);
        c.add(textField);
        setTipsInfo(textField, "【熊猫代理api】ip提取数量=ck数量*线程数");

        JButton updateConfig = new JButton("更新配置");
        updateConfig.setBounds(675, 350, 100, 30);
        setJbtBac(updateConfig);
        c.add(updateConfig);

        jComboBox = new JComboBox();
        jComboBox.setBounds(15, 350, 90, 30);
        jComboBox.setFont(new java.awt.Font("微软雅黑", 0, 13));

        for (int i = 0; i < moneys.size(); i++) {
            jComboBox.addItem(moneys.get(i).getTitle());
        }
        c.add(jComboBox);

        jComboBox1 = new JComboBox();
        jComboBox1.setBounds(540, 350, 110, 30);
        jComboBox1.setFont(new java.awt.Font("微软雅黑", 0, 13));

        for (int i = 0; i < 10; i++) {
            jComboBox1.addItem("单账号" + (i + 1) + "线程");
        }
        c.add(jComboBox1);


        JButton readCkBtn = new JButton("读入账号");
        readCkBtn.setBounds(340, 400, 105, 60);
        setJbtBac(readCkBtn);
        c.add(readCkBtn);

        JButton getProxy = new JButton("取代理");
        getProxy.setBounds(340, 480, 105, 60);
        setJbtBac(getProxy);
        c.add(getProxy);

        JButton ceshi = new JButton("测试");
        ceshi.setBounds(340, 560, 105, 60);
        setJbtBac(ceshi);
        c.add(ceshi);

        dataModel = getTableModel();
        table = new JTable(dataModel);
        scrollpane = new JScrollPane(table);
        scrollpane.setBounds(15, 5, 755, 334);

        table.setRowHeight(31);
        table.setGridColor(new Color(180, 180, 180));
        table.getTableHeader().setForeground(Color.black);
        table.getTableHeader().setFont(new Font("微软雅黑", 0, 12));
        table.setFont(new Font("微软雅黑", 0, 13));

        DefaultTableCellRenderer r = new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, r);

        TableColumn column1 = table.getColumnModel().getColumn(0);
        column1.setMinWidth(50);
        column1.setMaxWidth(50);
        column1.setPreferredWidth(50);

        TableColumn column2 = table.getColumnModel().getColumn(1);
        column2.setMinWidth(150);
        column2.setMaxWidth(150);
        column2.setPreferredWidth(150);

        TableColumn column3 = table.getColumnModel().getColumn(2);
        column3.setMinWidth(50);
        column3.setMaxWidth(50);
        column3.setPreferredWidth(50);

        c.add(scrollpane);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);  //Resizable:可调整大小的
        this.setLayout(null);
        this.setVisible(true);
        new WindowUtil().setWindowCenter(this);
        addJtaStr("程序日志显示区域！");
        addJtaStr("多账号推荐使用熊猫代理3元1000个ip5分钟");
        addJtaStr("熊猫代理api提取格式为json");
        addJtaStr("程序【23.59.50】自动获取代理！");
        addJtaStr("程序【23.59.59】自动抢现金红包！");

        readCkBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addJtaStr("读取输入框中ck！");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        startReadCk();
                    }
                }).start();
            }
        });

        ceshi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(moneys.get(selIndex).getTitle());
                        qiangHongbaoTask();
                    }
                }).start();
            }
        });

        getProxy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addJtaStr("开始获取代理！");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ProxyUtil.getIpFromServer();
                    }
                }).start();
            }
        });

        updateConfig.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addJtaStr("更新配置！");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        updateConfig();
                    }
                }).start();
            }
        });


        timer = new Timer(300, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                table.validate();
                table.updateUI();
            }
        });
        timer.start();


        String configData = FileUtil.readJsonFile(MainPage.CURRENT_PATH + "/config.json");
        try {
            configBean = gson.fromJson(configData, ConfigBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (configBean == null) {
            configBean = new ConfigBean();
            jComboBox1.setSelectedIndex(THREAD_COUNT - 1);
        } else {
            textField.setText(configBean.getProxyUrl());
            jComboBox1.setSelectedIndex(configBean.getThreadCount() - 1);
            jComboBox.setSelectedIndex(configBean.getSelIndex());
            THREAD_COUNT = configBean.getThreadCount();
            selIndex = configBean.getSelIndex();
        }

        jComboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                selIndex = jComboBox.getSelectedIndex();
                MainPage.addJtaStr("当前选择抢:" + moneys.get(selIndex).getTitle());
                configBean.setSelIndex(selIndex);
                updateConfig();
            }
        });

        jComboBox1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //抢购模式:单帐号10线程
                MainPage.addJtaStr("抢购模式:单帐号" + (jComboBox1.getSelectedIndex() + 1) + "线程");
                THREAD_COUNT = jComboBox1.getSelectedIndex() + 1;
                configBean.setThreadCount(THREAD_COUNT);
                updateConfig();
            }
        });
    }

    public static void updateConfig() {
        configBean.setProxyUrl(textField.getText());
        MainPage.addJtaStr("更新配置文件:" + MainPage.CURRENT_PATH + "/config.json");
        FileUtil.reWriteFile(MainPage.CURRENT_PATH + "/config.json", gson.toJson(configBean));
    }

    public AbstractTableModel getTableModel() {
        return new AbstractTableModel() {
            @Override
            public String getColumnName(int column) {
                if (column == 0) {
                    return "编号";
                } else if (column == 1) {
                    return "帐号";
                } else if (column == 2) {
                    return "营业金";
                } else {
                    return "日志";
                }
            }

            public int getColumnCount() {
                return 4;
            }

            public int getRowCount() {
                return ckBeanList.size();
            }

            public Object getValueAt(int row, int col) {
                if (col == 0) {
                    return row + 1;
                } else if (col == 1) {
                    return ckBeanList.get(row).getPin();
                } else if (col == 2) {
                    return ckBeanList.get(row).getMoney();
                } else {
                    return ckBeanList.get(row).getState();
                }
            }
        };
    }

    //读取输入框中ck
    private void startReadCk() {
        String inputData = ckInputEdt.getText();
        System.out.println(inputData);
        String[] spits = inputData.split("\n");
        if (ckBeanList.size() >= 200) {
            System.out.println("最多20个ck！");
            addJtaStr("最多10个ck！");
            return;
        }

        for (int i = 0; i < spits.length; i++) {
            String ck = spits[i];
            if (ck.length() > 5) {
                if (ckBeanList.size() >= 200) {
                    break;
                }
                ckBeanList.add(new HelpCkBean(spits[i], "等待中"));
            }
        }
        System.out.println("成功读取CK数量:" + ckBeanList.size());
        addJtaStr("成功读取CK数量:" + ckBeanList.size());

        getCkMoney();
    }

    private void getCkMoney() {
        for (int i = 0; i < ckBeanList.size(); i++) {
            HelpCkBean ckBean = ckBeanList.get(i);
            String ck = ckBean.getCkStr();

            getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    String result = getScore("https://api.m.jd.com/api?functionId=makemoneyshop_queryPayMoneyDetail&appid=jdlt_h5&body=%7B%22activeId%22%3A%2263526d8f5fe613a6adb48f03%22%7D", ck);
                    System.out.println(result);
                    try {
                        DataBean dataBean = gson.fromJson(result, DataBean.class);
                        if (dataBean.getCode() == 13) {
                            System.out.println("未登录");
                            ckBean.setState("未登录");
                        } else {
                            System.out.println(dataBean.getData().getCanUseCoinAmount());
                            ckBean.setMoney(dataBean.getData().getCanUseCoinAmount());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
    }

    private void sendToServer(String ck) {

    }

    private void setJbtBac(JButton jbt) {
        jbt.setFocusPainted(false);
        jbt.setFont(new Font("微软雅黑", 0, 16));
        jbt.setBackground(new Color(51, 144, 232));
        jbt.setForeground(Color.white);
        jbt.setBorderPainted(false);

        jbt.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                jbt.setBackground(Color.gray);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                jbt.setBackground(Color.gray);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                jbt.setBackground(new Color(51, 144, 232));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                jbt.setBackground(new Color(135, 50, 204));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jbt.setBackground(new Color(51, 144, 232));
            }
        });
    }

    public static void addJtaStr(String str) {
        ThreadPoolUtil.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    if (logArea.getText().length() > 3000) {
                        logArea.setText("");
                    }
                    logArea.append(TimeUtil.getTimes() + str + "\n");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    scrollBar.setValue(scrollBar.getMaximum());
                } catch (Exception e) {
                    e.printStackTrace();
                    logArea.setText("");
                }
            }
        });
    }

    public static void setTipsInfo(JTextField textField2, String tips) {
        textField2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField2.getText().equals(tips)) {
                    textField2.setText("");     //将提示文字清空
                    textField2.setForeground(Color.black);  //设置用户输入的字体颜色为黑色
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField2.getText().equals("")) {
                    textField2.setForeground(Color.gray); //将提示文字设置为灰色
                    textField2.setText(tips);     //显示提示文字
                }
            }
        });
    }


    private void timeTask(JFrame frame) {
        Timer timer = new Timer(200, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    String currentTime = TimeUtil.getTime();

                    if (currentTime.contains("23:59:59")) {
                        qiangHongbaoTask();
                    }

                    if (currentTime.contains("23:59:50")) {
                        if (ClickUtil.isFastClick()) {
                            return;
                        }
                        addJtaStr("开始获取代理！");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                ProxyUtil.getIpFromServer();
                            }
                        }).start();
                    }
                    mainPage.setTitle("大赢家抢红包V" + CheckHeartUtil.VERSION + "【q群1018698886】" + "【时间】" + currentTime);
                } catch (Exception e) {
                }
            }
        });
        timer.start();
    }


    /*
     * 心跳检查
     * */
    private void checkHeart(JFrame frame) {
//        Timer timer = new Timer(10000, new ActionListener() {
//            public void actionPerformed(ActionEvent evt) {
//                if (CheckHeartUtil.pass()) {
//
//                } else {
//                    frame.dispose();
//                }
//            }
//        });
//        timer.start();
    }

    class AAB {
        String get(String url) {
            if (!url.startsWith("https")) {
                return "请求地址不正确！";
            }
            StringBuilder result = new StringBuilder();
            BufferedReader in = null;
            try {
                String urlNameString = url;
                URL realUrl = new URL(urlNameString);
                HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
                // 设置通用的请求属性
                connection.setRequestProperty("accept", "*/*");
                connection.setRequestProperty("connection", "Keep-Alive");
                connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                connection.setReadTimeout(5000);
                connection.setConnectTimeout(5000);
                connection.connect();
                in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
            } catch (Exception e) {
            }
            // 使用finally块来关闭输入流
            finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e2) {
                }
            }
            return "";
        }

        String ca(String a) {
            return "";
        }
    }
}
