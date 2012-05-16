package com.mermaid.excelmerge.ui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;

import com.mermaid.excelmerge.ui.view.MainView;

public class MainController implements java.awt.event.ActionListener,
										java.awt.event.KeyListener {
	private MainView mainView;

	public MainController(MainView mainView) {
		this.mainView = mainView;
		
		mainView.addCorpJB.addActionListener(this);
		mainView.removeCorpJB.addActionListener(this);
		mainView.printJB.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source instanceof JButton) {
			if (source.equals(mainView.addCorpJB)) {
				mainView.corpTree.addRegion();
			} else if (source.equals(mainView.removeCorpJB)) {
				mainView.corpTree.removeRegion();
			} else if (source.equals(mainView.printJB)) {
				
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