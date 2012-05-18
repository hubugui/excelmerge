package com.mermaid.excelmerge.ui.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.mermaid.excelmerge.ui.model.Corp;

public class CorpImportFrame extends JDialog {
	private Corp corp;
	private JPanel contentPane;

	private String[] excelType = {"利润表", "现金流量表", "负债表"};

	public CorpImportFrame(Corp corp) {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new GridBagLayout());
		GridBagConstraints gridBagConst = new GridBagConstraints();

		for (int i = 0; i < excelType.length; i++) {
			gridBagConst.fill = GridBagConstraints.HORIZONTAL;
			gridBagConst.gridx = 0;
			gridBagConst.gridy = i;
			gridBagConst.weightx = 0.1;

			JLabel typeJL = new JLabel(excelType[i]);
			contentPane.add(typeJL, gridBagConst);

			JTextField excelPathJTF = new JTextField();
			gridBagConst.fill = GridBagConstraints.HORIZONTAL;
			gridBagConst.gridx = 1;
			gridBagConst.gridy = i;
			gridBagConst.weightx = 1;
			contentPane.add(excelPathJTF, gridBagConst);

			JButton chooseJB = new JButton("请选择");
			gridBagConst.fill = GridBagConstraints.HORIZONTAL;
			gridBagConst.gridx = 2;
			gridBagConst.gridy = i;
			gridBagConst.weightx = 0.1;
			contentPane.add(chooseJB, gridBagConst);
		}

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		gridBagConst.fill = GridBagConstraints.HORIZONTAL;
		gridBagConst.gridwidth = GridBagConstraints.REMAINDER;
		gridBagConst.gridy = excelType.length;
		contentPane.add(controlPanel, gridBagConst);

		JButton confirmJB = new JButton("确定");
		controlPanel.add(confirmJB);

		JButton cancelJB = new JButton("取消");
		controlPanel.add(cancelJB);
/*
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		double widthRatio = 0.25d;
		double heightRatio = 0.25d;
		double screenWidth = dim.getWidth();
		double screenHeight = dim.getHeight();

		int frameWidth = (int) (screenWidth * widthRatio);
		int frameHeight = (int) (screenHeight * heightRatio);
		int frameX = (int) (screenWidth * (1 - widthRatio) / 2);
		int frameY = (int) (screenHeight * (1 - heightRatio) / 2);

		this.setSize(frameWidth, frameHeight);
		this.setLocation(frameX, frameY);
*/
		this.setTitle(corp.getName() + "-" + "导入Excel");
		this.setContentPane(contentPane);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setModal(true);
	}
}