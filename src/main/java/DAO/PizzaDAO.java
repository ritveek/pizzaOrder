package DAO;

import models.Pizza;


import java.util.List;

public interface PizzaDAO {
    List<Pizza> getAllPizzas();

    Pizza getPizzaById(int pizzaId);

    boolean addPizza(Pizza pizza);

    boolean updatePizza(Pizza pizza);

    boolean deletePizza(int pizzaId);

    List<Pizza> getAllPizzaTypes();
}