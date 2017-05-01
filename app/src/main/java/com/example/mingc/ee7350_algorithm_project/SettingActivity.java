package com.example.mingc.ee7350_algorithm_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.mingc.ee7350_algorithm_project.model.Topology;
import com.example.mingc.ee7350_algorithm_project.model.UserInput;

/**
 * Created by Mingc on 3/24/2017.
 */

public class SettingActivity extends AppCompatActivity{
    public static final String KEY_USERINPUT = "user_input";

    private UserInput userInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInput = new UserInput();
        setupUI();
    }

    private void setupUI(){
        setContentView(R.layout.activity_setting_parameters);
        setTitle("Set Parameters");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: // Click return button
                finish();
                return true;
            case R.id.ic_save:
                saveAndExit();
                return true;
        }
        return false;
    }

    private void saveAndExit() {
        if(userInput == null){
            userInput = new UserInput();
        }
        String numOfPoints = ((EditText) findViewById(R.id.set_item_numOfPoints))
                                .getText().toString();
        String thresholdToConnect = ((EditText) findViewById(R.id.set_item_avgDegree))
                .getText().toString();
        try{
            userInput.numberOfPoints = Integer.valueOf(numOfPoints);
            userInput.avgDegree = Integer.valueOf(thresholdToConnect);
        }catch (NumberFormatException e){
            e.printStackTrace();
            finish();
        }
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_USERINPUT, userInput);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    public void onTopologyTypeClicked(View view){
        // If the button now checked
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) { // Set up RGG type
            case R.id.set_item_type_square_radioBtn:
                if(checked){
                    userInput.topology = Topology.SQUARE;
                }
                break;
            case R.id.set_item_type_circle_radioBtn:
                if(checked){
                    userInput.topology = Topology.CIRCLE;
                }
                break;
            case R.id.set_item_type_sphere_radioBtn:
                if(checked){
                    userInput.topology = Topology.SPHERE;
                }
                break;
        }
    }
}
