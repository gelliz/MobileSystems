package gev.fit.bstu.by.lr_4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PersonalPageActivity extends AppCompatActivity {

    TextView twName;
    TextView twEmail;
    TextView twLocation;
    TextView twPhone;
    TextView twSocNetw;
    private List<Person> person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_page);

        twName = findViewById(R.id.name);
        twEmail = findViewById(R.id.email);
        twLocation = findViewById(R.id.location);
        twPhone = findViewById(R.id.phone);
        twSocNetw = findViewById(R.id.socNetwork);

        if (person == null) {
            person = new ArrayList<>();
        }
        person = JSONHelper.readFromFile(this);

        Intent intent = getIntent();
        Person p = (Person) intent.getSerializableExtra("person");

        twName.append(p.getName());
        twEmail.append(p.getEmail());
        twLocation.append(p.getLocation());
        twPhone.append(p.getPhone());
        twSocNetw.append(p.getSocialNetwork());
    }

    public void backToMainActivity (View view) {
        finish();
    }

    public void deleteContact(View view) {
        Intent intent = getIntent();
        intent.putExtra("ID", intent.getStringExtra("ID"));
        setResult(RESULT_OK, intent);
        finish();
    }

    public void openMail(View view) {
        Intent mailIntent = new Intent();
        mailIntent.setAction(Intent.ACTION_SEND);
        mailIntent.putExtra(Intent.EXTRA_TEXT, this.twEmail.getText().toString());
        mailIntent.setType("text/plain");

        if (mailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mailIntent);
        }
    }

    public void openMaps(View view) {
        Uri loc = Uri.parse("https://www.google.by/maps/place/" + this.twLocation.getText().toString());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, loc);
        startActivity(mapIntent);
    }

    public void openPhone(View view) {
        Uri number = Uri.parse("tel:" + this.twPhone.getText().toString());
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        startActivity(callIntent);
    }

    public void openSocNetwork(View view) {
        Person p = (Person) getIntent() .getSerializableExtra("person");
        Uri soc_n = Uri.parse("https://vk.com/" + p.getSocialNetwork());
        Intent socIntent = new Intent(Intent.ACTION_VIEW, soc_n);
        startActivity(socIntent);
    }
}