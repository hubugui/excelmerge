package com.mermaid.excelmerge.ui.model;

public class CorpExcel {
	private String name;
	private String path;

	public CorpExcel(String name, String path) {
		super();
		this.name = name;
		this.path = path;
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
