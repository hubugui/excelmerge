package com.mermaid.excelmerge.ui.model;

import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.lightdev.app.samples.regedit.Region;

public class Corp extends Region {
	private Hashtable<String, CorpExcel> excelTable = new Hashtable<String, CorpExcel>();

	public Corp(int id, String name) {
		super(id, name);
	}

	public void addExcel(String type, String path) {
		excelTable.put(type, new CorpExcel(type, path));
	}

	public void removeExcel(String type) {
		excelTable.remove(type);
	}

	public String getExcel(String type) {
		CorpExcel corpExcel = (CorpExcel) excelTable.get(type);

		return corpExcel == null ? null : corpExcel.getPath();
	}
	
	public int excelSize() {
		return excelTable.size();
	}

	public List<CorpExcel> getAll() {
		List<CorpExcel> list = new LinkedList<CorpExcel>();

		for (CorpExcel excel:excelTable.values()) {
			list.add(excel);
		}	

		Collections.sort(list, new CorpExcel("", ""));

		return list;
	}
}