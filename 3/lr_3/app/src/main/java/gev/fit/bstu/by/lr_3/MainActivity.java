package gev.fit.bstu.by.lr_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.*;
import android.widget.CalendarView.OnDateChangeListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ID_WRITE_PERMISSION = 200;
    private String date;
    private List<Note> noteInt, noteExt;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    CalendarView clndrView;
    RadioButton rbIntS;
    EditText etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNote = findViewById(R.id.add_note);
        clndrView = findViewById(R.id.clndrView);
        rbIntS = findViewById(R.id.internal);
        rbIntS.setChecked(true);

        Calendar calendar = Calendar.getInstance();
        date = sdf.format(calendar.getTime());

        if (noteInt == null || noteExt == null) {
            noteInt = new ArrayList<>();
            noteExt = new ArrayList<>();
        }

        noteInt = InternalStorage.readFromFile(this);
        noteExt = ExternalStorage.readFromFile(this);
        checkingFileNote();

        clndrView.setOnDateChangeListener(new OnDateChangeListener() {

            @Override
            public  void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selDate = new StringBuilder().append(dayOfMonth).append('-').
                        append(month+1).append('-').append(year).toString();
                Toast.makeText(getApplicationContext(),"Select " + selDate, Toast.LENGTH_SHORT).show();

                date = selDate;
                checkingFileNote();
            }
        });
    }

    public void checkingFileNote() {
        etNote.setText("");
        if (rbIntS.isChecked()) {
            if (noteInt != null) {
                for (int i = 0; i < noteInt.size(); i++) {
                    if (noteInt.get(i).date.equals(date)) {
                        etNote.setText(noteInt.get(i).record);
                    }
                }
            }
        }
        else {
            if (noteExt != null) {
                for (int i = 0; i < noteExt.size(); i++) {
                    if (noteExt.get(i).date.equals(date)) {
                        etNote.setText(noteExt.get(i).record);
                    }
                }
            }
        }
    }

    public void checkingNoteChange() {
        boolean isChange = false;
        String rec = etNote.getText().toString();
        Note nt = new Note(date, rec);

        if (rbIntS.isChecked()) {
            for (int i = 0; i < noteInt.size(); i++) {
                if (noteInt.get(i).date.equals(nt.date)) {
                    noteInt.get(i).record = nt.record;
                    isChange = true;
                    Toast.makeText(this, "The note changed", Toast.LENGTH_SHORT).show();
                }
            }
            if (!isChange) {
                noteInt.add(nt);
                Toast.makeText(this, "The note added", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            for (int i = 0; i < noteExt.size(); i++) {
                if (noteExt.get(i).date.equals(nt.date)) {
                    noteExt.get(i).record = nt.record;
                    isChange = true;
                    Toast.makeText(this, "The note changed", Toast.LENGTH_SHORT).show();
                }
            }
            if (!isChange) {
                noteExt.add(nt);
                Toast.makeText(this, "The note added", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addNoteToFile(View view) {
        checkingNoteChange();
        if (rbIntS.isChecked()) {
            InternalStorage.writeToFile(this, noteInt);
        }
        else {
            permissionToWriteToExtFile(noteExt);
        }
    }

    public void deleteNoteFromFile(View view) {
        if (rbIntS.isChecked()) {
            for (int i = 0; i < noteInt.size(); i++) {
                if (noteInt.get(i).date.equals(date)) {
                    noteInt.remove(i);
                    Toast.makeText(this, "The note deleted", Toast.LENGTH_SHORT).show();
                }
            }
            etNote.setText("");
            InternalStorage.writeToFile(this, noteInt);
        }
        else {
            for (int i = 0; i < noteExt.size(); i++) {
                if (noteExt.get(i).date.equals(date)) {
                    noteExt.remove(i);
                    Toast.makeText(this, "The note deleted", Toast.LENGTH_SHORT).show();
                }
            }
            etNote.setText("");
            permissionToWriteToExtFile(noteExt);
        }
    }

    private boolean askPermission(int requestId, String permissionName) {

        if (android.os.Build.VERSION.SDK_INT >= 23) {
            // Check if we have permission
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // If don't have permission so prompt the user.
                this.requestPermissions(
                        new String[]{permissionName},
                        requestId
                );
                return false;
            }
        }
        return true;
    }

    private void permissionToWriteToExtFile(List<Note> dataList) {
        boolean canWrite = this.askPermission(REQUEST_ID_WRITE_PERMISSION, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(!canWrite)  {
            Toast.makeText(this, "No permission to write to file", Toast.LENGTH_LONG).show();
        }

        ExternalStorage.writeToFile(this, dataList);
    }
}
 