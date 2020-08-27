package com.lifeistech.formation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProjectManagerActivity extends AppCompatActivity {

    //変数宣言
    Button makeProjectButton;
    TextView textView;
    ArrayAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_manager);

        final ListView listView = (ListView)findViewById(R.id.listView);


        listView.setAdapter(adapter);
        adapter = new ArrayAdapter(this,android.R.layout.simple_expandable_list_item_1);

        textView = (TextView) findViewById(R.id.textView3);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //タップの時の処理を書いていく
                ArrayAdapter adapter = (ArrayAdapter)listView.getAdapter();
                String item = (String)adapter.getItem(position);
                adapter.remove(item);
                adapter.add(item);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //長押しした際の処理を書いていく
                ArrayAdapter adapter= (ArrayAdapter)listView.getAdapter();
                String item = (String)adapter.getItem(position);
                adapter.remove(item);
                return false;
            }
        });
        ObjectStrage[] objectStrages;
        objectStrages = ObjectStrage.get(CachePref.KEY_USER_LIST, ObjectStrage[].class);
    }
}
