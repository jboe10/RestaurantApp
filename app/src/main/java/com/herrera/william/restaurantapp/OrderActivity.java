package com.herrera.william.restaurantapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {

    private Button addDrinkButton;
    private TextView summaryTextView;
    int tableNumber = 0;
    int seatNumber = 0;
    private EditText tableValue;
    String[][] foodItems;
    String[][] drinkItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        // Load food and drink array data
        Bundle bundle = getIntent().getExtras();
        foodItems = (String[][]) bundle.getSerializable("foodItems");
        drinkItems = (String[][]) bundle.getSerializable("drinkItems");
        Log.d("DEBUG: ", "food items = " + foodItems.length);
        Log.d("DEBUG: ", "drink items = " + drinkItems.length);

        // Check for table number changes
        EditText tableValue = (EditText)findViewById(R.id.tableValue);
        tableValue.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable value) {
                try {
                    tableNumber = Integer.parseInt(value.toString());
                } catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }
                Log.d("DEBUG", "Table value is " + value.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // Check for seat number changes
        EditText seatValue = (EditText)findViewById(R.id.seatValue);
        seatValue.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable value) {
                try {
                    seatNumber = Integer.parseInt(value.toString());
                } catch(NumberFormatException nfe) {
                    System.out.println("Could not parse " + nfe);
                }
                Log.d("DEBUG", "Seat value is " + value.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
    }

    // Called when user taps Add Drink button
    public void addDrink (View view){
        showPopupMenu(view);
    }

    // Called when user taps Add Food button
    public void addFood (View view){
        showPopupMenu(view);
    }

    // Called whe Submit Order button is selected
    public void submitOrder (View view) {

    }

    //Check that table and seat are good values
    public boolean checkInt(int number) {
        if (number >= 0 && number < 999){
            return true;
        }
        else if (number < 0) {
            Toast.makeText(getApplicationContext(),
                    "Table and seat numbers must be greater than 0", Toast.LENGTH_SHORT).show();
            return false;
        } else if (number > 999) {
            Toast.makeText(getApplicationContext(),
                    "Table and seat numbers must be less than 999", Toast.LENGTH_SHORT).show();
            return false;
        }
        else {
            Toast.makeText(getApplicationContext(), "Failed to detect a number", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    // Called when the add food or drink buttons are pressed.
    public void showPopupMenu(View anchor){
        Log.d("DEBUG", "showPopupMenu method entered.");
        PopupMenu menu = new PopupMenu(this, anchor);

        switch (anchor.getId()) {
            case R.id.addDrinkButton:
                for (int i = 0; i < drinkItems.length; i++){
                menu.getMenu().add(1, i, i, drinkItems[i][1]);
            }
                break;
            case R.id.addFoodButton:
                for (int i = 0; i < foodItems.length; i++){
                    menu.getMenu().add(2, i, i, foodItems[i][1]);
                }
                break;
        }

        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(checkInt(tableNumber) && checkInt(seatNumber)){
                    Toast.makeText(getApplicationContext(),
                            item.getTitle(), Toast.LENGTH_SHORT).show();
                    TextView summaryTextView = (TextView)findViewById(R.id.summaryTextView);
                    summaryTextView.setMovementMethod(new ScrollingMovementMethod());
                    if (item.getGroupId() == 1){
                        summaryTextView.append("\n" + "Table: " + tableNumber + " Seat: " + seatNumber +
                                " --> " + item.getTitle() + " - $" + drinkItems[item.getItemId()][2]);
                    }
                    else{
                        summaryTextView.append("\n" + "Table: " + tableNumber + " Seat: " + seatNumber +
                                " --> " + item.getTitle() + " - $" + foodItems[item.getItemId()][2]);
                    }

                }
                return true;
            }
        });
        menu.show();
    }



}
