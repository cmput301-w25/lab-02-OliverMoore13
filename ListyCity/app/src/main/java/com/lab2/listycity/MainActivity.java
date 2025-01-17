package com.lab2.listycity;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    ListView cityList;
    ArrayAdapter<String> cityAdapter;
    ArrayList<String> dataList;

    Button addCity;
    Button deleteCity;
    Button confirm;
    LinearLayout textLayout;
    EditText enterText;
    private int listIndex = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cityList = findViewById(R.id.city_list);
        String []cities = {"Edmonton", "Vancouver", "Ottawa", "Toronto", "Victoria", "St. John's", "Charlottetown", "Yellowknife"};

        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));

        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
        cityList.setAdapter(cityAdapter);

        addCity = (Button)findViewById(R.id.city_add);
        deleteCity = (Button)findViewById(R.id.city_delete);
        confirm = (Button)findViewById(R.id.city_confirm);
        textLayout = findViewById(R.id.enter_text);


        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //On click new EditText created, confirm button enabled, add city button disabled
                enterText = new EditText(MainActivity.this);
                enterText.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                enterText.setHint("Add City Here");
                textLayout.addView(enterText);
                confirm.setEnabled(true);
                addCity.setEnabled(false);
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Takes user input from EditText, if not empty the input is added to the dataList, CityList is updated, confirm button disabled, add city button enabled
                String input = enterText.getText().toString().trim();
                if (!input.isEmpty()) {
                    dataList.add(input);
                    textLayout.removeView(enterText);
                    enterText = null;
                    cityAdapter.notifyDataSetChanged();
                    confirm.setEnabled(false);
                    addCity.setEnabled(true);
                }
            }
        });

        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //If a city is selected its position is saved and delete button enabled
                listIndex = position;
                deleteCity.setEnabled(true);
            }
        });

        deleteCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Removes the selected city from the dataList, cityList is updated, delete button disabled
                if (listIndex != -1) {
                    dataList.remove(listIndex);
                    cityAdapter.notifyDataSetChanged();
                    listIndex = -1;
                    deleteCity.setEnabled(false);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}