package com.mermaid.excelmerge.ui.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
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

		String headers[] = {"姓名","年龄","科目"};
		Object[][] cells = new Object[8][256];

		table = new JTable(cells, headers);
		table.setAutoscrolls(true);

		JScrollPane tablePane = new JScrollPane(table);
		tablePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		tablePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		this.setLayout(new BorderLayout());
		this.add(toolbarJP, BorderLayout.NORTH);
		this.add(tablePane, BorderLayout.CENTER);
	}
}