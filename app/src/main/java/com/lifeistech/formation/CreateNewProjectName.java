package com.lifeistech.formation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateNewProjectName extends AppCompatActivity {
    EditText projectNameEditText;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_project_name);
        projectNameEditText = (EditText) findViewById(R.id.createProjectName);

//        Intent intent = getIntent();
//        projectNameEditText = intent.getStringExtra("project_name"); //project1}
    }


    public void nameSave(View v) {
        text = projectNameEditText.getText().toString();

        String[] projectsNames = ObjectStrage.get("project_name_array", String[].class);
        if (projectsNames == null) {
            projectsNames = new String[0];
            ObjectStrage.save(new ArrayList<String>(), "project_name_array");
        }
        List<String> nameList = Arrays.asList(projectsNames);
        ArrayList<String> nameArrayList = new ArrayList<>(nameList);

        for (String projectsName : nameArrayList) {
            if (projectsName.equals(text)) {
                //警告
                Toast.makeText(getApplicationContext(), "Please change the project name", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        nameArrayList.add(text);
        ObjectStrage.save(nameArrayList, "project_name_array");

        //新規作成
        Project project = new Project(new ArrayList<Dancer>(), text,new ArrayList<Integer>());
        ObjectStrage.save(project, project.name);

        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("project_name", project.name);
        startActivity(intent);

    }

}

