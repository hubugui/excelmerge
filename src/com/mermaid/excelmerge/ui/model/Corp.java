package com.mermaid.excelmerge.ui.model;

import java.util.Hashtable;

import com.lightdev.app.samples.regedit.Region;

public class Corp extends Region {
	private Hashtable<String, CorpExcel> excelTable = new Hashtable<String, CorpExcel>(); 

	public Corp(int id, String name) {
		super(id, name);
	}

	public Hashtable<String, CorpExcel> getExcelTable() {
		return this.excelTable;
	}
}