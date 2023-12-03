package hu.domparse.l27ncj;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DomWriteL27ncj {
    public static void main(String[] args) {
        // Create a new Document
        Document doc = createNewDocument();

        // Create root element
        Element rootElement = doc.createElement("L27NCJRestaurants");
        rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "XMLSchemaL27ncj.xsd");
        doc.appendChild(rootElement);

        // Add OrderItem elements
        addOrderItem(doc, rootElement, "1001", "5001", "2001", "2", "Elkészült");
        addOrderItem(doc, rootElement, "1002", "5002", "2002", "1", "Folyamatban");
        addOrderItem(doc, rootElement, "1003", "5003", "2003", "3", "Kiszállítva");
        addOrderItem(doc, rootElement, "1004", "5004", "2004", "4", "Megrendelve");

        // Add Order elements
        addOrder(doc, rootElement, "5001", "3001", "7500.00", "4.5", "4.0",
                "2023-04-12T18:30:00", "2023-04-12T19:00:00", "2023-04-12T17:45:00", "Készpénz");
        addOrder(doc, rootElement, "5002", "3002", "3200.00", null, null,
                "2023-04-13T12:00:00", null, null, "Kártya");
        addOrder(doc, rootElement, "5003", "3003", "4500.00", null, null,
                "2023-04-14T20:15:00", null, null, "Online");
        addOrder(doc, rootElement, "5004", "3004", "9800.00", null, null,
                "2023-04-15T13:30:00", null, null, "Utánvét");

        // Add Restaurant elements
        addRestaurant(doc, rootElement, "3001", "Kossuth Lajos utca", "Budapest", "2", "Étterem A");
        addRestaurant(doc, rootElement, "3002", "Széchenyi tér", "Debrecen", "8", "Étterem B");

        // Output the XML content
        System.out.println(XMLUtils.convertDocumentToString(doc));
        
        
        // Write the xml stirng into a file
        String xmlString = XMLUtils.convertDocumentToString(doc);
        
        try {
        	String filename = "XMLL27ncj1.xml";
        	PrintWriter writer = new PrintWriter(new FileWriter(filename), true);
        	writer.println(xmlString);
        	writer.close();
        	System.out.println("File write is completed. Note that generated files are not added automatically to eclipse!");
        	
        }
        catch (Exception e) {
        	System.out.println("An error has happend during file write!");
        	e.printStackTrace();
        }
        
        
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

    private static void addOrderItem(Document doc, Element root, String orderItemID, String orderID, String foodID, String quantity, String state) {
        Element orderItem = doc.createElement("OrderItem");
        orderItem.setAttribute("OrderItemID", orderItemID);
        orderItem.setAttribute("OrderID", orderID);
        orderItem.setAttribute("FoodID", foodID);

        Element quantityElement = doc.createElement("Quantity");
        quantityElement.setTextContent(quantity);
        orderItem.appendChild(quantityElement);

        Element stateElement = doc.createElement("State");
        stateElement.setTextContent(state);
        orderItem.appendChild(stateElement);

        root.appendChild(orderItem);
    }

    private static void addOrder(Document doc, Element root, String orderID, String restaurantID, String totalPrice,
                                 String tasteRating, String serviceRating, String paymentDate, String completionTime,
                                 String startTime, String paymentMethod) {
        Element order = doc.createElement("Order");
        order.setAttribute("OrderID", orderID);
        order.setAttribute("RestaurantID", restaurantID);

        addChildElement(doc, order, "TotalPrice", totalPrice);
        addChildElement(doc, order, "TasteRating", tasteRating);
        addChildElement(doc, order, "ServiceRating", serviceRating);
        addChildElement(doc, order, "PaymentDate", paymentDate);
        addChildElement(doc, order, "CompletionTime", completionTime);
        addChildElement(doc, order, "StartTime", startTime);
        addChildElement(doc, order, "PaymentMethod", paymentMethod);

        root.appendChild(order);
    }

    private static void addRestaurant(Document doc, Element root, String restaurantID, String street, String city, String door, String name) {
        Element restaurant = doc.createElement("Restaurant");
        restaurant.setAttribute("RestaurantID", restaurantID);

        Element address = doc.createElement("Address");
        addChildElement(doc, address, "Street", street);
        addChildElement(doc, address, "City", city);
        addChildElement(doc, address, "Door", door);

        restaurant.appendChild(address);

        addChildElement(doc, restaurant, "Name", name);

        root.appendChild(restaurant);
    }

    private static void addChildElement(Document doc, Element parent, String tagName, String textContent) {
        Element child = doc.createElement(tagName);
        if (textContent != null) {
            child.setTextContent(textContent);
        }
        parent.appendChild(child);
    }
}

class XMLUtils {
    public static String convertDocumentToString(Document doc) {
        try {
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            return writer.getBuffer().toString();
        } catch (TransformerException e) {
            e.printStackTrace();
            return null;
        }
    }
}