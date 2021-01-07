package com.example.Lab7;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {

    EditText nameBox;
    EditText surnameBox;
    EditText yearBox;
    Button delButton;
    Button saveButton;
    CheckBox chbFavorite;

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    ContentValues cv;
    long userId = 0;
    int favorite = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        nameBox = (EditText) findViewById(R.id.name);
        surnameBox = (EditText) findViewById(R.id.surname);
        yearBox = (EditText) findViewById(R.id.year);
        delButton = (Button) findViewById(R.id.deleteButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        chbFavorite = findViewById(R.id.favorite);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        if (userId > 0) {
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            nameBox.setText(userCursor.getString(1));
            surnameBox.setText(userCursor.getString(2));
            yearBox.setText(String.valueOf(userCursor.getInt(3)));
            if (userCursor.getString(4).equals("1"))
                chbFavorite.setChecked(true);

            userCursor.close();
        } else {
            delButton.setVisibility(View.GONE);
        }
    }

    class SaveContact extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cv = new ContentValues();
            if (chbFavorite.isChecked()) {
                favorite = 1;
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            cv.put(DatabaseHelper.COLUMN_NAME, nameBox.getText().toString());
            cv.put(DatabaseHelper.COLUMN_SURNAME, surnameBox.getText().toString());
            cv.put(DatabaseHelper.COLUMN_YEAR, Integer.parseInt(yearBox.getText().toString()));
            cv.put(DatabaseHelper.COLUMN_FAVORITE, favorite);

            if (userId > 0) {
                db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=" + userId, null);
            } else {
                db.insert(DatabaseHelper.TABLE, null, cv);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "Contact saved", Toast.LENGTH_SHORT).show();
            goHome();
        }
    }

    public void save(View view) {
        SaveContact saveContact = new SaveContact();
        saveContact.execute();
    }

    public void delete(View view) {
        db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(userId)});
        goHome();
    }

    private void goHome() {
        db.close();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}