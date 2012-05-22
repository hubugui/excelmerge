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
	private Document document;
	private TreeNode root;

	public TreeReader(String path, TreeNode root) throws ParserConfigurationException, IOException, SAXException {
		this.root = root;

		File file = new File(path);
		if (file.exists()) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        	DocumentBuilder builder = factory.newDocumentBuilder();
        	this.document = builder.parse(file);
			this.document.getDocumentElement().normalize();
		}
	}

	private void readCorp(Element element, TreeNode node) {
		NodeList nList = element.getChildNodes();

		for (int idx = 0; idx < nList.getLength(); idx++) {
			   Node nNode = nList.item(idx);
			   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				   Element eElement = (Element) nNode;
				   String elementName = eElement.getNodeName().toLowerCase();
				   String name = eElement.getAttribute("name");

				   if (elementName.equals("excel")) {
					   String path = eElement.getAttribute("path");

					   Corp corp = (Corp) ((DefaultMutableTreeNode) node).getUserObject();
					   corp.addExcel(name, path);
				   } else if (elementName.equals("corp")) {					   
					   int id = Integer.parseInt(eElement.getAttribute("id"));

					   DefaultMutableTreeNode child = new DefaultMutableTreeNode(new Corp(id, name));
					   ((DefaultMutableTreeNode) node).add(child);

					   readCorp(eElement, child);
				   }
			   }
		}
	}

	public boolean load() {
		if (document != null) {
			readCorp(document.getDocumentElement(), root);
			return true;
		} else
			return false;
	}
}