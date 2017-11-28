package com.freelance.jptalusan.algeops.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.freelance.jptalusan.algeops.R;
import com.freelance.jptalusan.algeops.Utilities.Constants;

//http://stackoverflow.com/questions/22274545/add-drawable-in-relative-layout-programmatically
public class MainActivity extends BaseOpsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button chooseAdd = (Button) findViewById(R.id.chooseAdd);
        Button chooseSub = (Button) findViewById(R.id.chooseSubtract);
        Button reset = (Button) findViewById(R.id.reset);

        chooseAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, AddActivity.class);
                addIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(addIntent);
            }
        });

        chooseSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addIntent = new Intent(MainActivity.this, SubtractActivity.class);
                addIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                MainActivity.this.startActivity(addIntent);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences(Constants.PREFS, MODE_PRIVATE);
                prefs.edit().putInt(Constants.SUB_LEVEL, Constants.LEVEL_1).apply();
                prefs.edit().putInt(Constants.CORRECT_SUB_ANSWERS, 0).apply();
                prefs.edit().putInt(Constants.ADD_LEVEL, Constants.LEVEL_1).apply();
                prefs.edit().putInt(Constants.CORRECT_ADD_ANSWERS, 0).apply();
            }
        });
    }
}
