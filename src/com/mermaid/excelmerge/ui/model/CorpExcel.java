package com.mermaid.excelmerge.ui.model;

import java.util.Comparator;

public class CorpExcel implements Comparator {
	private String name;
	private String path;

	public CorpExcel(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}

	public int compare(Object arg0, Object arg1) {
		CorpExcel obj1 = (CorpExcel) arg0;
		CorpExcel obj2 = (CorpExcel) arg1;

		return obj1.getName().compareTo(obj2.getName());
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
