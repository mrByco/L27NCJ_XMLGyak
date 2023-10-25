package hu.saxl27ncj;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

public class SaxL27NCJ {
	
	public static String pathToXml = "/Users/user/Repos/XML/L27NCJ_1025/SaxL27NCJ/src/L27NCJ_kurzusfelvetel.xml";

	public static void main(String[] args) {

	    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	    try {
	        SAXParser saxParser = saxParserFactory.newSAXParser();
	        MyHandler handler = new MyHandler();
	        saxParser.parse(new File(pathToXml), handler);


	    } catch (ParserConfigurationException | SAXException | IOException e) {
	        e.printStackTrace();
	    }

	}

}
