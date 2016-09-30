package com.example.gebruiker.todolist;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    DatabaseHelper myDb;
    EditText editName;
    Button btnAddData;
    private ArrayList<String> list;
    private ListAdapter theAdapter;
    private ListView listView;
    private Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_name);
        btnAddData = (Button) findViewById(R.id.button_add);
        list = new ArrayList<String>();

        AddData();
        viewAll();



        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id){
            {
                if(res!=null){
                    if(res.moveToFirst()){
                        res.moveToPosition(position);
                        String idToDo =    res.getString(res.getColumnIndex("ID"));
                        Integer deletedRows = myDb.deleteData(idToDo);
                        if (deletedRows > 0){
                            Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                            }
                        else
                            Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_LONG).show();
                    }}}
                viewAll();
                return true;
            }});}

    public void AddData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(editName.getText().toString());
                        if (isInserted == true){
                            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        viewAll();
                    }});}

    public void viewAll() {
        list.clear();
        theAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,  list);
        listView = (ListView)findViewById(R.id.theListView);
        listView.setAdapter(theAdapter);
        res = myDb.getAllData();
        while (res.moveToNext()) {
            list.add(res.getString(1));
        }}
}



