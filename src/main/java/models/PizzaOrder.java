package models;

import java.util.ArrayList;
import java.util.List;

public class PizzaOrder {
    private int orderId;
    private User user;
    private Pizza pizza;
    private List<Ingredient> ingredients;
    private double totalPrice;
    private String status;
    private double basePrice;

    PizzaOrder orderToModify = new PizzaOrder();

    public double calculateTotalPrice() {
        double totalPrice = basePrice; // Start with the base pizza price

        // Add the cost of each selected ingredient to the total price
        if (ingredients != null) {
            for (Ingredient ingredient : ingredients) {
                totalPrice += ingredient.getPrice();
            }
        }

        return totalPrice;
    }

    public PizzaOrder getOrderToModify() {
        return orderToModify;
    }

    public void setOrderToModify(PizzaOrder orderToModify) {
        this.orderToModify = orderToModify;
    }

    public PizzaOrder() {
        ingredients = new ArrayList<>();
        status = "Order";
    }
    public int getUserId() {
        return user.getId();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }
}