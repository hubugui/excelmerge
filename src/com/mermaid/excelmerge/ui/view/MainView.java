package com.mermaid.excelmerge.ui.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.UIManager;

import com.mermaid.excelmerge.ui.resources.Images;

public class MainView {
	private JFrame mainFrame;
	private JMenuBar menuBar;
	private JToolBar toolBar;
	private JSplitPane splitPane;
	private JToolBar statusBar;
	private JTree corpTree;
	private JTabbedPane excelTabbedPane;  

	public MainView() {
		initializeLookAndFeel();
		initializeFrame();
		initializeMenu();
		initializeToolbar();
		initializePanel();

		mainFrame.setVisible(true);
		splitPane.setDividerLocation(0.2);
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

		JMenu fileMenu = new JMenu("File");

		JMenuItem addMI = new JMenuItem("Add");
		addMI.setIcon(Images.getImageIcon("open.gif"));
		addMI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.ActionEvent.CTRL_MASK));
		fileMenu.add(addMI);

		JMenuItem removeMI = new JMenuItem("Remove");
		removeMI.setIcon(Images.getImageIcon("open.gif"));
		removeMI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.ActionEvent.CTRL_MASK));
		fileMenu.add(removeMI);

		fileMenu.addSeparator();

		JMenuItem closeMI = new JMenuItem("Exit");
		closeMI.setIcon(Images.getImageIcon("open.gif"));
		closeMI.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.ActionEvent.CTRL_MASK));
		fileMenu.add(closeMI);

		menuBar.add(fileMenu);
		mainFrame.setJMenuBar(menuBar);
	}

	private void initializeToolbar() {
		toolBar = new JToolBar();

		Insets margins = new Insets(0, 0, 0, 0);

		JButton addJB = new JButton(Images.getImageIcon("open.gif"));
		addJB.setMargin(margins);
		addJB.setToolTipText("Measure Marker(Alt + A");
		addJB.setMnemonic('a');
		toolBar.add(addJB);

		JButton removeJB = new JButton(Images.getImageIcon("measure.gif"));		
		removeJB.setMargin(margins);
		removeJB.setToolTipText("Measure Marker(Alt + R");
		removeJB.setMnemonic('r');
		toolBar.add(removeJB);

		toolBar.addSeparator();

		JButton printJB = new JButton(Images.getImageIcon("measure.gif"));
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
		initializeRightPanel();
		initializeStatusPanel();
	}

	private void initializeLeftPanel() {
		corpTree = new JTree();
		splitPane.add(corpTree, JSplitPane.LEFT);
	}

	private void initializeRightPanel() {
		excelTabbedPane = new JTabbedPane();

		ExcelPanel excelPanel1 = new ExcelPanel();
		excelTabbedPane.add("利润表", excelPanel1);

		ExcelPanel excelPanel2 = new ExcelPanel();
		excelTabbedPane.add("现金流量表", excelPanel2);

		ExcelPanel excelPanel3 = new ExcelPanel();
		excelTabbedPane.add("负债表", excelPanel3);

		splitPane.add(excelTabbedPane, JSplitPane.RIGHT);
	}

	private void initializeStatusPanel() {
		statusBar = new JToolBar();
		JLabel statusJL = new JLabel("This is status bar");
		statusBar.add(statusJL);

		mainFrame.getContentPane().add(statusBar, BorderLayout.SOUTH);
	}
}