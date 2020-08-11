package com.zzxx.exam.test;

import javax.swing.*;
import java.awt.*;

public class LayoutDemo {
	public static void main(String[] args) {
		JFrame frame = new JFrame("布局管理");
		JPanel content = new JPanel(new BorderLayout());
		JPanel bottom = new JPanel(new BorderLayout());
		JPanel btnPanel = new JPanel(new FlowLayout());
		JButton ok = new JButton("OK");
		JButton cancel = new JButton("Cancel");
		//btnPanel把两个按钮加到画布
		btnPanel.add(ok);
		btnPanel.add(cancel);
		//bottom画布添加btnPanel到西边
		bottom.add(BorderLayout.EAST, btnPanel);
		//content添加画布bottom到南边
		content.add(BorderLayout.SOUTH, bottom);
		//content加到窗口
		frame.setContentPane(content);
		frame.setSize(380, 300);
		frame.setVisible(true);
	}
}
