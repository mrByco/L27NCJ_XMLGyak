package hu.saxl27ncj;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyHandler extends DefaultHandler {
	
	private int indenting = 0;


	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		
		this.indenting += 2;
		
		String attrContentStr = "";
		
		for (int i = 0; i < attributes.getLength(); i++) {
			if (i > 0) {
				attrContentStr += ",";
			}
			String name = attributes.getQName(i);
			String value = attributes.getValue(i);
			attrContentStr += name + "=" + value;
		}
		if (attributes.getLength() > 0) {
			attrContentStr = " {" + attrContentStr + "} ";
		}
		
		printLine(qName, " start", attrContentStr);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		printLine(qName, " end", "");
		this.indenting -= 2;
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		
	}
	
	private void printLine(String element, String suffix, String attributes) {
		this.printIndent();
		System.out.print(element + attributes + suffix + "\n");
	}
	
	
	private void printIndent() {
		for (int i = 0; i < indenting; i++) {
			System.out.print(" ");
		}
	}
}