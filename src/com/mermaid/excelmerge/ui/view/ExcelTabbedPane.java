package com.mermaid.excelmerge.ui.view;

import java.util.List;

import javax.swing.JTabbedPane;

import com.mermaid.excelmerge.ui.model.Corp;
import com.mermaid.excelmerge.ui.model.CorpExcel;

public class ExcelTabbedPane extends JTabbedPane {
	private Corp corp;

	public ExcelTabbedPane(Corp corp) {
		super();

		this.corp = corp;
		initialize();
	}

	private void initialize() {
		List<CorpExcel> list = corp.getAll();

		for (CorpExcel excel:list) {
			ExcelPanel excelPanel = new ExcelPanel(excel);

			this.addTab(excel.getName(), excelPanel);
		}
	}
}