package com.herrera.william.restaurantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean isDataLoaded = false;
    String[][] foodItems;
    String[][] drinkItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // This is called when the data load activity ends.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                try{
                    isDataLoaded = bundle.getBoolean("isDataLoaded");
                }
                catch (Exception e){e.printStackTrace();}

                if(isDataLoaded) {
                    foodItems = (String[][]) bundle.getSerializable("foodItems");
                    drinkItems = (String[][]) bundle.getSerializable("drinkItems");
                    Log.d("DEBUG: ", "food items = " + foodItems.length);
                    Log.d("DEBUG: ", "drink items = " + drinkItems.length);
                }
            }
        }
    }


    // Called when user taps Order button
    public void orderButton (View view){
        if (isDataLoaded){
            Bundle bundle = new Bundle();
            bundle.putSerializable("foodItems", foodItems);
            bundle.putSerializable("drinkItems", drinkItems);
            Intent intent = new Intent(this, OrderActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else
            Toast.makeText(getApplicationContext(), "No data has been loaded.", Toast.LENGTH_LONG).show();
    }

    // Called when user taps Close Out button
    public void closeOutButton (View view){
        Intent intent = new Intent(this, CloseOutActivity.class);
        startActivity(intent);
    }

    // Called when user taps Seating button
    public void seatingButton (View view) {
        Intent intent = new Intent(this, SeatingActivity.class);
        startActivity(intent);
    }

    // Called when user taps Load Data button
    public void loadDataButton (View view) {
        Intent intent = new Intent(this, LoadDataActivity.class);
        startActivityForResult(intent, 1);
    }
}
