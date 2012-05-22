package com.mermaid.excelmerge.ui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.mermaid.excelmerge.ui.model.Corp;
import com.mermaid.excelmerge.ui.model.TreeDataSource;
import com.mermaid.excelmerge.ui.view.CorpImportDialog;
import com.mermaid.excelmerge.ui.view.ExcelPanel;
import com.mermaid.excelmerge.ui.view.ExcelTabbedPane;
import com.mermaid.excelmerge.ui.view.MainView;

public class MainController implements java.awt.event.ActionListener,
										java.awt.event.KeyListener,
										TreeSelectionListener{
	private MainView mainView;
	private TreeDataSource treeDataSource;

	public MainController(MainView mainView) {
		this.mainView = mainView;
		this.treeDataSource = new TreeDataSource(mainView.corpTree);
		this.treeDataSource.load();
		mainView.corpTree.addTreeSelectionListener(this);
		mainView.corpTree.expandTree(mainView.corpTree);		

		mainView.saveJMI.addActionListener(this);
		mainView.exitJMI.addActionListener(this);
		mainView.guideJMI.addActionListener(this);
		mainView.aboutJMI.addActionListener(this);

		mainView.addCorpJB.addActionListener(this);
		mainView.removeCorpJB.addActionListener(this);
		mainView.importExcelJB.addActionListener(this);
		mainView.printJB.addActionListener(this);
	}

	public void valueChanged(TreeSelectionEvent e) {
		TreePath selectedPath = e.getNewLeadSelectionPath();

		if (selectedPath != null) {
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
			if (selectedNode != null) {
				Corp corp = (Corp) selectedNode.getUserObject();
				if (corp != null)
					mainView.setRightComponent(new ExcelTabbedPane(corp));
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source instanceof JButton) {
			if (source.equals(mainView.addCorpJB)) {
				mainView.corpTree.addRegion();			
				return;
			} else if (source.equals(mainView.removeCorpJB)) {
				mainView.corpTree.removeRegion();
			} else if (source.equals(mainView.importExcelJB)) {
				TreePath selectedPath = mainView.corpTree.getSelectionPath();
				if (selectedPath != null && selectedPath.getLastPathComponent() != null) {
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
					Corp corp = (Corp) selectedNode.getUserObject();

					if (((Integer) corp.getId()) > 0) {
						CorpImportDialog corpImportDialog = new CorpImportDialog(corp, treeDataSource);
						corpImportDialog.setVisible(true);							
					}

					mainView.corpTree.setSelectionPath(selectedPath);
				}
			} else if (source.equals(mainView.printJB)) {
				;
			}
		} else if (source instanceof JMenuItem) {
			if (source.equals(mainView.saveJMI)) {
				mainView.statusJL.setText("正在保存......");

				if (treeDataSource.save())
					mainView.statusJL.setText("保存成功！");
				else
					mainView.statusJL.setText("保存失败！");
			} else if (source.equals(mainView.exitJMI)) {
				System.exit(0);
			} else if (source.equals(mainView.guideJMI)) {
				
			} else if (source.equals(mainView.aboutJMI)) {
				
			}
		}		

		mainView.corpTree.requestFocusInWindow();
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