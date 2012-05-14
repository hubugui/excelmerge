package com.mermaid.excelmerge.ui;

import javax.swing.SwingUtilities;

import com.mermaid.excelmerge.ui.controller.MainController;
import com.mermaid.excelmerge.ui.view.MainView;

public class Main {
	public static void main(String[] args) {
		try {
	    	SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					new MainController(new MainView());
				}
			});
		} catch (Exception e) {
            e.printStackTrace();
		}		
	}
}