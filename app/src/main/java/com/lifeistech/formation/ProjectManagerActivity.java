package com.lifeistech.formation;

import android.content.Context;
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
    Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_manager);

        final ListView listView = (ListView) findViewById(R.id.listView);

        adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1);
        String[] projectsNames = ObjectStrage.get("project_name_array", String[].class);
        for (int i = 0; i < projectsNames.length; i++) {
            adapter.add(projectsNames[i]);
        }

        listView.setAdapter(adapter);

        textView = (TextView) findViewById(R.id.textView3);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //タップの時の処理を書いていく
                ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();
                String item = (String) adapter.getItem(position);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("project_name", item);
                startActivity(intent);

//                String projectNameEditText = intent.getStringExtra("project_name"); //project1
//                project = ObjectStrage.get(projectNameEditText, Project.class);

//                ObjectStrage.save(project, project.name);
            }
        });

//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                //長押しした際の処理を書いていく
//                ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();
//                String item = (String) adapter.getItem(position);
//                adapter.remove(item);
//                return false;
//            }
//        });
    }
}
