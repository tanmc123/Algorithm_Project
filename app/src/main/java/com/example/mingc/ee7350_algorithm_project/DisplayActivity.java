package com.example.mingc.ee7350_algorithm_project;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mingc.ee7350_algorithm_project.model.BackBone;
import com.example.mingc.ee7350_algorithm_project.model.Disk;
import com.example.mingc.ee7350_algorithm_project.model.Point;
import com.example.mingc.ee7350_algorithm_project.model.RandomGeometricGraph;

import com.example.mingc.ee7350_algorithm_project.model.Sphere;
import com.example.mingc.ee7350_algorithm_project.model.Square;
import com.example.mingc.ee7350_algorithm_project.model.Topology;
import com.example.mingc.ee7350_algorithm_project.model.UserInput;
import com.example.mingc.ee7350_algorithm_project.util.ColorUtil;
import com.example.mingc.ee7350_algorithm_project.util.ReportUtil;
import com.example.mingc.ee7350_algorithm_project.views.DiskView;
import com.example.mingc.ee7350_algorithm_project.views.RGGView;
import com.example.mingc.ee7350_algorithm_project.views.SphereView;
import com.example.mingc.ee7350_algorithm_project.views.SqaureView;

import java.util.List;
import java.util.Set;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;
import static com.example.mingc.ee7350_algorithm_project.SettingActivity.KEY_USERINPUT;

/**
 * Created by Mingc on 3/24/2017.
 * This Activity is used to display the generation of certain RGG with
 * the parameters set by user.
 */

public class DisplayActivity extends AppCompatActivity {
    public static final int DRAW_POINT_FLAG = 200;
    public static final int DRAW_EDGES_FLAG = 201;
    public static final int DRAW_EMPTY_FLAG = 202;
    public static final int DRAW_COLORED_POINT_FLAG = 203;

    public static final String KEY_BACKBONE = "backbone";
    public static final String KEY_TOPOLOGY = "topology";

    private Topology topology = Topology.SQUARE;

    RandomGeometricGraph displayRGG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupUI();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home: // Click return button
                finish();
                return true;
            /*case R.id.ic_save:
                return true;*/
        }
        return false;
    }

    private void setupUI(){

        // The userInput that get from Main Activity
        UserInput userInput = getIntent().getParcelableExtra(KEY_USERINPUT);
        if(userInput == null){
            userInput = new UserInput();
        }
        RGGView rggView = null;
        switch (userInput.topology) {
            case SQUARE:
                topology = Topology.SQUARE;
                displayRGG = new Square(userInput);
                setContentView(R.layout.activity_draw_sqaure);
                rggView = (SqaureView) findViewById(R.id.sqaure_view);
                break;

            // Circle Part ---------------------------------------------------------------------------
            case CIRCLE:
                topology = Topology.CIRCLE;
                displayRGG = new Disk(userInput);
                setContentView(R.layout.activity_draw_disk);
                //final DiskView diskView = new DiskView(this);
                rggView = (DiskView) findViewById(R.id.disk_view);
                break;
            case SPHERE:
                topology = Topology.SPHERE;
                displayRGG = new Sphere(userInput);
                setContentView(R.layout.activity_draw_sphere);
                rggView = (SphereView) findViewById(R.id.sphere_view);
                break;

        }

        if(rggView == null){
            return; // rggView has not been set up
        }
        final RGGView finalRggView = rggView;
        Button drawBtn = (Button) findViewById(R.id.display_activity_drawPoint_btn);
        drawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getBaseContext(),"" + displayRGG.getPoints().get(1).x, Toast.LENGTH_LONG).show();

                finalRggView.doDrawPoints(displayRGG);
            }
        });

        Button drawEdgeBtn = (Button) findViewById(R.id.display_activity_drawEdge_btn);
        drawEdgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //displayRGG.bruteForce();
                if (!displayRGG.isPartIPassedFlag()) {
                    displayRGG.cellMethod();
                }

                finalRggView.doDrawEdges(displayRGG);
            }
        });
        // Color Button
        Button colorBtn = (Button) findViewById(R.id.display_activity_color_btn);
        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!displayRGG.isPartIPassedFlag()) {
                    displayRGG.cellMethod();
                }
                if(!displayRGG.isColoredFlag()) {
                    ColorUtil.color(displayRGG);
                }
                finalRggView.doDrawColoredPoint(displayRGG);
            }
        });

        Button toResultBtn = (Button) findViewById(R.id.display_activity_toResultBtn);
        final UserInput finalUserInput = userInput;
        toResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // All requirement for BackBone has created, generate a backbone object and transmit to ResultActivity
                Intent intent = new Intent(DisplayActivity.this, ResultActivity.class);
                intent.putExtra(KEY_USERINPUT, finalUserInput);
                startActivity(intent);
            }
        });


        final TextView speedEditText = (TextView) findViewById(R.id.square_activity_speed_editText);
        SeekBar speedSeekBar = (SeekBar) findViewById(R.id.square_activity_seekBar);
        speedSeekBar.setMax(30);
        speedSeekBar.setProgress(30);
        finalRggView.setDrawSpeed(30);
        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = progress;
                speedEditText.setText("" + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                finalRggView.setDrawSpeed(31 - progress);
                finalRggView.invalidate();
            }
        });


        setTitle("Draw Graph");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }




    // Fake data
    /*private void fakeData(){
        // With the first benchmark of the introduction
        numberOfPoints = 1000;
        aveDegree = 32;
        Distribution = "Square";

        square = new Sqaure(numberOfPoints, aveDegree);

    }*/
}

