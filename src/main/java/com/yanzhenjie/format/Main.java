/*
 * Copyright © Yan Zhenjie. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yanzhenjie.format;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;

/**
 * <p>Main.</p>
 * Created by YOLANDA on 2017/5/6.
 */
public class Main extends JFrame {

    private JRadioButton mJRBtnString;
    private JRadioButton mJRBtnBuffer;
    private JTextField mEdtFieldName;
    private JSplitPane mSplPane;
    private JTextArea newSql;
    private JTextArea oldSql;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Main frame = new Main();
                frame.start();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    private void start() {
        URL imageUrl = Main.class.getResource("/icon.png");
        ImageIcon ico = new ImageIcon(imageUrl);
        setIconImage(ico.getImage());

        setMinimumSize(new Dimension(840, 600));
        setTitle("格式化字符串成Java字符串");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 842, 605);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(10, 80));
        contentPane.add(panel, BorderLayout.NORTH);
        panel.setLayout(new BorderLayout(0, 0));

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setPreferredSize(new Dimension(300, 10));
        panel.add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(null);

        JLabel label = new JLabel("选择生成方式：");
        label.setBounds(10, 10, 153, 20);
        panel_1.add(label);

        mJRBtnString = new JRadioButton("String");
        mJRBtnString.setSelected(true);
        mJRBtnString.setBounds(52, 36, 79, 23);
        panel_1.add(mJRBtnString);

        mJRBtnBuffer = new JRadioButton("StringBuffer");
        mJRBtnBuffer.setBounds(144, 36, 107, 23);
        panel_1.add(mJRBtnBuffer);

        ButtonGroup bGroup = new ButtonGroup();
        bGroup.add(mJRBtnString);
        bGroup.add(mJRBtnBuffer);

        mEdtFieldName = new JTextField();
        mEdtFieldName.setText("formatString");
        mEdtFieldName.setBounds(313, 31, 180, 33);
        panel_1.add(mEdtFieldName);
        mEdtFieldName.setColumns(10);

        JLabel label_1 = new JLabel("变量名：");
        label_1.setBounds(276, 13, 87, 15);
        panel_1.add(label_1);

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new MatteBorder(1, 0, 1, 1, (Color) new Color(0, 0, 0)));
        panel_3.setPreferredSize(new Dimension(200, 10));
        panel.add(panel_3, BorderLayout.EAST);
        panel_3.setLayout(new BorderLayout(0, 0));

        JButton button = new JButton("生成");
        button.addActionListener(e -> {
            // 生成SQL
            String oldSqlStr = oldSql.getText();
            if (oldSqlStr.equals("")) {
                JOptionPane.showMessageDialog(Main.this, "请在左侧输入字符串再执行！");
                return;
            }
            // 清空
            if (!newSql.getText().equals("")) {
                newSql.setText("");
            }
            String visibleName = mEdtFieldName.getText();
            if (visibleName.trim().equals("")) {
                JOptionPane.showMessageDialog(Main.this, "请输入变量名！");
                return;
            }
            String[] unFormatString = oldSqlStr.split("\n");
            StringBuilder formatString = new StringBuilder();
            // 对SQL进行拼接
            if (mJRBtnString.isSelected()) {
                // String形式
                formatString.append("String ").append(visibleName).append(" = ");
                for (int i = 0; i < unFormatString.length; i++) {
                    if (i == 0) {
                        formatString.append("\"").append(unFormatString[i]).append("\"");
                    } else {
                        formatString.append("\n    + \"").append(unFormatString[i]).append("\"");
                    }
                }
                formatString.append(";");
            } else {
                // StringBuffer形式
                formatString.append("StringBuffer ")
                        .append(visibleName)
                        .append(" = new StringBuffer()");
                for (int i = 0; i < unFormatString.length; i++) {
                    formatString.append("\n    .append(\"").append(unFormatString[i]).append("\")");
                }
                formatString.append(";");
            }
            newSql.setText(formatString.toString());
        });
        button.setFont(new Font("楷体", Font.PLAIN, 32));
        panel_3.add(button, BorderLayout.CENTER);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new MatteBorder(0, 1, 1, 1, new Color(0, 0, 0)));
        contentPane.add(panel_2, BorderLayout.CENTER);
        panel_2.setLayout(new BorderLayout(0, 0));

        mSplPane = new JSplitPane();
        mSplPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                mSplPane.setDividerLocation(0.4);
            }
        });
        panel_2.add(mSplPane, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane();
        mSplPane.setLeftComponent(scrollPane);

        oldSql = new JTextArea();
        scrollPane.setViewportView(oldSql);

        JScrollPane scrollPane_1 = new JScrollPane();
        mSplPane.setRightComponent(scrollPane_1);

        newSql = new JTextArea();
        scrollPane_1.setViewportView(newSql);

        JPanel panel_4 = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_4.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        panel_4.setPreferredSize(new Dimension(10, 30));
        panel_2.add(panel_4, BorderLayout.NORTH);

        JLabel labelHint = new JLabel("请在左侧输入你要格式化的SQL语句：");
        labelHint.setHorizontalAlignment(SwingConstants.LEFT);
        panel_4.add(labelHint);
    }
}