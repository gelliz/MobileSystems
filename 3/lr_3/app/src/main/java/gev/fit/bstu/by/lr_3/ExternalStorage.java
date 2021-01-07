package gev.fit.bstu.by.lr_3;

import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class ExternalStorage {
    private static final String FILE_NAME = "notes.json";

    private static File getExternalPath() {
        return (new File(Environment.getExternalStorageDirectory(), FILE_NAME));
    }

    static boolean writeToFile(Context context, List<Note> dataList) {

        Gson gson = new Gson();
        InternalStorage.DataItems dataItems = new InternalStorage.DataItems();
        dataItems.setNotes(dataList);
        String jsonString = gson.toJson(dataItems);

        FileOutputStream fileOutputStream = null;

        try {
            File myFile = getExternalPath();
            fileOutputStream = new FileOutputStream(myFile);
            fileOutputStream.write(jsonString.getBytes());
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    static List<Note> readFromFile(Context context) {

        InputStreamReader streamReader = null;
        FileInputStream fileInputStream = null;

        try {
            File myFile = getExternalPath();
            fileInputStream = new FileInputStream(myFile);
            streamReader = new InputStreamReader(fileInputStream);
            Gson gson = new Gson();
            InternalStorage.DataItems dataItems = gson.fromJson(streamReader, InternalStorage.DataItems.class);
            return dataItems.getNotes();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        finally {
            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
