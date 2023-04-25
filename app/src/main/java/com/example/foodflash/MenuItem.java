package com.example.foodflash;


public class MenuItem {

    private int mRecipeId;

    private String mRecipeName;
    private String mIngredients;
    private Double mPrice;

    public MenuItem(String recipeName, String ingredients, Double price) {
        mRecipeName = recipeName;
        mIngredients = ingredients;
        mPrice = price;
    }

    @Override
    public String toString() {
        return "RecipeId:" + mRecipeId + + '\n' +
                "Name:" + mRecipeName + '\n' +
                "Ingredients:" + mIngredients + '\n' +
                "Price: " + mPrice + '\n' +
                "=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=" + "\n";
    }

    public int getRecipeId() {
        return mRecipeId;
    }

    public void setRecipeId(int recipeId) {
        mRecipeId = recipeId;
    }

    public String getRecipeName() {
        return mRecipeName;
    }

    public void setRecipeName(String recipeName) {
        mRecipeName = recipeName;
    }

    public String getIngredients() {
        return mIngredients;
    }

    public void setIngredients(String ingredients) {
        mIngredients = ingredients;
    }

    public Double getPrice() {
        return mPrice;
    }

    public void setPrice(Double price) {
        mPrice = price;
    }
}
