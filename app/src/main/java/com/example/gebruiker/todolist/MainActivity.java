package com.example.gebruiker.todolist;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editText;
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

        editText = (EditText) findViewById(R.id.editText);
        btnAddData = (Button) findViewById(R.id.button_add);
        list = new ArrayList<String>();

        if (savedInstanceState != null){
            String input = (String)savedInstanceState.getString("user input");
            editText.setText(input);
        }

        viewAll();
        addData();
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
                            Toast.makeText(MainActivity.this, "Task removed", Toast.LENGTH_SHORT).show();
                            }
                        else
                            Toast.makeText(MainActivity.this, "Task not remmoved", Toast.LENGTH_SHORT).show();
                    }}}
                viewAll();
                return true;
            }});}

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String userInput = editText.getText().toString();
        outState.putSerializable("user input", userInput);
    }

    public void addData() {
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         boolean isInserted = myDb.insertData(editText.getText().toString());
                            if (isInserted == true) {
                                Toast.makeText(MainActivity.this, "Task added", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(MainActivity.this, "Task not added", Toast.LENGTH_SHORT).show();
                            viewAll();
                            editText.setText("");
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



