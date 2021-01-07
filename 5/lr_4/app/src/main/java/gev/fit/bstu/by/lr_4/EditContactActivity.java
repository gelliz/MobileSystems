package gev.fit.bstu.by.lr_4;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EditContactActivity extends AppCompatActivity {

    EditText etName;
    EditText etEmail;
    EditText etLocation;
    EditText etPhone;
    EditText etSocNetw;
    private List<Person> person;
    int index = 0;
    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        etName = findViewById(R.id.name);
        etEmail = findViewById(R.id.email);
        etLocation = findViewById(R.id.location);
        etPhone = findViewById(R.id.phone);
        etSocNetw = findViewById(R.id.socNetwork);

        if (person == null) {
            person = new ArrayList<>();
        }
        person = JSONHelper.readFromFile(this);

        Person p = (Person) getIntent().getSerializableExtra("person");
        for (int j = 0; j < person.size(); j++) {
            if (person.get(j).getID() == p.getID()) {
                index = j;
                break;
            }
        }

        etName.append(p.getName());
        etEmail.append(p.getEmail());
        etLocation.append(p.getLocation());
        etPhone.append(p.getPhone());
        etSocNetw.append(p.getSocialNetwork());
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure that want to save changes?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            person.get(index).setName(etName.getText().toString());
                            person.get(index).setEmail(etEmail.getText().toString());
                            person.get(index).setLocation(etLocation.getText().toString());
                            person.get(index).setPhone(etPhone.getText().toString());
                            person.get(index).setSocialNetwork(etSocNetw.getText().toString());

                            JSONHelper.writeToFile(getApplicationContext(),  person);
                            Toast.makeText(getApplicationContext(), "Contact changed", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    })
                    .setNegativeButton("No", null);
            builder.create().show();
        }
        else
        {
            Toast.makeText(this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
        }
    }
}