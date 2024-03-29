package com.mermaid.excelmerge.ui.model;

import java.io.FileOutputStream;
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

import com.mermaid.excelmerge.ui.model.TreeNodeAccess.TreeNodeAccessCB;

public class TreeWriter {
	private final static String VERSION = "1.0";
	private final static String DESCRIPTOR = "excel merge data file";
	private final static String CORP = "Mermaid";

	private String path;
	private Document document;
	private TreeNode node;

	public TreeWriter(String path, TreeNode node) throws ParserConfigurationException {
		this.path = path;
		this.node = node;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		this.document = builder.newDocument();
	}

	private class TreeNodeAccessCBSave implements TreeNodeAccessCB {
		public Object access(TreeNode node, int depth, Object userData) {
			Corp corp = (Corp) ((DefaultMutableTreeNode) node).getUserObject();
			corp.setDepth(depth);

			Element parentE = (Element) userData;
			Element nodeE = document.createElement("corp");
			parentE.appendChild(nodeE);

			nodeE.setAttribute("name", corp.getName());
			nodeE.setAttribute("id", corp.getId().toString());

			List<CorpExcel> excelList = corp.getAll();
			for (CorpExcel cExcel:excelList) {
				Element excel = document.createElement("excel");
				nodeE.appendChild(excel);

				excel.setAttribute("name", cExcel.getName());
				excel.setAttribute("path", cExcel.getPath());
			}
			
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