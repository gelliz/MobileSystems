package gev.fit.bstu.by.lr_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class AddContactActivity extends AppCompatActivity {

    private List<Person> person;
    EditText etName;
    EditText etEmail;
    EditText etLocation;
    EditText etPhone;
    EditText etSocNetw;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        etName = findViewById(R.id.name);
        etEmail = findViewById(R.id.email);
        etLocation = findViewById(R.id.location);
        etPhone = findViewById(R.id.phone);
        etSocNetw = findViewById(R.id.socNetwork);

        if (person == null) {
            person = new ArrayList<>();
        }
        person = JSONHelper.readFromFile(this);
    }

    public void backToMainActivity (View view) {
        finish();
    }

    public void saveContact(View view) {
        if(!etName.getText().toString().isEmpty()
                &&!etEmail.getText().toString().isEmpty()
                &&!etLocation.getText().toString().isEmpty()
                &&!etPhone.getText().toString().isEmpty()
                &&!etSocNetw.getText().toString().isEmpty())
        {
            Person newPerson = new Person(getID() + 1,
                    etName.getText().toString(),
                    etEmail.getText().toString(),
                    etLocation.getText().toString(),
                    etPhone.getText().toString(),
                    etSocNetw.getText().toString());

            person.add(newPerson);


            JSONHelper.writeToFile(this,  person);
            Toast.makeText(this, "Contact saved", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, intent);
            finish();
        }
        else
        {
            Toast.makeText(this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    public int getID()
    {
        int maxID = 0;
        for (Person p : person) {
            if (maxID < p.getID()) {
                maxID = p.getID();
            }
        }
        return maxID;
    }
}