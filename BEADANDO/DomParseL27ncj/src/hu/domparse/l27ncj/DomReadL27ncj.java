package hu.domparse.l27ncj;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
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

public class DomReadL27ncj {

	public static void main(String[] args) {
		Document root = Read(pathToXml);
		PrintNode(root.getDocumentElement(), 0, System.out);
	}

	public static String pathToXml = "XMLL27ncj.xml";
	public int indenting = 0;
	
    public static Document Read(String fromPath) {
    	
        String filePath = pathToXml;
        System.out.println("Reading: " + pathToXml);
        
        File xmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // now XML is loaded as Document in memory, lets convert it to Object List
            System.out.println();
            return doc;

        } catch (SAXException | ParserConfigurationException ex1) {
            ex1.printStackTrace();
            
        }catch ( IOException e1 ) {
        	System.out.println("Error: " + e1.getMessage());
        	System.out.println("Error: " + e1.getLocalizedMessage());
        }
        return null;
    }
    
    /**
     * Prints the text for a node, recursively prints the inner node, and so on
     * @param node
     * @param indenting
     */
    public static void PrintNode(Node node, int indenting, PrintStream stream) {
    	
    	
    	String attrContentStr = extractAttributesStr(node);
    
    	printStartTag(node.getNodeName(), attrContentStr, indenting, stream);
    	
    	NodeList children = node.getChildNodes();
    	
    	
        int length = children.getLength();
        
        if (length > 1) {
            for (int i = 0; i < length; i++) {
         
            	Node child = children.item(i);
            	var subChildren = child.getChildNodes();
            	
            	var nodeName = child.getNodeName();
            	if (nodeName == "#text") {
            		//stream.print(child.getTextContent());
            		//continue;
            	}
            	if (child.getNodeName() == "#comment") {
            		printComment(child.getTextContent(), stream);
            		continue;
            	}

            	if (subChildren.getLength() > 1) {
                	PrintNode(child, indenting + 1, stream);
                	
            	}else if (child.getTextContent().length() > 0) {
            		// Print start and end node
            		if (nodeName != "#text") {
                		stream.print("<" + nodeName + ">");
            		}
            		
            		stream.print(child.getTextContent());

            		if (nodeName != "#text") {
                		stream.print("</" + nodeName + ">");
            		}
            	} else {
            		PrintNode(child, 0, stream);
            	}
            }
    	}else {
    		
    		printLine(node.getTextContent(), "", "", indenting + 2, stream);
    	}
    	printEndTag(node.getNodeName(), indenting, System.out);
    }
    
    /**
     * Prints a node into a text stream
     * @param node
     * @param indenting
     */
    public static void PrintNodeOld(Node node, int indenting, PrintStream stream) {
    	
    	
    	String attrContentStr = extractAttributesStr(node);
    
    	printStartTag(node.getNodeName(), attrContentStr, indenting, stream);
    	
    	NodeList children = node.getChildNodes();
    	
    	
        int length = children.getLength();
        
    	if (length > 1) {
            for (int i = 0; i < length; i++) {
         
            	Node child = children.item(i);
            	var subChildren = child.getChildNodes();
            	if (child.getNodeName() == "#text") {
            		stream.print(child.getFirstChild().getTextContent());
            		continue;
            	}
            	if (child.getNodeName() == "#comment") {
            		printComment(child.getTextContent(), stream);
            		continue;
            	}

            	if (subChildren.getLength() > 1) {
                	PrintNode(child, indenting + 1, stream);
            	}
            }
    	}else {
    		//printLine(node.getTextContent(), "", "", indenting + 2, stream);
    	}
    	printEndTag(node.getNodeName(), indenting, stream);
    	
    	
    }

	private static String extractAttributesStr(Node node) {
		String attrContentStr = "";
		NamedNodeMap namedNodeMap = node.getAttributes();
		
    	if (namedNodeMap != null) {
    		for (int i = 0; i < namedNodeMap.getLength(); i++) {
    			if (i > 0) {
    				attrContentStr += " ";
    			}
    			String name = namedNodeMap.item(i).getNodeName();
    			String value = namedNodeMap.item(i).getNodeValue();
    			attrContentStr += name + "=\"" + value + "\"";
    		}
    		if (namedNodeMap.getLength() > 0) {
    			attrContentStr = " " + attrContentStr + "";
    		}
    	}
		return attrContentStr;
	}

    /**
     * Gets a tag internal text value
     * @param tag
     * @param element
     * @return A string representing the tag value
     */
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
    
    /**
     * PRints a whole line used for the other type of printing out structured data
     * @param element
     * @param suffix used to set start/stop
     * @param attributes Attributes, already formated in correct text format
     * @param indent the indentiation to print
     */
    private static void printLine(String element, String suffix, String attributes, int indent, PrintStream stream) {
		printIndent(indent, stream);
		stream.print(element + attributes + suffix + "\n");
	}
    
    /**
     * Prints a start tag
     * @param element
     * @param attributesStr
     * @param indent
     */
    private static void printStartTag(String element, String attributesStr, int indent, PrintStream stream) {
    	printIndent(indent, stream);
    	stream.print("<" + element + attributesStr + ">\n");
    }
    
    /**
     * Prints an end tag
     * @param element
     * @param indent
     */
    private static void printEndTag(String element, int indent, PrintStream stream) {
    	printIndent(indent, stream);
    	stream.print("</" + element + ">\n");
    }
    
    /**
     * Prints out a comment
     * @param text
     */
    private static void printComment(String text, PrintStream stream) {
    	stream.print("<!--" + text + "-->\n");
    }
	
	/**
	 * Prints the indentation
	 * @param indent
	 */
	private static void printIndent(int indent, PrintStream stream) {
		for (int i = 0; i < indent; i++) {
			stream.print("  ");
		}
	}

}
