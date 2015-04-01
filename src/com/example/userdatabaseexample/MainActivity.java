package com.example.userdatabaseexample;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.example.userdatabaseexample.models.Place;
import com.example.userdatabaseexample.models.User;
import com.example.userdatabaseexample.store.UserDatabase;

import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {

    private UserDatabase userDatabase;
    private ArrayAdapter<User> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        userDatabase = UserDatabase.get(this);

        adapter = new ArrayAdapter<User>(MainActivity.this, android.R.layout.simple_list_item_1);
        ListView usersView = (ListView) findViewById(R.id.users);
        usersView.setAdapter(adapter);

        Button makeDb = (Button) findViewById(R.id.makeDb);
        makeDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatabaseWorkTask().execute();
            }
        });

        Button clear = (Button) findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ClearTablesTask().execute();
            }
        });

        new AsyncTask<Void, Void, List<User>>() {
            @Override
            protected List<User> doInBackground(Void... params) {
                return userDatabase.getAllUsers();
            }

            @Override
            protected void onPostExecute(List<User> users) {
                adapter.addAll(users);
            }
        }.execute();
    }

    private class DatabaseWorkTask extends AsyncTask<Void, Void, Void> {

        private List<User> users;
        private List<Place> places;

        @Override
        protected Void doInBackground(Void... params) {

            Random random = new Random();

            userDatabase.addUser(new User("aaa" + random.nextInt(1000), "bbb" + random.nextInt(1000)));
            users = userDatabase.getAllUsers();

            userDatabase.addPlace(new Place("some description_" + random.nextInt(1000), random.nextInt(100)));
            places = userDatabase.getAllPlaces();

            Log.i("MainActivityTag", "places: " + places);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.clear();
            adapter.addAll(users);
        }
    }

    private class ClearTablesTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            userDatabase.removeAllPlaces();
            userDatabase.removeAllUsers();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            adapter.clear();
            adapter.notifyDataSetChanged();
        }
    }

}
