package com.herrera.william.restaurantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public class LoadDataActivity extends AppCompatActivity {

    String JSONmenu;
    String[][] foodItems;
    String[][] drinkItems;
    boolean isDataLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_data);
    }

    @Override
    public void onBackPressed() {
        Log.d("DEBUG: ","Back button pressed, saving food and drink arrays");
        Bundle bundle = new Bundle();
        bundle.putBoolean("isDataLoaded", isDataLoaded);
        bundle.putSerializable("foodItems", foodItems);
        bundle.putSerializable("drinkItems", drinkItems);
        Intent intent = new Intent(this, LoadDataActivity.class);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void downloadDataButton (View view) {
        //Do something to call server and get JSON data
        //This is a test JSON data object.

        JSONmenu = "{\n" +
                "    \"menu\": {\n" +
                "        \"food\": [{\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Hamburgers\",\n" +
                "                \"price\": 5\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"Hot dogs\",\n" +
                "                \"price\": 3\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 3,\n" +
                "                \"name\": \"Pizza\",\n" +
                "                \"price\": 4\n" +
                "            }\n" +
                "        ],\n" +
                "        \"drink\": [{\n" +
                "                \"id\": 1,\n" +
                "                \"name\": \"Coke\",\n" +
                "                \"price\": 2\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 2,\n" +
                "                \"name\": \"Lemonade\",\n" +
                "                \"price\": 2\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": 3,\n" +
                "                \"name\": \"Coffee\",\n" +
                "                \"price\": 3\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";

        try {
            // Create the root JSONObject from the JSON string.
            JSONObject jsonRootObject = new JSONObject(JSONmenu).getJSONObject("menu");

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonDrinkArray = jsonRootObject.optJSONArray("drink");
            JSONArray jsonFoodArray = jsonRootObject.optJSONArray("food");

            // Initialize the food and drink arrays based on length
            drinkItems = new String[jsonDrinkArray.length()][3];
            foodItems = new String[jsonDrinkArray.length()][3];

            Log.d("DEBUG:", "Drink array items: " + jsonDrinkArray.length());
            Log.d("DEBUG:", "Food array items: " + jsonFoodArray.length());

            //Iterate the jsonArray and populate the food and drink arrays from the info of JSONObjects
            for(int i=0; i < jsonDrinkArray.length(); i++){
                JSONObject jsonObject = jsonDrinkArray.getJSONObject(i);
                drinkItems[i][0] = jsonObject.optString("id");
                drinkItems[i][1] = jsonObject.optString("name");
                drinkItems[i][2] = jsonObject.optString("price");
            }

            for(int i=0; i < jsonFoodArray.length(); i++){
                JSONObject jsonObject = jsonFoodArray.getJSONObject(i);
                foodItems[i][0] = jsonObject.optString("id");
                foodItems[i][1] = jsonObject.optString("name");
                foodItems[i][2] = jsonObject.optString("price");
            }
        }
        catch (JSONException e) {e.printStackTrace();}
        isDataLoaded = true;
    }
}
