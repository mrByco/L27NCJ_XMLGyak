package hu.domparse.l27ncj;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DomQueryL27ncj {

    public static void main(String[] args) {
        Document doc = DomReadL27ncj.Read("XMLL27ncj.xml");

        // Lekérdezés: Legolcsóbb étel
        Element cheapestFood = findCheapestFood(doc);

        // Legérdezés, étterem a legjobb ízzel
        Element highestRatedRestaurant = findHighestRatedOrder(doc);


        Element findUnpaidOrders = findUnpaidOrders(doc);

        // Lekérdezés, Legutóbbi befizetés
        Element latestPaymentDate = findLatestPaymentDate(doc);

        // Lekérdezés: Leghosszabb nevű alkalmazott
        Element employeeWithLongestName = findOwnerWithLongestName(doc);
    }

    private static Element findCheapestFood(Document doc) {
        NodeList foods = doc.getElementsByTagName("Food");
        Element cheapestFood = null;
        BigDecimal lowestPrice = new BigDecimal(Integer.MAX_VALUE);
        for (int i = 0; i < foods.getLength(); i++) {
            Node food = foods.item(i);
            if (food.getNodeType() == Node.ELEMENT_NODE) {
                Element foodElement = (Element) food;
                BigDecimal price = new BigDecimal(foodElement.getElementsByTagName("Price").item(0).getTextContent());
                if (price.compareTo(lowestPrice) < 0) {
                    lowestPrice = price;
                    cheapestFood = foodElement;
                }
            }
        }
        if (cheapestFood != null) {
            System.out.println(formatElement((Element) cheapestFood));
        }
        return cheapestFood;
    }

    private static Element findHighestRatedOrder(Document doc) {
        NodeList orders = doc.getElementsByTagName("Order");
        Element highestRatedOrder = null;
        BigDecimal highestRating = BigDecimal.ZERO;
        for (int i = 0; i < orders.getLength(); i++) {
            Node order = orders.item(i);
            if (order.getNodeType() == Node.ELEMENT_NODE) {
                Element orderElement = (Element) order;
                NodeList tasteRatings = orderElement.getElementsByTagName("TasteRating");
                if (tasteRatings.getLength() > 0) {
                    BigDecimal rating = new BigDecimal(tasteRatings.item(0).getTextContent());
                    if (rating.compareTo(highestRating) > 0) {
                        highestRating = rating;
                        highestRatedOrder = orderElement;
                    }
                }
            }
        }
        if (highestRatedOrder != null) {
            System.out.println(formatElement((Element) highestRatedOrder));
        }
        return highestRatedOrder;
    }

    private static Element findUnpaidOrders(Document doc) {
        NodeList orders = doc.getElementsByTagName("Order");
        Element mostExpensiveIngredient = null;
        
        ArrayList<Element> orderElements = new ArrayList<Element>();
        
        
        BigDecimal highestPrice = BigDecimal.ZERO;
        for (int i = 0; i < orders.getLength(); i++) {
            Node order = orders.item(i);
            Element orderElement = (Element) order;
            boolean hasPayment = orderElement.getElementsByTagName("PaymentDate").getLength() > 0;
            if (!hasPayment) {
            	orderElements.add(orderElement);
            }
        }
        for (int i = 0; i < orderElements.size(); i++) {
            System.out.println(formatElement((Element) orderElements.get(i)));
        }
        return mostExpensiveIngredient;
    }

    private static Element findLatestPaymentDate(Document doc) {
        NodeList orders = doc.getElementsByTagName("Order");
        Element latestOrder = null;
        Date latestDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        for (int i = 0; i < orders.getLength(); i++) {
        	
        	
        	if (latestDate == null) {
        		latestOrder = (Element) orders.item(i);

                NodeList paymentDates = latestOrder.getElementsByTagName("PaymentDate");
        		try {
					latestDate = sdf.parse(paymentDates.item(0).getTextContent());
				} catch (DOMException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
        		break;
        	}
            Node order = orders.item(i);
            if (order.getNodeType() == Node.ELEMENT_NODE) {
                Element orderElement = (Element) order;
                NodeList paymentDates = orderElement.getElementsByTagName("PaymentDate");
                if (paymentDates.getLength() > 0) {
                    try {
                        Date paymentDate = sdf.parse(paymentDates.item(0).getTextContent());
                        if (latestDate == null || paymentDate.after(latestDate)) {
                            latestDate = paymentDate;
                            latestOrder = orderElement;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        } 
        if (latestOrder != null && latestDate != null) {
            System.out.println(formatElement((Element) latestOrder));
        }
        return latestOrder;
    }

    private static Element findOwnerWithLongestName(Document doc) {
        NodeList employees = doc.getElementsByTagName("Owner");
        Element employeeWithLongestName = null;
        int maxLength = 0;
        for (int i = 0; i < employees.getLength(); i++) {
            Node employee = employees.item(i);
            if (employee.getNodeType() == Node.ELEMENT_NODE) {
                Element employeeElement = (Element) employee;
                String name = employeeElement.getElementsByTagName("Name").item(0).getTextContent();
                if (name.length() > maxLength) {
                    maxLength = name.length();
                    employeeWithLongestName = employeeElement;
                }
            }
        }
        if (employeeWithLongestName != null) {
            System.out.println(formatElement((Element) employeeWithLongestName));
        }
        return employeeWithLongestName;
    }
    
    private static String formatElement(Element element) {
        StringBuilder result = new StringBuilder();
        result.append("<").append(element.getTagName());

        // Add attributes if any
        if (element.hasAttributes()) {
            for (int i = 0; i < element.getAttributes().getLength(); i++) {
                result.append(" ")
                        .append(element.getAttributes().item(i).getNodeName())
                        .append("=\"")
                        .append(element.getAttributes().item(i).getNodeValue())
                        .append("\"");
            }
        }

        result.append(">");

        // Add child elements and text content
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                result.append(formatElement((Element) child));
            } else if (child.getNodeType() == Node.TEXT_NODE) {
                result.append(child.getTextContent());
            }
        }

        result.append("</").append(element.getTagName()).append(">");
        return result.toString();
    }
}
