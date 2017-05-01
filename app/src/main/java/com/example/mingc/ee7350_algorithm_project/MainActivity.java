package com.example.mingc.ee7350_algorithm_project;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mingc.ee7350_algorithm_project.model.Topology;
import com.example.mingc.ee7350_algorithm_project.model.UserInput;
import com.example.mingc.ee7350_algorithm_project.util.TopologyUtil;

import static com.example.mingc.ee7350_algorithm_project.SettingActivity.KEY_USERINPUT;

public class MainActivity extends AppCompatActivity {


    private static final int REQ_CODE_SET_PARAMETER_EDIT = 100;

    private UserInput userInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupUI();
    }

    /* This function is used to receive the data transformed from Setting Activity
    *  RequestCode is defined in class and set in Set Parameter Button
    *  resultCode should be RESULT_OK if received successfully
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_CODE_SET_PARAMETER_EDIT && resultCode == Activity.RESULT_OK){
            userInput = data.getParcelableExtra(KEY_USERINPUT);
            // When received a new data, renew the display in main Activity
            setupSettings();
        }
    }

    private void setupUI(){
        setupSettings();

        /*This part is to setup button listener for the button list on main Activity
        * There are two buttons here:
        * Draw Diagram Button and the Set Parameter Button
        * */
        Button drawSquareBtn = (Button) findViewById(R.id.main_draw_square_btn);
        drawSquareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra(KEY_USERINPUT, userInput);
                setResult(Activity.RESULT_OK, intent);
                startActivity(intent);
            }
        });

        Button walkThroughBtn = (Button) findViewById(R.id.main_walk_through_btn);
        walkThroughBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WalkThroughActivity.class);
                startActivity(intent);
            }
        });

        Button setParameterBtn = (Button) findViewById(R.id.main_set_parameters_btn);
        setParameterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                userInput = null;
                startActivityForResult(intent, REQ_CODE_SET_PARAMETER_EDIT);
            }
        });

        /* Set listener to benchmark buttons */
        final Button benchMarkBtn1 = (Button) findViewById(R.id.main_bench_mark1_btn);
        benchMarkBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = new UserInput(1000, 32, Topology.SQUARE);
                setupSettings();
            }
        });

        final Button benchMarkBtn2 = (Button) findViewById(R.id.main_bench_mark2_btn);
        benchMarkBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = new UserInput(4000, 64, Topology.SQUARE);
                setupSettings();
            }
        });

        final Button benchMarkBtn3 = (Button) findViewById(R.id.main_bench_mark3_btn);
        benchMarkBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = new UserInput(16000, 64, Topology.SQUARE);
                setupSettings();
            }
        });

        final Button benchMarkBtn4 = (Button) findViewById(R.id.main_bench_mark4_btn);
        benchMarkBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = new UserInput(64000, 64, Topology.SQUARE);
                setupSettings();
            }
        });

        final Button benchMarkBtn5 = (Button) findViewById(R.id.main_bench_mark5_btn);
        benchMarkBtn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = new UserInput(64000, 128, Topology.SQUARE);
                setupSettings();
            }
        });

        final Button benchMarkBtn6 = (Button) findViewById(R.id.main_bench_mark6_btn);
        benchMarkBtn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = new UserInput(4000, 64, Topology.CIRCLE);
                setupSettings();
            }
        });

        final Button benchMarkBtn7 = (Button) findViewById(R.id.main_bench_mark7_btn);
        benchMarkBtn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = new UserInput(4000, 128, Topology.CIRCLE);
                setupSettings();
            }
        });

        final Button benchMarkBtn8 = (Button) findViewById(R.id.main_bench_mark8_btn);
        benchMarkBtn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = new UserInput(4000, 64, Topology.SPHERE);
                setupSettings();
            }
        });

        final Button benchMarkBtn9 = (Button) findViewById(R.id.main_bench_mark9_btn);
        benchMarkBtn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = new UserInput(16000, 128, Topology.SPHERE);
                setupSettings();
            }
        });

        final Button benchMarkBtn10 = (Button) findViewById(R.id.main_bench_mark10_btn);
        benchMarkBtn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = new UserInput(64000, 128, Topology.SPHERE);
                setupSettings();
            }
        });

    }

    /*
    * This Part is used to add the user set parameters view into the container we already defined
    * in activity_main files
    * */
    private void setupSettings() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.main_user_data_Container);
        linearLayout.removeAllViews();
        View view = setupUserSettingView();
        linearLayout.addView(view);
    }

    // Generate the view by the data in userInput
    private View setupUserSettingView(){
        if(userInput == null){
            userInput = new UserInput();
        }
        View view = getLayoutInflater().inflate(R.layout.user_setting_item, null);
        String topology = TopologyUtil.topologyToString(userInput.topology);
        String point = "The number of points are " + userInput.numberOfPoints;
        String degree = "The average degrees are " + userInput.avgDegree;
        ((TextView) view.findViewById(R.id.set_item_numOfPoints_textview))
                    .setText(point);
        ((TextView) view.findViewById(R.id.set_item_topology_textview))
                .setText(topology);
        ((TextView) view.findViewById(R.id.set_item_avgDegree_textview))
                .setText(degree);
        return view;
    }


}
