package com.example.simpleparadox.listycity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/* Nahmed2 - Natasha Osmani
*
* Taken form LAB 5 DEMO
* Add cities like in demo (changed layout to contraints to look better rotated
*
* DELETEING CITY:
*   Long click on item in list
*   Fragment pops up
*       Click delete
*   Item is deleted from database
*   OnEvent is called and updates the app view
*
*  */

public class MainActivity extends AppCompatActivity implements DeleteCityFragment.OnFragmentInteractionListener{

    // Declare the variables so that you will be able to reference it later.
    ListView cityList;
    ArrayAdapter<City> cityAdapter;
    ArrayList<City> cityDataList;

    CustomList customList;

    FirebaseFirestore db;
    final String TAG = "Sample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Class variables Lab 5 #9
        Button addCityButton;
        final EditText addCityEditText;
        final EditText addProvinceEditText;

        //References Lab 5 #9
        addCityButton = findViewById(R.id.add_city_button);
        addCityEditText = findViewById(R.id.add_city_field);
        addProvinceEditText = findViewById(R.id.add_province_edit_text);

        //Lab 5 #10
        // Get a reference to the ListView and create an object for the city list
        cityList = findViewById(R.id.city_list);
        cityDataList = new ArrayList<>();
        // Set the adapter for the ListView to the CustomAdapter that we created in Lab 3
        cityAdapter = new CustomList(this, cityDataList);
        cityList.setAdapter(cityAdapter);

        //Lab 5 #11
        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        //Lab 5 #12
        //Get a top level reference to the collection
        final CollectionReference collectionReference = db.collection("Cities");

        //Add button actions
        addCityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String cityName = addCityEditText.getText().toString();
                final String provinceName = addProvinceEditText.getText().toString();

                HashMap<String,String> data = new HashMap<>();  //Key,Value

                if (cityName.length()>0 && provinceName.length()>0) {
                    data.put("Province Name", provinceName);

                    //Store into firestore
                    // The set method sets a unique id for the document
                    collectionReference
                            .document(cityName)
                            .set(data)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // These are a method which gets executed when the task is succeeded
                                    Log.d(TAG, "Data has been added successfully!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // These are a method which gets executed if there’s any problem
                                    Log.d(TAG, "Data could not be added!" + e.toString());
                                }
                            });

                    //Clear the edit text field
                    addCityEditText.setText("");
                    addProvinceEditText.setText("");

                }
            }
        });

        //State of database at any point in time
        //onEvent occurs whenever an event occurs
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable
                    FirebaseFirestoreException error) {

                // Clear the old list
                cityDataList.clear();
                //Fill list with data from database instead
                for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {
                    Log.d(TAG, String.valueOf(doc.getData().get("Province Name")));
                    String city = doc.getId();
                    String province = (String) doc.getData().get("Province Name");
                    cityDataList.add(new City(city, province)); // Adding the cities and provinces from FireStore
                }
                cityAdapter.notifyDataSetChanged(); // Notifying the adapter to render any new data fetched from the cloud
            }
        });


        
        //Long click on city in listview to call delete fragment
        cityList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new DeleteCityFragment(position).show(getSupportFragmentManager(), "EDIT_EXP");
                return true;
            }
        });


//        dataList = new ArrayList<>();
//        dataList.addAll(Arrays.asList(cities));
//
//        cityAdapter = new ArrayAdapter<>(this, R.layout.content, dataList);
//
//        cityList.setAdapter(cityAdapter);

    }

    //If deleting, delete from list
    @Override
    public void onDeletePressed(int position){

        db.collection("Cities")
                .document(cityDataList.get(position).getCityName())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Data successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error deleting data", e);
                    }
                });
    }

}
