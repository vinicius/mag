package asct.ui;

import java.util.Stack;
import java.util.Vector;

import dataTypes.kindOfItens;

class TreeNamePathHolder {
	private String fileName;

	private String filePath;

	private kindOfItens nodeKind;

	private String nodeName;

	private String nodePath;

	private Vector pathVector;

	private String binaryPlatform;

	TreeNamePathHolder(String filePath, String fileName, kindOfItens kind,
			String binaryPlatform) {
		this.filePath = filePath;
		this.fileName = fileName;
		nodeKind = kind;
		pathVector = new Vector();
		nodePath = fileToNode(filePath.substring(filePath.indexOf("/")));

		String lastName = (String) pathVector.firstElement();
		if (!filePath.equals("/")) {
			nodeName = lastName;
		} else {
			nodeName = "/";
		}
		this.binaryPlatform = binaryPlatform;
	}

	/**
	 * @author vidal
	 * @param String
	 *            path - substring de fileNode.getPath() creates an string -
	 *            nodePath, with the name path of the node from of the end of
	 *            string "repository" fills the Vector pathVector - a vector
	 *            representation of nodePath
	 */
	private String fileToNode(String path) {
		Stack stk = new Stack();
		path = path + "/";
		int end = path.length() - 1;
		int nextBegin = 0;
		String subresult = "";
		String result = "";
		for (int pos = nextBegin; pos < end; pos = nextBegin + 1) {
			nextBegin = path.indexOf("/", pos);
			subresult = path.substring(pos, nextBegin);
			stk.push(subresult);
			result = result + subresult + "/";
		}
		int last = stk.size() - 1;
		for (int i = 0; i <= last; i++) {
			pathVector.add(stk.pop());
		}
		return result;
	}// fileToNode method

	void setKind(kindOfItens kind) {
		nodeKind = kind;
	}

	void setPlatform(String platform) {
		binaryPlatform = platform;
	}

	public String toString() {
		return nodeName;
	}

	String getNodePath() {
		return nodePath;
	}

	String getFileName() {
		return fileName;
	}

	String getFilePath() {
		return filePath;
	}

	kindOfItens getKind() {
		return nodeKind;
	}

	String getPlatform() {
		return binaryPlatform;
	}

	String getPath() {
		int end = pathVector.size();
		String nome = "";
		for (int i = 0; i < end; i++) {
			nome = nome + "/" + pathVector.elementAt(i);
		}
		return nome;
	}

	boolean isApp() {
		return (nodeKind.value() == kindOfItens._applicationDirectory);
	}

	boolean isAppDesc() {
		return (nodeKind.value() == kindOfItens._applicationDescriptionFile);
	}

	boolean isBinary() {
		return (nodeKind.value() == kindOfItens._binaryFile);
	}

	boolean isCommonDir() {
		return (nodeKind.value() == kindOfItens._commonDirectory);
	}

	boolean isRoot() {
		return (nodeKind.value() == kindOfItens._rootDirectory);
	}

	// boolean isCommonFile() {
	// return (nodeKind.equals(kindOfItens.));
	// ?

}// TreeNamePathHolder class
