package DAO;

import models.Ingredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IngredientDAO {
    List<Ingredient> getAllIngredients();

    Ingredient getIngredientById(int ingredientId);

    boolean addIngredient(Ingredient ingredient);

    boolean updateIngredient(Ingredient ingredient);

    boolean deleteIngredient(int ingredientId);

    List<Ingredient> getIngredientsForOrder(int orderId);

}
