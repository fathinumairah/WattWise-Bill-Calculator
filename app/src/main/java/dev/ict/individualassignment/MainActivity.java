/*
 * *
 *  Copyright (c) 2024 .All rights reserved.
 *  Fathin Umairah Binti Noordin
 * /
 */

package dev.ict.individualassignment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnCalculate, btnClear;
    EditText etUnit, etRebate;
    TextView textViewResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etUnit = findViewById(R.id.etUnit);
        etRebate = findViewById(R.id.etRebate);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnBack);
        textViewResult = findViewById(R.id.textViewResult);

        btnCalculate.setOnClickListener(v -> calculateBill());
        btnClear.setOnClickListener(this);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==R.id.menuAbout) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view == btnClear) {
            clearFields();
        }
    }

    @SuppressLint("SetText18n")
    private void calculateBill() {
        String unitsUsedString = etUnit.getText().toString();
        String rebatePercentageString = etRebate.getText().toString();

        if (unitsUsedString.isEmpty() || rebatePercentageString.isEmpty()) {
            textViewResult.setText("Please Enter Valid Values!");
            return;
        }

        double unitsUsed = Double.parseDouble(unitsUsedString);
        double rebatePercentage = Double.parseDouble(rebatePercentageString) / 100;

        double totalCharges = calculateCharges(unitsUsed);
        double finalCost = totalCharges - (totalCharges * rebatePercentage);

        //Use String.format() to display the result with 2 decimal places
        String result = String.format("Total Final Cost : RM %.2f", finalCost);
        textViewResult.setText(result);
    }

    private double calculateCharges(double unitsUsed) {
        double totalCharges = 0.0;

        if (unitsUsed <= 200) {
            totalCharges = unitsUsed * 0.218;
        } else if (unitsUsed <= 300) {
            totalCharges = (200 * 0.218) + ((unitsUsed - 200) * 0.334);
        } else if (unitsUsed <= 600) {
            totalCharges = (200 * 0.218) + (100 * 0.334) + ((unitsUsed - 300) * 0.516);
        } else if (unitsUsed > 600) {
            totalCharges = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((unitsUsed - 600) * 0.546);
        }

        return totalCharges;

    }

    private void clearFields() {
        etUnit.setText("");
        etRebate.setText("");
        textViewResult.setText("Total Final Cost : RM 0");
    }
}



