package gev.fit.bstu.by.lr_6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddContactActivity extends AppCompatActivity {

    EditText etName;
    EditText etEmail;
    EditText etLocation;
    EditText etPhone;
    EditText etSocNetw;

    Intent intent = new Intent();
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        etName = findViewById(R.id.name);
        etEmail = findViewById(R.id.email);
        etLocation = findViewById(R.id.location);
        etPhone = findViewById(R.id.phone);
        etSocNetw = findViewById(R.id.socNetwork);

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference(user.getUid());
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
            Person newPerson = new Person(
                    etName.getText().toString(),
                    etEmail.getText().toString(),
                    etLocation.getText().toString(),
                    etPhone.getText().toString(),
                    etSocNetw.getText().toString());

            myRef.push().setValue(newPerson);
            Toast.makeText(this, "Contact saved", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK, intent);
            finish();
        }
        else
        {
            Toast.makeText(this, "Fill in all the fields", Toast.LENGTH_SHORT).show();
        }
    }
}