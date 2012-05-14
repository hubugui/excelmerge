package com.mermaid.excelmerge.ui.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

public class ExcelPanel extends javax.swing.JPanel {
	private JPanel toolbarJP;
	private JCheckBox allJCB;
	private JCheckBox ownerJCB;
	private JTable table;

	public ExcelPanel() {
		super();
		initializeUI();
	}

	private void initializeUI() {
		toolbarJP = new JPanel();

		allJCB = new JCheckBox("All");		
		toolbarJP.add(allJCB);

		ownerJCB = new JCheckBox("Owner");
		toolbarJP.add(ownerJCB);

		String headers[] = {"课程名称","课程代号","学费"};
		Object[][] cells = new Object[65535][256];

		table = new JTable(cells, headers);
		table.setAutoscrolls(true);

		this.setLayout(new BorderLayout());
		this.add(toolbarJP, BorderLayout.NORTH);
		this.add(table, BorderLayout.CENTER);
	}
}