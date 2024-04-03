package com.cn.jmw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

//使用GUI开发一个秒表要求STOP的时候可以打印时间到控制台
public class 秒表 extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 200;
    private static final int INTERVAL = 1000;
    private static final String TITLE = "算法计时器";
    private static final String START = "Start";
    private static final String STOP = "Stop";
    private static final String RESET = "Reset";
    private static final String PRINT = "Print";
    private static final String FILE = "File";
    private static final String EXIT = "Exit";

    private static final String FN = "文件名称";

    private static final String CHOOSE = "Choose";
    private static String FILENAME = "time.txt";
    private static final String FILEPATH =
            "C:\\Users\\wjm\\IdeaProjects\\spring-boot-demo\\algorithm\\src\\main\\java\\com\\cn\\jmw\\华为C卷\\一百分\\";

    private Timer timer;
    private int hour;
    private int minute;
    private int second;
    private boolean isRunning;
    private boolean isPrinted;
    private JButton startButton;
    private JButton stopButton;
    private JButton resetButton;
    private JButton printButton;
    private JButton fileButton;
    private JButton exitButton;
    //选择
    private JButton chooseButton;
    private JLabel timeLabel;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem exitItem;
    private File file;

    public 秒表() {
        setTitle(TITLE);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2, 1)); // 使用网格布局，2行1列
