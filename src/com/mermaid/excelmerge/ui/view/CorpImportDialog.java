package com.mermaid.excelmerge.ui.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

import com.mermaid.excelmerge.ui.model.TreeDataSource;
import com.mermaid.excelmerge.ui.model.Corp;
import com.mermaid.excelmerge.ui.util.FileCopy;

public class CorpImportDialog extends JDialog implements java.awt.event.ActionListener {
	private Corp corp;
	private TreeDataSource dataSource;	
	private JPanel contentPane;
	public JButton confirmJB;
	public JButton cancelJB;

	private JTextField[] excelPathJTFArray;

	private String[] excelType = {"利润表", "现金流量表", "负债表"};

	public CorpImportDialog(Corp corp, TreeDataSource dataSource) {
		this.corp = corp;
		this.dataSource = dataSource;
		initialize();
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source instanceof JButton) {
			if (source.equals(confirmJB)) {
				if (excelPathJTFArray != null) {
					for (JTextField excelPathJTF:excelPathJTFArray) {
						String name = excelPathJTF.getClientProperty("name").toString();
						String path = excelPathJTF.getText().trim();

						if (name != null && path.length() > 0) {
							try {
								String oldPath = corp.getExcel(name);
								if (oldPath != null) {
									File oldExcel = new File(oldPath);
									if (oldExcel.exists())
										oldExcel.delete();
								}

								long current = System.currentTimeMillis();
								String newPath = dataSource.storage_dir;
								newPath += corp.getName() + "-";
								newPath += current + "-";
								newPath += new File(path).getName();

								FileCopy.copy(path, newPath);
								corp.addExcel(name, new File(newPath).getAbsolutePath());

								while (System.currentTimeMillis() == current)
									Thread.sleep(1);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}

					dataSource.save();
				}
			}
		}

		this.dispose();
	}

	private void initialize() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout());

		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());		
		GridBagConstraints gridBagConst = new GridBagConstraints();

		excelPathJTFArray = new JTextField[excelType.length];
		for (int i = 0; i < excelType.length; i++) {
			gridBagConst.fill = GridBagConstraints.HORIZONTAL;
			gridBagConst.gridx = 0;
			gridBagConst.gridy = i;
			gridBagConst.weightx = 0.1;

			JLabel typeJL = new JLabel(excelType[i]);
			centerPanel.add(typeJL, gridBagConst);

			excelPathJTFArray[i] = new JTextField();
			excelPathJTFArray[i].setColumns(40);
			excelPathJTFArray[i].putClientProperty("name", excelType[i]);

			gridBagConst.fill = GridBagConstraints.HORIZONTAL;
			gridBagConst.gridx = 1;
			gridBagConst.gridy = i;
			gridBagConst.weightx = 1;
			centerPanel.add(excelPathJTFArray[i], gridBagConst);

			class ChooseActionListener implements java.awt.event.ActionListener {
				private JTextField excelPathJTF;
				private Component parent;
				
				public ChooseActionListener(JTextField excelPathJTF, Component parent) {
					this.excelPathJTF = excelPathJTF;
					this.parent = parent;
				}

				public void actionPerformed(ActionEvent e) {
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setDialogTitle("请选择");
					fileChooser.setFileFilter(new FileFilter() {
						public boolean accept(File f) {
							if (f.getName().endsWith("xls") || f.getName().endsWith("csv") || f.isDirectory()) 
								return true; 
							return false; 
						}

						public String getDescription() {
							return "Excel(*.xls)";
						}
					});

					if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) { 
						File fileIn = fileChooser.getSelectedFile(); 
						if (fileIn.exists())
							excelPathJTF.setText(fileIn.getAbsolutePath());
					}
				}
			}

			JButton chooseJB = new JButton("请选择");
			chooseJB.addActionListener(new ChooseActionListener(excelPathJTFArray[i], this));
			gridBagConst.fill = GridBagConstraints.HORIZONTAL;
			gridBagConst.gridx = 2;
			gridBagConst.gridy = i;
			gridBagConst.weightx = 0.1;
			centerPanel.add(chooseJB, gridBagConst);
		}

		contentPane.add(centerPanel, BorderLayout.CENTER);

		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		confirmJB = new JButton("确定");
		confirmJB.addActionListener(this);
		controlPanel.add(confirmJB);

		cancelJB = new JButton("取消");
		cancelJB.addActionListener(this);
		controlPanel.add(cancelJB);

		contentPane.add(controlPanel, BorderLayout.SOUTH);
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
		this.getRootPane().registerKeyboardAction(this, 
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
				JComponent.WHEN_IN_FOCUSED_WINDOW);
	}
}