package hu.domparse.l27ncj;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DomQueryL27ncj {

    public static void main(String[] args) {
        Document doc = DomReadL27ncj.Read("XMLL27ncj.xml");

        // Lekérdezés: Legolcsóbb étel
        findCheapestFood(doc);

        // Legérdezés, étterem a legjobb ízzel
        findHighestRatedRestaurant(doc);

        // Lekérdezés, legdrágább hozzávaló
        findMostExpensiveIngredient(doc);

        // Lekérdezés, Legutóbbi befizetés
        findLatestPaymentDate(doc);

        // Lekérdezés: Leghosszabb nevű alkalmazott
        findEmployeeWithLongestName(doc);
    }

    private static void findCheapestFood(Document doc) {
        NodeList foods = doc.getElementsByTagName("Food");
        Node cheapestFood = null;
        BigDecimal lowestPrice = new BigDecimal(Integer.MAX_VALUE);
        for (int i = 0; i < foods.getLength(); i++) {
            Node food = foods.item(i);
            if (food.getNodeType() == Node.ELEMENT_NODE) {
                Element foodElement = (Element) food;
                BigDecimal price = new BigDecimal(foodElement.getElementsByTagName("Price").item(0).getTextContent());
                if (price.compareTo(lowestPrice) < 0) {
                    lowestPrice = price;
                    cheapestFood = food;
                }
            }
        }
        if (cheapestFood != null) {
            System.out.println("The cheapest food is: " +
                    ((Element) cheapestFood).getElementsByTagName("Name").item(0).getTextContent() +
                    " with a price of " + lowestPrice);
        }
    }

    private static void findHighestRatedRestaurant(Document doc) {
        NodeList orders = doc.getElementsByTagName("Order");
        Node highestRatedOrder = null;
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
                        highestRatedOrder = order;
                    }
                }
            }
        }
        if (highestRatedOrder != null) {
            System.out.println("The highest-rated restaurant has an order with TasteRating: " + highestRating);
        }
    }

    private static void findMostExpensiveIngredient(Document doc) {
        NodeList ingredients = doc.getElementsByTagName("Ingredient");
        Node mostExpensiveIngredient = null;
        BigDecimal highestPrice = BigDecimal.ZERO;
        for (int i = 0; i < ingredients.getLength(); i++) {
            Node ingredient = ingredients.item(i);
            if (ingredient.getNodeType() == Node.ELEMENT_NODE) {
                Element ingredientElement = (Element) ingredient;
                BigDecimal price = new BigDecimal(ingredientElement.getElementsByTagName("UnitPrice").item(0).getTextContent());
                if (price.compareTo(highestPrice) > 0) {
                    highestPrice = price;
                    mostExpensiveIngredient = ingredient;
                }
            }
        }
        if (mostExpensiveIngredient != null) {
            System.out.println("The most expensive ingredient is: " +
                    ((Element) mostExpensiveIngredient).getElementsByTagName("Name").item(0).getTextContent() +
                    " with a unit price of " + highestPrice);
        }
    }

    private static void findLatestPaymentDate(Document doc) {
        NodeList orders = doc.getElementsByTagName("Order");
        Node latestOrder = null;
        Date latestDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        for (int i = 0; i < orders.getLength(); i++) {
            Node order = orders.item(i);
            if (order.getNodeType() == Node.ELEMENT_NODE) {
                Element orderElement = (Element) order;
                NodeList paymentDates = orderElement.getElementsByTagName("PaymentDate");
                if (paymentDates.getLength() > 0) {
                    try {
                        Date paymentDate = sdf.parse(paymentDates.item(0).getTextContent());
                        if (latestDate == null || paymentDate.after(latestDate)) {
                            latestDate = paymentDate;
                            latestOrder = order;
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (latestOrder != null && latestDate != null) {
            System.out.println("The latest payment date is: " + sdf.format(latestDate));
        }
    }

    private static void findEmployeeWithLongestName(Document doc) {
        NodeList employees = doc.getElementsByTagName("Employee");
        Node employeeWithLongestName = null;
        int maxLength = 0;
        for (int i = 0; i < employees.getLength(); i++) {
            Node employee = employees.item(i);
            if (employee.getNodeType() == Node.ELEMENT_NODE) {
                Element employeeElement = (Element) employee;
                String name = employeeElement.getElementsByTagName("Name").item(0).getTextContent();
                if (name.length() > maxLength) {
                    maxLength = name.length();
                    employeeWithLongestName = employee;
                }
            }
        }
        if (employeeWithLongestName != null) {
            System.out.println("The employee with the longest name is: " +
                    ((Element) employeeWithLongestName).getElementsByTagName("Name").item(0).getTextContent());
        }
    }
}