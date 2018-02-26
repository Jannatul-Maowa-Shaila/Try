package com.myfirst1.shaila.atry;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by User on 2/23/2018.
 */

public class SelectItem  extends AppCompatActivity {

    private static final String TAG = "SelectItem";

    SelectHelper mDatabaseHelpers;

    private ListView mmListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_list_layout);
        mmListView = (ListView) findViewById(R.id.slistView);
        mDatabaseHelpers = new SelectHelper(this);

        populateListViews();
    }

    private void populateListViews() {
        Log.d(TAG, "populateListViews: Displaying data in the ListView.");

        //getting the data and append to a list
        Cursor data = mDatabaseHelpers.getData();
        ArrayList<String> listDatas = new ArrayList<>();
        while(data.moveToNext()){
            //getting the value from the database in column 1
            //then add it to the ArrayList
            listDatas.add(data.getString(1));
        }
        //create the list adapter and set the adapter
        ListAdapter adapters = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listDatas);
        mmListView.setAdapter(adapters);

        //set an onItemClickListener to the ListView
        mmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);

                Cursor data = mDatabaseHelpers.getItemID(name); //get the id associated with that name
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent edittScreenIntent = new Intent(SelectItem.this, EditDataActivity.class);
                    edittScreenIntent.putExtra("id",itemID);
                    edittScreenIntent.putExtra("name",name);
                    startActivity(edittScreenIntent);
                }
                else{
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }

    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}
