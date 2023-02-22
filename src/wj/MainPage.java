package wj;

import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static wj.QiangGouUtil.*;


public class MainPage extends JFrame {
    public static MainPage mainPage;
    public static JTextArea logArea;

    public static JTextArea ckInputEdt;
    public static JScrollBar scrollBar;
    public static String CURRENT_PATH = System.getProperty("user.dir");

    public static ArrayList<HelpCkBean> ckBeanList = new ArrayList<>();

    TableModel dataModel;
    JScrollPane scrollpane;
    JTable table;
    Timer timer;

    //构造函数
    public MainPage() {
        timeTask(this);
        mainPage = this;

        Container c = getContentPane();

        logArea = new JTextArea(15, 35);
        logArea.setFont(new Font("微软雅黑", 1, 13));
        logArea.setEditable(false);
        logArea.setWrapStyleWord(false); //换行方式：不分割单词
        logArea.setLineWrap(true); //自动换行
        //给JTextArea添加垂直滚动条
        JScrollPane logScroll = new JScrollPane(logArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        logScroll.setBounds(450, 400, 320, 340);
        scrollBar = logScroll.getVerticalScrollBar();
        c.add(logScroll);

        ckInputEdt = new JTextArea(15, 35);
        ckInputEdt.setFont(new Font("正楷", 1, 15));
        ckInputEdt.setWrapStyleWord(false); //换行方式：不分割单词
        ckInputEdt.setLineWrap(true); //自动换行
        //给JTextArea添加垂直滚动条
        JScrollPane ckInputEdtScroll = new JScrollPane(ckInputEdt, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        ckInputEdtScroll.setBounds(15, 400, 320, 340);
        c.add(ckInputEdtScroll);

        JButton readCkBtn = new JButton("读入账号");
        readCkBtn.setBounds(340, 400, 105, 60);
        setJbtBac(readCkBtn);
        c.add(readCkBtn);

        JButton ceshi = new JButton("测试");
        ceshi.setBounds(340, 480, 105, 60);
        setJbtBac(ceshi);
        c.add(ceshi);

        dataModel = getTableModel();
        table = new JTable(dataModel);
        scrollpane = new JScrollPane(table);
        scrollpane.setBounds(15, 5, 755, 376);

        table.setRowHeight(35);
        table.setGridColor(new Color(180, 180, 180));
        table.getTableHeader().setForeground(Color.black);
        table.getTableHeader().setFont(new Font("微软雅黑", 1, 13));
        table.setFont(new Font("微软雅黑", 1, 13));

        DefaultTableCellRenderer r=new DefaultTableCellRenderer();
        r.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class,r);

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
        WindowUtil.setWindowCenter(this);

        addJtaStr("程序日志显示区域！");
//        addJtaStr("程序当前运行目录:" + MainPage.CURRENT_PATH);
        addJtaStr("抢购模式:单帐号20线程");
        addJtaStr("请在左边输入账号！");

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
                addJtaStr("开始抢红包！");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        qiangHongbaoTask();
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
        if (ckBeanList.size() >= 20) {
            System.out.println("最多20个ck！");
            addJtaStr("最多10个ck！");
            return;
        }

        for (int i = 0; i < spits.length; i++) {
            String ck = spits[i];
            if (ck.length() > 5) {
                if (ckBeanList.size() >= 20) {
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
                    String result = sendGet("https://api.m.jd.com/?functionId=runningPageHome&body={%22linkId%22:%22L-sOanK_5RJCz7I314FpnQ%22,%22isFromJoyPark%22:false,%22joyLinkId%22:%22%22}&t=1676533172999&appid=activities_platform&client=android&clientVersion=4.8.2&cthr=1&uuid=2393667303366393-4603536646561393&build=2385&screen=360*780&networkType=UNKNOWN&d_brand=HUAWEI&d_model=TAS-AN00&lang=zh_CN&osVersion=10&partner=huawei&eid=eidA1f848122aesfwrFzq9FGSh6w8U4xvTMphsKxdTD8IPLDIQaqvVBkIzdYhFNHgzMNh2oyBI0yXSji04Z73TTmCZOP%2BomTs9BX4YZVnYFYmXicBqE2", ck);
                    System.out.println(result);
                    try {
                        JSONObject job = new JSONObject(result);
                        String code = job.optString("code");
                        if("1000".equals(code)){
                            System.out.println("未登录");
                            ckBean.setState("未登录");
                        }else{
                            String prizeValue = job.optJSONObject("data").optJSONObject("runningHomeInfo").optString("prizeValue");
                            System.out.println(prizeValue);
                            ckBean.setMoney(prizeValue);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private void setJbtBac(JButton jbt) {
        jbt.setFocusPainted(false);
        jbt.setFont(new Font("微软雅黑", 1, 16));
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

    public static void main(String[] args) {
        new MainPage();
    }


    private void timeTask(JFrame frame) {
        Timer timer = new Timer(200, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                try {
                    String currentTime = TimeUtil.getTime();

                    if (currentTime.contains("23:59:59")) {
                        qiangHongbaoTask();
                    }
                    mainPage.setTitle("大赢家抢红包" + "--【时间】" + currentTime);
                } catch (Exception e) {
                }
            }
        });
        timer.start();
    }

}
