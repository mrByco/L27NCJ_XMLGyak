package hu.domparse.l27ncj;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomModifyL27ncj {

	public static void main(String[] args) {
		
		
		Document doc = DomReadL27ncj.Read("XMLL27ncj.xml");
		
		// Add new nodes
        Element newEmployee = doc.createElement("Employee");
        newEmployee.setAttribute("EmployeeID", "7005");
        Element newName = doc.createElement("Name");
        newName.setTextContent("Fekete Péter");
        Element newIdCardNumber = doc.createElement("IdCardNumber");
        newIdCardNumber.setTextContent("ID13579246");
        newEmployee.appendChild(newName);
        newEmployee.appendChild(newIdCardNumber);
        doc.getDocumentElement().appendChild(newEmployee);

        // Remove nodes
        NodeList employees = doc.getElementsByTagName("Employee");
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
        
        DomReadL27ncj.PrintNode(doc.getDocumentElement(), 0, System.out); 
		

	}
}