//        setLayout(new FlowLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        hour = 0;
        minute = 0;
        second = 0;
        isRunning = false;
        isPrinted = false;

        timer = new Timer(INTERVAL, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                second++;
                if (second == 60) {
                    second = 0;
                    minute++;
                }
                if (minute == 60) {
                    minute = 0;
                    hour++;
                }
                timeLabel.setText(String.format("%02d:%02d:%02d", hour, minute, second));
            }
        });

        //增加一个选择文件的功能，可以修改一个static变量FILENAME,并且默认在这个路径下FILEPATH
        chooseButton = new JButton(CHOOSE);
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();

                // 设置默认打开的目录
                File defaultDirectory = new File(FILEPATH);
                fileChooser.setCurrentDirectory(defaultDirectory);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fileChooser.showOpenDialog(null);
                file = fileChooser.getSelectedFile();
                //这里只是修改变量FILENAME名称
                FILENAME = file.getName();

                //刷新fileMenu
                fileMenu.setText(FN + ":" + FILENAME);
            }
        });

        startButton = new JButton(START);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    timer.start();
                    isRunning = true;
                }
            }
        });

        stopButton = new JButton(STOP);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isRunning) {
                    timer.stop();
                    isRunning = false;
                }

                file = new File(FILEPATH + FILENAME);
                try {
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    //先读取文件查看有没有一个多行以/**开头 中间每一行开头都有一个*，最后以*/结尾
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    int lineNumber = 0;
                    while ((line = reader.readLine()) != null) {
                        lineNumber++;
                        if (line.contains("public class")) {
                            // 找到了包含 public class 的行
                            System.out.println("找到了包含 public class 的行：" + line + "，位于第 " + lineNumber + " 行");
                            break; // 找到后退出循环
                        }
                    }

                    //如果lineNumber等于0,就说明没有找到包含 public class 的行，提示到GUI上
                    if (lineNumber == 0) {
                        JOptionPane.showMessageDialog(null, "没有找到包含 public class 的行");
                        return;
                    }
                    reader.close();

                    BufferedReader readerRe = new BufferedReader(new FileReader(file));
                    line = readerRe.readLine();
                    //在1-lineNumber行，检测有没有这个class注释 /** ... *  ...*/
                    boolean classCommented = false;
                    // 继续读取后续行，检查是否存在多行注释
                    boolean isComment = false;
                    int temp = lineNumber;
                    while ((line = readerRe.readLine()) != null) {
                        temp++;
                        line = line.trim();
                        if (line.startsWith("/**")) {
                            isComment = true;
                            // 如果是多行注释的起始行，检查是否存在 *
                            if (!line.endsWith("*/")) {
                                classCommented = true;
                            }
                        }
                    }
                    readerRe.close();

                    //存在类注释很好，就在在类注释中*/结束行 前一行添加一行注释
                    if (classCommented) {
                        // 读取文件内容
                        StringBuilder content = new StringBuilder();
                        BufferedReader readerAdd = new BufferedReader(new FileReader(file));
                        while ((line = readerAdd.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                        readerAdd.close();

                        // 在类注释中*/前一行添加一行注释
                        String[] lines = content.toString().split("\n");
                        StringBuilder newContent = new StringBuilder();
                        for (int i = 0; i < lines.length; i++) {
                            newContent.append(lines[i]).append("\n");
                            if (lines[i].trim().startsWith("/**")) {
                                //几年几月几号时分秒，展示当前时间
                                LocalDateTime now = LocalDateTime.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                String dateTimeString = now.format(formatter);
                                newContent.append(" *╭—————————————————————————————╮\n");
                                newContent.append(" *│ 当前时间：" + dateTimeString + " │\n");
                                newContent.append(" *│ 完成时间：" + String.format("%02d:%02d:%02d", hour, minute, second) + "            │\n");
                                newContent.append(" *╰—————————————————————————————╯\n");
                            }
                        }

                        // 写入文件
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                        writer.write(newContent.toString());
                        writer.close();
                    } else {
                        //不存在类注释，就在第一行添加一个类注释
                        // 读取文件内容
                        StringBuilder content = new StringBuilder();
                        BufferedReader readerAdd = new BufferedReader(new FileReader(file));
                        while ((line = readerAdd.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                        readerAdd.close();

                        // 在lineNumber行添加类注释
                        String[] lines = content.toString().split("\n");
                        StringBuilder newContent = new StringBuilder();
                        for (int i = 0; i < lines.length; i++) {
                            if (i == lineNumber - 1) {
                                newContent.append("/**\n");
                                newContent.append(" * \n");
                                newContent.append(" */\n");
                            }
                            newContent.append(lines[i]).append("\n");
                        }

                        // 写入文件
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                        writer.write(newContent.toString());
                        writer.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                if (!isRunning) {
                    hour = 0;
                    minute = 0;
                    second = 0;
                    timeLabel.setText(String.format("%02d:%02d:%02d", hour, minute, second));
                    isPrinted = false;
                }
            }
        });

        resetButton = new JButton(RESET);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    hour = 0;
                    minute = 0;
                    second = 0;
                    timeLabel.setText(String.format("%02d:%02d:%02d", hour, minute, second));
                    isPrinted = false;
                }
            }
        });

        printButton = new JButton(PRINT);
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isPrinted) {
                    System.out.println(String.format("%02d:%02d:%02d", hour, minute, second));
                    isPrinted = true;
                }
            }
        });

        fileButton = new JButton(FILE);
        fileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                file = new File(FILEPATH + FILENAME);
                try {
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    //先读取文件查看有没有一个多行以/**开头 中间每一行开头都有一个*，最后以*/结尾
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    int lineNumber = 0;
                    while ((line = reader.readLine()) != null) {
                        lineNumber++;
                        if (line.contains("public class")) {
                            // 找到了包含 public class 的行
                            System.out.println("找到了包含 public class 的行：" + line + "，位于第 " + lineNumber + " 行");
                            break; // 找到后退出循环
                        }
                    }

                    //如果lineNumber等于0,就说明没有找到包含 public class 的行，提示到GUI上
                    if (lineNumber == 0) {
                        JOptionPane.showMessageDialog(null, "没有找到包含 public class 的行");
                        return;
                    }
                    reader.close();

                    BufferedReader readerRe = new BufferedReader(new FileReader(file));
                    line = readerRe.readLine();
                    //在1-lineNumber行，检测有没有这个class注释 /** ... *  ...*/
                    boolean classCommented = false;
                    // 继续读取后续行，检查是否存在多行注释
                    boolean isComment = false;
                    int temp = lineNumber;
                    while ((line = readerRe.readLine()) != null) {
                        temp++;
                        line = line.trim();
                        if (line.startsWith("/**")) {
                            isComment = true;
                            // 如果是多行注释的起始行，检查是否存在 *
                            if (!line.endsWith("*/")) {
                                classCommented = true;
                            }
                        }
                    }
                    readerRe.close();

                    //存在类注释很好，就在在类注释中*/结束行 前一行添加一行注释
                    if (classCommented) {
                        // 读取文件内容
                        StringBuilder content = new StringBuilder();
                        BufferedReader readerAdd = new BufferedReader(new FileReader(file));
                        while ((line = readerAdd.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                        readerAdd.close();

                        // 在类注释中*/前一行添加一行注释
                        String[] lines = content.toString().split("\n");
                        StringBuilder newContent = new StringBuilder();
                        for (int i = 0; i < lines.length; i++) {
                            newContent.append(lines[i]).append("\n");
                            if (lines[i].trim().startsWith("/**")) {
                                //几年几月几号时分秒，展示当前时间
                                LocalDateTime now = LocalDateTime.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                String dateTimeString = now.format(formatter);
                                newContent.append(" *╭——————————————————————————————╮\n");
                                newContent.append(" *│ 当前时间：" + dateTimeString + "  │\n");
                                newContent.append(" *│ 完成时间：" + String.format("%02d:%02d:%02d", hour, minute, second) + "             │\n");
                                newContent.append(" *╰——————————————————————————————╯\n");
                            }
                        }

                        // 写入文件
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                        writer.write(newContent.toString());
                        writer.close();
                    } else {
                        //不存在类注释，就在第一行添加一个类注释
                        // 读取文件内容
                        StringBuilder content = new StringBuilder();
                        BufferedReader readerAdd = new BufferedReader(new FileReader(file));
                        while ((line = readerAdd.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                        readerAdd.close();

                        // 在lineNumber行添加类注释
                        String[] lines = content.toString().split("\n");
                        StringBuilder newContent = new StringBuilder();
                        for (int i = 0; i < lines.length; i++) {
                            if (i == lineNumber - 1) {
                                newContent.append("/**\n");
                                newContent.append(" * \n");
                                newContent.append(" */\n");
                            }
                            newContent.append(lines[i]).append("\n");
                        }

                        // 写入文件
                        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                        writer.write(newContent.toString());
                        writer.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        exitButton = new JButton(EXIT);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        timeLabel = new JLabel(String.format("%02d:%02d:%02d", hour, minute, second));
        timeLabel.setHorizontalAlignment(JLabel.CENTER); // 居中对齐

        menuBar = new JMenuBar();
        fileMenu = new JMenu(FN + ":" + FILENAME);
        exitItem = new JMenuItem(EXIT);
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        // 按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3)); // 使用网格布局，1行3列
        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(chooseButton);
        add(buttonPanel);

//        add(startButton);
//        add(stopButton);
//        add(resetButton);
//        add(printButton);
//        add(fileButton);
//        add(exitButton);
//        add(chooseButton);
        add(timeLabel);

        setJMenuBar(menuBar);

        setLocationRelativeTo(null); // 窗口居中显示
        setVisible(true);

    }

    public static void main(String[] args) {
        new 秒表();
    }


}
