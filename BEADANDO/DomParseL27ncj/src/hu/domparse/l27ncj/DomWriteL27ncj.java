package hu.domparse.l27ncj;

import java.io.File;
import java.io.PrintStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DomWriteL27ncj {
	public static void main(String[] args) {

		// Create document builder
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		Document doc = null;
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();

			// Create a new document
			doc = dBuilder.newDocument();

			// Create the root element with namespaces
			Element rootElement = doc.createElement("L27NCJRestaurants");
			rootElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			rootElement.setAttribute("xsi:noNamespaceSchemaLocation", "XMLSchemaL27ncj.xsd");
			doc.appendChild(rootElement);

			// Creating Order items
			Element orderItem1 = doc.createElement("OrderItem");
			orderItem1.setAttribute("OrderItemID", "1005");
			orderItem1.setAttribute("OrderID", "5005");
			orderItem1.setAttribute("FoodID", "2005");
			orderItem1.setTextContent(" ");
			rootElement.appendChild(orderItem1);
			
			
			Element state = doc.createElement("State");
			//state.setTextContent("Elkeszult");
			
			orderItem1.appendChild(state);

			Element orderItem2 = doc.createElement("OrderItem");
			orderItem2.setAttribute("OrderItemID", "1006");
			orderItem2.setAttribute("OrderID", "5006");
			orderItem2.setAttribute("FoodID", "2006");
			rootElement.appendChild(orderItem2);
			

			

			Element orderItem3 = doc.createElement("OrderItem");
			orderItem3.setAttribute("OrderItemID", "1007");
			orderItem3.setAttribute("OrderID", "5007");
			orderItem3.setAttribute("FoodID", "2007");
			rootElement.appendChild(orderItem3);

			// Creating Orders
			Element order1 = doc.createElement("Order");
			order1.setAttribute("OrderID", "5005");
			order1.setAttribute("RestaurantID", "3005");
			rootElement.appendChild(order1);

			Element order2 = doc.createElement("Order");
			order2.setAttribute("OrderID", "5006");
			order2.setAttribute("RestaurantID", "3006");
			rootElement.appendChild(order2);

			Element order3 = doc.createElement("Order");
			order3.setAttribute("OrderID", "5007");
			order3.setAttribute("RestaurantID", "3007");
			rootElement.appendChild(order3);

			// Creating Restaurants
			Element restaurant1 = doc.createElement("Restaurant");
			restaurant1.setAttribute("RestaurantID", "3005");
			rootElement.appendChild(restaurant1);

			Element restaurant2 = doc.createElement("Restaurant");
			restaurant2.setAttribute("RestaurantID", "3006");
			rootElement.appendChild(restaurant2);

			Element restaurant3 = doc.createElement("Restaurant");
			restaurant3.setAttribute("RestaurantID", "3007");
			rootElement.appendChild(restaurant3);

			// Creating Foods
			Element food1 = doc.createElement("Food");
			food1.setAttribute("FoodID", "2005");
			rootElement.appendChild(food1);

			Element food2 = doc.createElement("Food");
			food2.setAttribute("FoodID", "2006");
			rootElement.appendChild(food2);

			Element food3 = doc.createElement("Food");
			food3.setAttribute("FoodID", "2007");
			rootElement.appendChild(food3);

			// Creating Ingredients
			Element ingredient1 = doc.createElement("Ingredient");
			ingredient1.setAttribute("IngredientID", "4005");
			rootElement.appendChild(ingredient1);

			Element ingredient2 = doc.createElement("Ingredient");
			ingredient2.setAttribute("IngredientID", "4006");
			rootElement.appendChild(ingredient2);

			Element ingredient3 = doc.createElement("Ingredient");
			ingredient3.setAttribute("IngredientID", "4007");
			rootElement.appendChild(ingredient3);

			// Creating StockItems
			Element stockItem1 = doc.createElement("StockItem");
			stockItem1.setAttribute("StockItemID", "6005");
			stockItem1.setAttribute("EmployeeID", "7005");
			rootElement.appendChild(stockItem1);

			Element stockItem2 = doc.createElement("StockItem");
			stockItem2.setAttribute("StockItemID", "6006");
			stockItem2.setAttribute("EmployeeID", "7006");
			rootElement.appendChild(stockItem2);

			Element stockItem3 = doc.createElement("StockItem");
			stockItem3.setAttribute("StockItemID", "6007");
			stockItem3.setAttribute("EmployeeID", "7007");
			rootElement.appendChild(stockItem3);

			// Creating Employees
			Element employee1 = doc.createElement("Employee");
			employee1.setAttribute("EmployeeID", "7005");
			rootElement.appendChild(employee1);

			Element employee2 = doc.createElement("Employee");
			employee2.setAttribute("EmployeeID", "7006");
			rootElement.appendChild(employee2);

			Element employee3 = doc.createElement("Employee");
			employee3.setAttribute("EmployeeID", "7007");
			rootElement.appendChild(employee3);
			

			DomReadL27ncj.PrintNode(rootElement, 0, System.out);

		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (doc == null) {
			return;
		}
		
		return;
		/*
		DomReadL27ncj.PrintNode(doc.getDocumentElement(), 0, System.out);

		File file = new File("XMLL27ncj-copy.xml");

		if (!file.canWrite()) {
			System.out.print("File is not writable: " + file.getAbsolutePath());
		}

		try {

			PrintStream fileStream = new PrintStream(file);

			// Write the prolog first
			fileStream.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
			DomReadL27ncj.PrintNode(doc.getDocumentElement(), 0, fileStream);

			fileStream.close();

			System.out.println("File is succesfully writen to: " + file.getAbsolutePath());
			System.out.println("NOTE, THE FILE WON-T BE ADDED AUTOMATICALLY TO ECLIPSE");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}*/

	}
}
