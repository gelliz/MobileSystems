package gev.fit.bstu.by.lr_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    String[] lifestyles = {"Passive lifestyle", "Moderate activity", "Average activity", "High activity", "Sportsman"};
    final static String textViewTexKey = "TEXTVIEW_TEXT";

    RadioButton female;
    Spinner spinner;
    EditText age;
    EditText height;
    EditText weight;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.lifestyles);
        female = findViewById(R.id.female);
        female.setChecked(true);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lifestyles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        result = findViewById(R.id.result);
        outState.putString(textViewTexKey, result.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        String textViewText = savedInstanceState.getString(textViewTexKey);
        result = findViewById(R.id.result);
        result.setText(textViewText);
    }

    public void Calculate(View view) {
        age = findViewById(R.id.age);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);

        double[] AMR = {1.2, 1.375, 1.55, 1.725, 1.9};
        double BMR = 0;
        int res = 0;

        if (age.getText().toString().isEmpty()
                || height.getText().toString().isEmpty()
                || weight.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Not all fields are filled!", Toast.LENGTH_SHORT).show();
            return;
        }

        int ageValue = Integer.parseInt(age.getText().toString());
        int heightValue = Integer.parseInt(height.getText().toString());
        int weightValue = Integer.parseInt(weight.getText().toString());

        if (ageValue<20 || ageValue>100 || heightValue<50 || heightValue>250 || weightValue<5 || weightValue>200)
        {
            Toast.makeText(this, "Incorrect value entered.", Toast.LENGTH_SHORT).show();
        }
        else if (female.isChecked())
        {
            BMR = 655.0955 + (9.5634*weightValue) + (1.8496*heightValue) - (4.6756*ageValue);
        }
        else
        {
            BMR = 66.4730 + (13.7516*weightValue) + (5.0033*heightValue) - (6.7550*ageValue);
        }

        res = (int)(BMR*AMR[spinner.getSelectedItemPosition()]);

        result = findViewById(R.id.result);
        result.setText("" + res);
    }
}