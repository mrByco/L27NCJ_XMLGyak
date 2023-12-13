package hu.domparse.l27ncj;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomModifyL27ncj {

	public static void main(String[] args) {
		
		
		Document doc = DomReadL27ncj.Read("XMLL27ncj.xml");
		//Document doc = createNewDocument();
		

        //Element rootElement = doc.createElement("L27NCJRestaurants");
        //rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        //rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "XMLSchemaL27ncj.xsd");
        //doc.appendChild(rootElement);
		
		Element rootElement = doc.getDocumentElement();
		
		System.out.println(doc.getDocumentElement().getChildNodes().getLength());
		
		// Add new nodes
        Element newFood = doc.createElement("Food");
        doc.getDocumentElement().appendChild(newFood);
        

        
        newFood.setAttribute("FoodID", "7005");
        
        Element name = doc.createElement("Name");
        name.setTextContent("Csiga olajban sutve");
        newFood.appendChild(name);
        
        
        Element description = doc.createElement("Description");
        description.setTextContent("Extra Maxi csigas");
        newFood.appendChild(description);
        

        Element price = doc.createElement("Price");
        price.setTextContent("1250.00");
        newFood.appendChild(price);
    

        // Remove nodes
        NodeList employees = doc.getElementsByTagName("Owner");
        if (employees.getLength() > 0) {
            Node firstEmployee = employees.item(0);
            doc.getDocumentElement().removeChild(firstEmployee);
        }

        // Modify nodes
        NodeList foods = doc.getElementsByTagName("Food");
        for (int i = 0; i < foods.getLength(); i++) {
            Node food = foods.item(i);
            if (food.getNodeType() == Node.ELEMENT_NODE) {
                Element foodElement = (Element) food;
                if ("Gulyásleves".equals(foodElement.getElementsByTagName("Name").item(0).getTextContent())) {
                    foodElement.getElementsByTagName("Price").item(0).setTextContent("1600.00");
                }
            }
        }
        


		System.out.println(doc.getDocumentElement().getTagName());
		

		System.out.println(XMLUtils.convertDocumentToString(doc));
		

	}

    private static Document createNewDocument() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
    }
}
