package com.mermaid.excelmerge.ui.model;

import java.util.List;
import java.util.Vector;

import com.lightdev.app.samples.regedit.Region;

public class Corp extends Region {
	private List<CorpExcel> excelList = new Vector<CorpExcel>();

	public Corp(int id, String name) {
		super(id, name);
	}

	public void addExcel(String type, String path) {
		excelList.add(new CorpExcel(type, path));
	}

	public void removeExcel(String type) {
		for (CorpExcel cExcel:excelList) {
			if (cExcel.getName().equals(type)) {
				excelList.remove(cExcel);
			}
		}
	}

	public String getExcel(String type) {
		for (CorpExcel cExcel:excelList) {
			if (cExcel.getName().equals(type)) {
				return cExcel.getPath();
			}
		}

		return null;
	}

	public List<CorpExcel> getExcelList() {
		return this.excelList;
	}
}