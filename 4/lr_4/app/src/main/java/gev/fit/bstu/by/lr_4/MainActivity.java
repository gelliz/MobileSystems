package gev.fit.bstu.by.lr_4;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView contactsList;
    private List<Person> person;
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactsList = findViewById(R.id.contactsList);

        if (person == null) {
            person = new ArrayList<>();
        }
        person = JSONHelper.readFromFile(this);

        adapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_1, person);

        contactsList.setAdapter(adapter);
        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Person p = (Person) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), PersonalPageActivity.class);
                intent.putExtra("person", p);
                startActivityForResult(intent, 2);
            }
        };
        contactsList.setOnItemClickListener(itemClickListener);
    }

    public void addContact(View view) {
        Intent intent = new Intent(this, AddContactActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = getIntent();

        if (requestCode == 2) {
            if (data == null) {
                return;
            }
            else {
                Person personById = null;
                Person id = (Person) data.getSerializableExtra("person");
                for (int i = 0; i < person.size(); i++) {
                    if (person.get(i).getID() == id.getID()) {
                        personById = person.get(i);
                        break;
                    }
                }
                person.remove(personById);
                JSONHelper.writeToFile(this, person);
                Toast.makeText(this, "Contact deleted", Toast.LENGTH_SHORT).show();
            }
        }

        finish();
        startActivity(intent);
    }
}