package domc0ller1115;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class DomModifyC0LLER {
	 public static void main(String[] args) {
	        try {
	            File xmlFile = new File("C:\\Users\\vanda\\Desktop\\C0LLER_1115\\C0LLER_kurzusfelvetel.xml");
	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	            Document doc = dBuilder.parse(xmlFile);
	            doc.getDocumentElement().normalize();

	            modifyExternal(doc);
	            modifyLanguage(doc, "angol", "német");
	            modifyCoursesForInstructor(doc, "Dr. Bednarik László");
	            
	            saveXmlToFile(doc, "kurzusfelvetelModify1C0LLER.xml");
	            printXmlToConsole(doc);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	 private static void modifyExternal(Document doc) {
		 	String ujOraado1 = "Varga Lajos";
		 	String ujOraado2 = "Szabó Nóra";
		 	
	        NodeList kurzusokNodeList = doc.getElementsByTagName("kurzus");
	        int modifiedCount = 0;

	        for (int i = 0; i < kurzusokNodeList.getLength(); i++) {
	            Element kurzusElement = (Element) kurzusokNodeList.item(i);
	            NodeList oraadoNodeList = kurzusElement.getElementsByTagName("oraado");

	            if (oraadoNodeList.getLength() == 0 && modifiedCount < 2) {
	                Element oraadoElement = doc.createElement("oraado");
	                oraadoElement.appendChild(doc.createTextNode(modifiedCount == 0 ? ujOraado1 : ujOraado2));
	                kurzusElement.appendChild(oraadoElement);

	                modifiedCount++;
	            }
	        }
	    }
	 
	 private static void modifyLanguage(Document doc, String fromLanguage, String toLanguage) {
	        NodeList kurzusokNodeList = doc.getElementsByTagName("kurzus");

	        for (int i = 0; i < kurzusokNodeList.getLength(); i++) {
	            Element kurzusElement = (Element) kurzusokNodeList.item(i);
	            String nyelvAttributum = kurzusElement.getAttribute("nyelv");

	            if (fromLanguage.equals(nyelvAttributum)) {
	                kurzusElement.setAttribute("nyelv", toLanguage);
	            }
	        }
	    }
	 
	 private static void modifyCoursesForInstructor(Document doc, String instructorName) {
	        NodeList kurzusokNodeList = doc.getElementsByTagName("kurzus");

	        for (int i = 0; i < kurzusokNodeList.getLength(); i++) {
	            Element kurzusElement = (Element) kurzusokNodeList.item(i);
	            NodeList oktatoNodeList = kurzusElement.getElementsByTagName("oktato");

	            for (int j = 0; j < oktatoNodeList.getLength(); j++) {
	                Node oktatoNode = oktatoNodeList.item(j);
	                String currentInstructor = oktatoNode.getTextContent();

	                if (instructorName.equals(currentInstructor)) {
	                	kurzusElement.getElementsByTagName("hely").item(0).setTextContent("In/101 In I. e. 101");
	        	        kurzusElement.getElementsByTagName("idopont").item(0).setTextContent("Szerda 12:00-14:00");
	                }
	            }
	        }
	    }

	    private static void printXmlToConsole(Document doc) {
	        try {
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
	            DOMSource source = new DOMSource(doc);
	            StreamResult consoleResult = new StreamResult(System.out);
	            transformer.transform(source, consoleResult);
	            System.out.println("\n\n");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    private static void saveXmlToFile(Document doc, String fileName) {
	        try {
	            TransformerFactory transformerFactory = TransformerFactory.newInstance();
	            Transformer transformer = transformerFactory.newTransformer();
	            DOMSource source = new DOMSource(doc);
	            StreamResult result = new StreamResult(new File(fileName));
	            transformer.transform(source, result);
	            System.out.println("A módosított XML fájl elmentve: " + fileName);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
}
