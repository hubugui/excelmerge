package com.mermaid.excelmerge.ui.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mermaid.excelmerge.ui.model.TreeNodeAccess.TreeNodeAccessCB;

public class TreeReader {
	private final static String VERSION = "1.0";
	private final static String DESCRIPTOR = "excel merge data file";
	private final static String CORP = "Mermaid";

	private String path;
	private Document document;
	private TreeNode node;

	public TreeReader(String path, TreeNode node) throws ParserConfigurationException, IOException, SAXException {
		this.path = path;
		this.node = node;

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		this.document = dBuilder.parse(new File(path));
		this.document.getDocumentElement().normalize();
	}

	private boolean readExcel(Element element, Corp corp) {
		NodeList nList = document.getElementsByTagName("excel");

		for (int idx = 0; idx < nList.getLength(); idx++) {
			   Node nNode = nList.item(idx);

			   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				   Element eElement = (Element) nNode;

				   String name = eElement.getAttribute("name");
				   String path = eElement.getAttribute("path");

				   corp.removeExcel(name);
				   corp.addExcel(name, path);				   
			   }
		}
		
		return true;
	}

	private boolean read() {
		NodeList nList = document.getElementsByTagName("corp");

		for (int idx = 0; idx < nList.getLength(); idx++) {
			   Node nNode = nList.item(idx);

			   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				   Element eElement = (Element) nNode;
				   
				   String name = eElement.getAttribute("name");
				   int id = Integer.parseInt(eElement.getAttribute("id"));

				   Corp corp = new Corp(id, name);
				   readExcel(eElement, corp);
				   
				   ((DefaultMutableTreeNode) node).add(new DefaultMutableTreeNode(corp));
			   }
		}
		
		return true;
	}

	public boolean load() {
		boolean rc = true;

		try {
			read();
		} catch (Exception ex) {
			rc = false;
			ex.printStackTrace();
		}

		return rc;
	}
}