package gev.fit.bstu.by.lr_4;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.*;
import android.widget.AbsListView.MultiChoiceModeListener;

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

        adapter = new ArrayAdapter<Person>(this, android.R.layout.simple_list_item_activated_1, person);

        contactsList.setAdapter(adapter);
        contactsList.setOnItemClickListener(itemClickListener);

        contactsList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        contactsList.setMultiChoiceModeListener(new MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                getMenuInflater().inflate(R.menu.context_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.viewContacts :
                        if (getSelectedContacts().size() > 1) {
                            Toast.makeText(getApplicationContext(), "Select one contact to view", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Person p = getSelectedContacts().get(0);
                            Intent intent = new Intent(getApplicationContext(), PersonalPageActivity.class);
                            intent.putExtra("person", p);
                            startActivityForResult(intent, 1);
                        }
                        return true;
                    case R.id.editContacts :
                        if (getSelectedContacts().size() > 1) {
                            Toast.makeText(getApplicationContext(), "Select one contact to edit", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Person p = getSelectedContacts().get(0);
                            Intent intent = new Intent(getApplicationContext(), EditContactActivity.class);
                            intent.putExtra("person", p);
                            startActivityForResult(intent, 1);
                        }
                        return true;
                    case R.id.deleteContacts :
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Are you sure that want to delete this contact(s)?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = getIntent();
                                        person.removeAll(getSelectedContacts());
                                        JSONHelper.writeToFile(getApplicationContext(), person);
                                        Toast.makeText(getApplicationContext(), "Contact(s) deleted", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("No", null);
                        builder.create().show();
                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                actionMode = null;
            }
        });
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Person p = (Person) adapterView.getItemAtPosition(i);
            popupMenu(view, p);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.newContact :
                addContact(findViewById(R.id.newContact));
                return true;
            case R.id.editContact :
                Toast toast = Toast.makeText(this, "Select a contact to edit", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                ///////////editContact(findViewById(R.id.editContact));
                return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void popupMenu(final View view, final Person p) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.copyContact :
                        ClipboardManager clipboardManager = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText("", p.getName());
                        clipboardManager.setPrimaryClip(clipData);
                        Toast.makeText(getApplicationContext(), "Contact copied to clipboard", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.deleteContact :
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Are you sure that want to delete this contact?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = getIntent();
                                        Person personById = null;
                                        for (int j = 0; j < person.size(); j++) {
                                            if (person.get(j).getID() == p.getID()) {
                                                personById = person.get(j);
                                                break;
                                            }
                                        }
                                        person.remove(personById);
                                        JSONHelper.writeToFile(getApplicationContext(), person);
                                        Toast.makeText(getApplicationContext(), "Contact deleted", Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("No", null);
                        builder.create().show();
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private List<Person> getSelectedContacts() {
        List<Person> selectedContacts = new ArrayList<>();

        SparseBooleanArray sparseBooleanArray = contactsList.getCheckedItemPositions();
        for (int i = 0; i < sparseBooleanArray.size(); i++) {
            if (sparseBooleanArray.valueAt(i)) {
                Person p = (Person) contactsList.getItemAtPosition(sparseBooleanArray.keyAt(i));
                selectedContacts.add(p);
            }
        }
        return selectedContacts;
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