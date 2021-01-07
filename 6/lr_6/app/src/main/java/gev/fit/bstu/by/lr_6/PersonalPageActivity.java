package gev.fit.bstu.by.lr_6;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

public class PersonalPageActivity extends AppCompatActivity {

    TextView twName;
    TextView twEmail;
    TextView twLocation;
    TextView twPhone;
    TextView twSocNetw;

    Person person;
    Query emailQuery;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);

        twName = findViewById(R.id.name);
        twEmail = findViewById(R.id.email);
        twLocation = findViewById(R.id.location);
        twPhone = findViewById(R.id.phone);
        twSocNetw = findViewById(R.id.socNetwork);

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference(user.getUid());

        person = (Person) getIntent().getSerializableExtra("person");

        emailQuery = myRef.orderByChild("email").equalTo(person.getEmail());

        twName.append(person.getName());
        twEmail.append(person.getEmail());
        twLocation.append(person.getLocation());
        twPhone.append(person.getPhone());
        twSocNetw.append(person.getSocialNetwork());
    }

    public void editContact (View view) {
        Intent intent = new Intent(this, EditContactActivity.class);
        intent.putExtra("person", person);
        startActivityForResult(intent, 1);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void deleteContact(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure that want to delete this contact?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        emailQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for(DataSnapshot singleSnapshot : snapshot.getChildren()) {
                                    singleSnapshot.getRef().removeValue();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getApplicationContext(), "The read failed:  " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        Toast.makeText(getApplicationContext(), "Contact deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = getIntent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .setNegativeButton("No", null);
        builder.create().show();
    }
}