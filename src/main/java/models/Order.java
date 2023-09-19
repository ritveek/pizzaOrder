package models;

public class Order extends PizzaOrder {
    private int id;
    private Pizza pizza;
    private User user;
    private double total;
    private OrderStatus status;

    public Order() {
    }

    public Order(Pizza pizza, User user, double total, OrderStatus status) {
        this.pizza = pizza;
        this.user = user;
        this.total = total;
        this.status = status;
    }

    public Order(int id, Pizza pizza, User user, double total, OrderStatus status) {
        this.id = id;
        this.pizza = pizza;
        this.user = user;
        this.total = total;
        this.status = status;
    }

    // Getters and setters for all fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", pizza=" + pizza +
                ", user=" + user +
                ", total=" + total +
                ", status=" + status +
                '}';
    }
}
