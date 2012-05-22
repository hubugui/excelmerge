package com.mermaid.excelmerge.ui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import com.mermaid.excelmerge.ui.resources.Images;

public class MainView {
	public JFrame mainFrame;
	private JMenuBar menuBar;
	private JToolBar toolBar;
	private JSplitPane splitPane;
	private JToolBar statusBar;
	public CorpTree corpTree;

	public JMenuItem saveJMI;
	public JMenuItem exitJMI;
	public JMenuItem guideJMI;
	public JMenuItem aboutJMI;

	public JButton addCorpJB;
	public JButton removeCorpJB;
	public JButton importExcelJB;
	public JButton exportExcelJB;
	public JButton printJB;	

	public JLabel statusJL;

	public MainView() {
		initializeLookAndFeel();
		initializeFrame();
		initializeMenu();
		initializeToolbar();
		initializePanel();

		mainFrame.setVisible(true);
		splitPane.setDividerLocation(0.2);
		corpTree.requestFocusInWindow();
	}

	private void initializeLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initializeFrame() {
		mainFrame = new JFrame("ExcelMerge");

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		double ratio = 0.75d;
		double screenWidth = dim.getWidth();
		double screenHeight = dim.getHeight();

		int frameWidth = (int) (screenWidth * ratio);
		int frameHeight = (int) (screenHeight * ratio);
		int frameX = (int) (screenWidth * (1 - ratio) / 2);
		int frameY = (int) (screenHeight * (1 - ratio) / 2);

		mainFrame.setLocation(frameX, frameY);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(frameWidth, frameHeight);
	}

	private void initializeMenu() {
		menuBar = new JMenuBar();

		// file menu
		JMenu fileMenu = new JMenu("文件(F)");
		fileMenu.setMnemonic('f');

		saveJMI = new JMenuItem("保存");
		saveJMI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.ActionEvent.CTRL_MASK));
		fileMenu.add(saveJMI);

		fileMenu.addSeparator();

		exitJMI = new JMenuItem("退出");
		// exitJMI.setIcon(Images.getImageIcon("open.gif"));
		exitJMI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.ActionEvent.CTRL_MASK));
		fileMenu.add(exitJMI);

		menuBar.add(fileMenu);

		// help menu
		JMenu helpMenu = new JMenu("帮助(H)");
		helpMenu.setMnemonic('h');

		guideJMI = new JMenuItem("指南");
		guideJMI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.ActionEvent.CTRL_MASK));
		helpMenu.add(guideJMI);

		helpMenu.addSeparator();
		
		aboutJMI = new JMenuItem("关于");
		aboutJMI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.ActionEvent.CTRL_MASK));
		helpMenu.add(aboutJMI);

		menuBar.add(helpMenu);

		mainFrame.setJMenuBar(menuBar);
	}

	private void initializeToolbar() {
		toolBar = new JToolBar();

		Insets margins = new Insets(0, 0, 0, 0);

		addCorpJB = new JButton("增加");
		addCorpJB.setMargin(margins);
		addCorpJB.setToolTipText("增加公司(Alt + A");
		addCorpJB.setMnemonic('a');
		toolBar.add(addCorpJB);

		removeCorpJB = new JButton("删除");		
		removeCorpJB.setMargin(margins);
		removeCorpJB.setToolTipText("删除公司(Alt + R");
		removeCorpJB.setMnemonic('r');
		toolBar.add(removeCorpJB);

		toolBar.addSeparator();

		importExcelJB = new JButton("导入");		
		importExcelJB.setMargin(margins);
		importExcelJB.setToolTipText("导入Excel(Alt + I");
		importExcelJB.setMnemonic('i');
		toolBar.add(importExcelJB);

		exportExcelJB = new JButton("导出");		
		exportExcelJB.setMargin(margins);
		exportExcelJB.setToolTipText("导出Excel(Alt + E");
		exportExcelJB.setMnemonic('e');
		toolBar.add(exportExcelJB);
		
		toolBar.addSeparator();

		printJB = new JButton(Images.getImageIcon("print.gif"));
		printJB.setMargin(margins);
		printJB.setToolTipText("Measure Marker(Alt + P");
		printJB.setMnemonic('p');
		toolBar.add(printJB);

		mainFrame.getContentPane().add(toolBar, BorderLayout.NORTH);
	}

	private void initializePanel() {
		splitPane = new JSplitPane();
		splitPane.setOneTouchExpandable(true);
		mainFrame.getContentPane().add(splitPane, BorderLayout.CENTER);

		initializeLeftPanel();
		initializeStatusPanel();
	}

	private void initializeLeftPanel() {
		corpTree = new CorpTree();
		splitPane.setLeftComponent(corpTree);
	}

	private void initializeStatusPanel() {
		statusBar = new JToolBar();
		statusJL = new JLabel("status bar");
		statusBar.add(statusJL);

		mainFrame.getContentPane().add(statusBar, BorderLayout.SOUTH);
	}

	public void setRightComponent(JComponent component) {
		splitPane.setRightComponent(component);
	}
}