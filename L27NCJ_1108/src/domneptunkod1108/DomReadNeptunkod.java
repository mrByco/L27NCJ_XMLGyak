package domneptunkod1108;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomReadNeptunkod {
	public static String pathToXml = "/Users/user/Repos/XML/L27NCJ_1025/SaxL27NCJ/src/L27NCJ_kurzusfelvetel.xml";
	public int indenting = 0;
	
    public static void Read(String[] args) {

    	
        String filePath = pathToXml;
        
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getChildNodes();
            
            
            PrintNode(doc.getDocumentElement(), 0);
           
            
            
            // now XML is loaded as Document in memory, lets convert it to Object List
            System.out.println();

        } catch (SAXException | ParserConfigurationException | IOException e1) {
            e1.printStackTrace();
        }
    }
    
    private static void PrintNode(Node node, int indenting) {
    	
    	
    	String attrContentStr = "";
    	NamedNodeMap namedNodeMap = node.getAttributes();
		
		for (int i = 0; i < namedNodeMap.getLength(); i++) {
			if (i > 0) {
				attrContentStr += ",";
			}
			String name = namedNodeMap.item(i).getNodeName();
			String value = namedNodeMap.item(i).getNodeValue();
			attrContentStr += name + "=" + value;
		}
		if (namedNodeMap.getLength() > 0) {
			attrContentStr = " {" + attrContentStr + "} ";
		}
    
    	
    	printLine(node.getNodeName(), " start", attrContentStr, indenting);
    	
    	NodeList children = node.getChildNodes();
    	
    	
        int length = children.getLength();
        
    	if (length > 1) {
            for (int i = 0; i < length; i++) {
         
            	Node child = children.item(i);
            	if (child.getNodeName() == "#text") {
            		continue;
            	}
            	PrintNode(child, indenting + 2);
            }
    	}else {
    		printLine(node.getTextContent(), "", "", indenting + 2);
    	}
    	printLine(node.getNodeName(), " end", "", indenting);
    }
    

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
    
    
    private static void printLine(String element, String suffix, String attributes, int indent) {
		printIndent(indent);
		System.out.print(element + attributes + suffix + "\n");
	}
	
	
	private static void printIndent(int indent) {
		for (int i = 0; i < indent; i++) {
			System.out.print(" ");
		}
	}

}