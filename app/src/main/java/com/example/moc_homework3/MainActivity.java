package com.example.moc_homework3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RvAdapter.ListItemClickListener {

    private EditText text_name;
    private EditText text_number;

    private final String SHARED_PREF_KEY = "shared preferences";
    private final String DATA_KEY = "contacts";
    private final String LOG_KEY = "Main Activity";

    private RecyclerView.Adapter mAdapter;

    private List<Contact> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load data from shared preferences if exist
        loadData();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        Button btn_add = (Button) findViewById(R.id.btn_add);
        text_name       = (EditText) findViewById(R.id.et_name);
        text_number     = (EditText) findViewById(R.id.et_number);

        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new RvAdapter(contacts, this);
        recyclerView.setAdapter(mAdapter);

        // Insert Button clicked
        btn_add.setOnClickListener((v) -> {
            if(text_name.getText().toString().equals("") || text_number.getText().toString().equals("")) {
                showErrorToast();
            }
            else {
                // Add new contact
                contacts.add(new Contact(text_name.getText().toString(), text_number.getText().toString()));

                // Sort collection
                Collections.sort(contacts);

                // Notify adapter on change
                mAdapter.notifyDataSetChanged();

                // Clear inpput
                text_name.setText("");
                text_number.setText("");

                Log.i(LOG_KEY, "List updated!");
                for (Contact items:contacts) {
                    Log.i(LOG_KEY, items.name + " " + items.number);
                }
                Log.i(LOG_KEY, "\n");
            }
        });
    }


    @Override
    public void onListItemClick(int clickedItemIndex) {
        contacts.remove(clickedItemIndex);
        mAdapter.notifyItemRemoved(clickedItemIndex);
    }


    private void showErrorToast() {
        Toast toast = Toast.makeText(
                getApplicationContext(),
                getString(R.string.error_message),
                Toast.LENGTH_SHORT);
        toast.show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(contacts);
        editor.putString(DATA_KEY, json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(DATA_KEY, null);
        Type type = new TypeToken<ArrayList<Contact>>() {}.getType();
        contacts = gson.fromJson(json, type);

        if(contacts == null) {
            contacts = new ArrayList<>();
        }

    }
}
