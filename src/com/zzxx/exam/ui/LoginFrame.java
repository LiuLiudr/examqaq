package com.zzxx.exam.ui;

import com.zzxx.exam.controller.ClientContext;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

/**
 * 登录界面 是一个具体窗口框
 */
public class LoginFrame extends JFrame {
    private static final long serialVersionUID = 1L;

    public LoginFrame() {
        init();
    }

    /**
     * 初始化界面组件和布局的
     */
    private void init() {
        this.setTitle("登录系统");
        JPanel contentPane = createContentPane();
        this.setContentPane(contentPane);
        // 必须先设大小后居中
        setSize(300, 220);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {//窗口监听
            public void windowClosing(WindowEvent e) {

            }
        });
    }

    private JPanel createContentPane() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBorder(new EmptyBorder(8, 8, 8, 8));
        p.add(BorderLayout.NORTH, new JLabel("登录考试系统", JLabel.CENTER));
        p.add(BorderLayout.CENTER, createCenterPane());
        p.add(BorderLayout.SOUTH, createBtnPane());
        return p;
    }

    private ClientContext controller = new ClientContext();

    public void setController(ClientContext controller) {
        this.controller = controller;
    }

    private JPanel createBtnPane() {
        JPanel p = new JPanel(new FlowLayout());
        JButton login = new JButton("Login");
        JButton cancel = new JButton("Cancel");
        p.add(login);
        p.add(cancel);

        getRootPane().setDefaultButton(login);//设置默认按钮。相当于直接回车就能点

        login.addActionListener(new ActionListener() {//login按钮监听
            public void actionPerformed(ActionEvent e) {
                //点击login，触发登陆业务
                try {
                    controller.login();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });

        cancel.addActionListener(new ActionListener() {//cancel按钮监听
            public void actionPerformed(ActionEvent e) {
                controller.exit();
            }
        });
        return p;
    }

    private JPanel createCenterPane() {
        JPanel p = new JPanel(new BorderLayout());//麻将布局加到画板
        p.setBorder(new EmptyBorder(8, 0, 0, 0));
        p.add(BorderLayout.NORTH, createIdPwdPane());
        message = new JLabel("", JLabel.CENTER);//错误的标签
        p.add(BorderLayout.SOUTH, message);//将错误的内容加到画板
        return p;
    }

    private JPanel createIdPwdPane() {
        JPanel p = new JPanel(new GridLayout(2, 1, 0, 6));
        p.add(createIdPane());
        p.add(createPwdPane());
        return p;
    }

    private JTextField idField;//给控制器用的成员变量。读取输入框账号

    private JPanel createIdPane() {
        JPanel p = new JPanel(new BorderLayout(6, 0));
        p.add(BorderLayout.WEST, new JLabel("编号:"));
        JTextField idField = new JTextField();//账号
        this.idField = idField;
        p.add(BorderLayout.CENTER, idField);

        return p;
    }

    /**
     * 简单工厂方法, 封装的复杂对象的创建过程, 返回一个对象实例
     */
    private JTextField pwdFeild;//给控制器用的成员变量.读取输入框密码

    private JPanel createPwdPane() {
        JPanel p = new JPanel(new BorderLayout(6, 0));
        p.add(BorderLayout.WEST, new JLabel("密码:"));
        JPasswordField pwdField = new JPasswordField();//密码
        this.pwdFeild = pwdField;
        pwdField.enableInputMethods(true);
        p.add(BorderLayout.CENTER, pwdField);
        return p;
    }

    public JTextField getIdField() {
        return idField;
    }


    public JTextField getPwdFeild() {
        return pwdFeild;
    }


    public JLabel getMessage() {
        return message;
    }


    private JLabel message;

    public void updateMessage(String message) {
        this.message.setText(message);//设置错误文本内容
    }


}
