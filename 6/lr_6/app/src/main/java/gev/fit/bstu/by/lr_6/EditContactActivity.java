package gev.fit.bstu.by.lr_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditContactActivity extends AppCompatActivity {

    EditText etName;
    EditText etEmail;
    EditText etLocation;
    EditText etPhone;
    EditText etSocNetw;

    Person person;
    Query emailQuery;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        etName = findViewById(R.id.name);
        etEmail = findViewById(R.id.email);
        etLocation = findViewById(R.id.location);
        etPhone = findViewById(R.id.phone);
        etSocNetw = findViewById(R.id.socNetwork);

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference(user.getUid());

        person = (Person) getIntent().getSerializableExtra("person");

        emailQuery = myRef.orderByChild("email").equalTo(person.getEmail());

        etName.append(person.getName());
        etEmail.append(person.getEmail());
        etLocation.append(person.getLocation());
        etPhone.append(person.getPhone());
        etSocNetw.append(person.getSocialNetwork());
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
                            emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for(DataSnapshot singleSnapshot : snapshot.getChildren()) {
                                        person.setName(etName.getText().toString());
                                        person.setEmail(etEmail.getText().toString());
                                        person.setLocation(etLocation.getText().toString());
                                        person.setPhone(etPhone.getText().toString());
                                        person.setSocialNetwork(etSocNetw.getText().toString());

                                        singleSnapshot.getRef().setValue(person);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getApplicationContext(), "The read failed:  " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            Toast.makeText(getApplicationContext(), "Contact changed", Toast.LENGTH_SHORT).show();
                            Intent intent = getIntent();
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