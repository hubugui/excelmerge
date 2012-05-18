package com.mermaid.excelmerge.ui.controller;

import java.io.FileOutputStream;

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

import com.lightdev.app.samples.regedit.Region;

public class TreeWriter {
	private final static String VERSION = "1.0";
	private final static String DESCRIPTOR = "excel merge data file";
	private final static String CORP = "Mermaid";

	private String name;
	private String path;
	private Document document;
	private TreeNode node;

	public TreeWriter(String name, String path, TreeNode node) throws ParserConfigurationException {
		this.name = name;
		this.path = path;
		this.node = node;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		this.document = builder.newDocument();
	}

	private class TreeNodeAccessCBSave implements TreeNodeAccessCB {
		public Object access(TreeNode node, int depth, Object userData) {
			Region region = (Region) ((DefaultMutableTreeNode) node).getUserObject();
			region.setDepth(depth);
			
			Element parentE = (Element) userData;
			Element nodeE = document.createElement("corp");
			parentE.appendChild(nodeE);
			
			nodeE.setAttribute("name", region.getName());
			
			return nodeE;
		}
	}

	private boolean write() {
		Element root = document.createElement("excelmerge");
		root.setAttribute("version", VERSION);
		root.setAttribute("corp", CORP);
		root.setAttribute("descriptor", DESCRIPTOR);
		document.appendChild(root);
		
		TreeNodeAccess.depthFirst(node, new TreeNodeAccessCBSave(), 0, root);
		return true;
	}

	public boolean save() {
		boolean rc = true;

		try {
			write();
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(document);
			
			transformer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			
			StreamResult result = new StreamResult(new FileOutputStream(path));
			transformer.transform(source, result);
		} catch (Exception ex) {
			rc = false;
			ex.printStackTrace();
		}

		return rc;
	}
}