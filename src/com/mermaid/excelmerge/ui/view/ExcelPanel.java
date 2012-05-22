package com.mermaid.excelmerge.ui.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.List;
import java.io.File;

import javax.swing.AbstractListModel;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import jxl.HeaderFooter;
import jxl.Sheet;
import jxl.Workbook;

import com.mermaid.excelmerge.ui.model.Corp;
import com.mermaid.excelmerge.ui.model.CorpExcel;

@SuppressWarnings("serial")
public class ExcelPanel extends javax.swing.JPanel {
	private CorpExcel excel;
	private JPanel toolbarJP;
	private JCheckBox allJCB;
	private JCheckBox ownerJCB;
	private JTable table;

	private Workbook workbook;
	private Sheet sheet;
	private int rows;
	private int cols;
	private String headers[];
	private Object[][] cells;

	public ExcelPanel(CorpExcel excel) {
		super();
		this.excel = excel;

		loadExcel();
		initializeUI();
	}

	class RowHeaderRenderer extends JLabel implements ListCellRenderer {
		RowHeaderRenderer(JTable table) {
			JTableHeader header = table.getTableHeader();
			setOpaque(true);
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			setHorizontalAlignment(CENTER);
			setForeground(header.getForeground());
			setBackground(header.getBackground());
			setFont(header.getFont());
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			setText((value == null) ? "" : value.toString());
			return this;
		}
	}

	private void initializeUI() {
		toolbarJP = new JPanel();

		allJCB = new JCheckBox("All");
		toolbarJP.add(allJCB);

		ownerJCB = new JCheckBox("Owner");
		toolbarJP.add(ownerJCB);

		table = new JTable(cells, headers);
		table.setAutoscrolls(true);

		// row header
		ListModel lm = new AbstractListModel() {
			public int getSize() {
				return rows;
			}

			public Object getElementAt(int index) {
				return index + 1;
			}
		};

		JList rowHeader = new JList(lm);
		rowHeader.setFixedCellHeight(table.getRowHeight());
		rowHeader.setCellRenderer(new RowHeaderRenderer(table));

		// col header

		// scroll pane
		JScrollPane scroll = new JScrollPane(table);
		scroll.setRowHeaderView(rowHeader);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		this.setLayout(new BorderLayout());
		this.add(toolbarJP, BorderLayout.NORTH);
		this.add(scroll, BorderLayout.CENTER);
	}

	private void loadExcel() {
		try {
			workbook = Workbook.getWorkbook(new File(excel.getPath()));
			sheet = workbook.getSheet(0);
			rows = sheet.getRows();
			cols = sheet.getColumns();

			cells = new Object[rows][cols];
			headers = new String[cols];
			for (int i = 0; i < cols; i++) {
				int chr = 'A' + i;
				headers[i] = (char) chr + "";
			}
			
			for (int row = 0; row < rows; row++) {
				for (int col = 0; col < cols; col++) {
					cells[row][col] = sheet.getCell(col, row).getContents();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}