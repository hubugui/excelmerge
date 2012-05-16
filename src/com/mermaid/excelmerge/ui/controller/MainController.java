package com.mermaid.excelmerge.ui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JMenuItem;

import com.mermaid.excelmerge.ui.view.MainView;

public class MainController implements java.awt.event.ActionListener,
										java.awt.event.KeyListener {
	private MainView mainView;
	private TreeDataSource treeDataSource;

	public MainController(MainView mainView) {
		this.mainView = mainView;
		this.treeDataSource = new TreeDataSource(mainView.corpTree);
		this.treeDataSource.load();

		mainView.saveJMI.addActionListener(this);
		mainView.exitJMI.addActionListener(this);
		mainView.guideJMI.addActionListener(this);
		mainView.aboutJMI.addActionListener(this);

		mainView.addCorpJB.addActionListener(this);
		mainView.removeCorpJB.addActionListener(this);
		mainView.importExcelJB.addActionListener(this);
		mainView.printJB.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source instanceof JButton) {
			if (source.equals(mainView.addCorpJB)) {
				if (mainView.corpTree.addRegion())
					treeDataSource.save();
			} else if (source.equals(mainView.removeCorpJB)) {
				if (mainView.corpTree.removeRegion())
					treeDataSource.save();
			} else if (source.equals(mainView.importExcelJB)) {
				
			} else if (source.equals(mainView.printJB)) {
				
			}
		} else if (source instanceof JMenuItem) {
			if (source.equals(mainView.saveJMI)) {
				
			} else if (source.equals(mainView.exitJMI)) {
				System.exit(0);
			} else if (source.equals(mainView.guideJMI)) {
				
			} else if (source.equals(mainView.aboutJMI)) {
				
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
}