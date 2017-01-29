package com.freelance.jptalusan.algeops.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.freelance.jptalusan.algeops.R;
import com.freelance.jptalusan.algeops.Utilities.AlgeOpsButtonsOnClickListener;
import com.freelance.jptalusan.algeops.Utilities.Constants;
import com.freelance.jptalusan.algeops.Utilities.Equation;
import com.freelance.jptalusan.algeops.Utilities.EquationGeneration;

//TODO: Add behavior and button responses
public class AddActivity extends BaseOpsActivity {
    private Button newQuestion;

    private ImageButton leftXAdd;
    private ImageButton leftXSub;
    private ImageButton rightXAdd;
    private ImageButton rightXSub;
    private ImageButton leftOneAdd;
    private ImageButton leftOneSub;
    private ImageButton rightOneAdd;
    private ImageButton rightOneSub;

    private RelativeLayout layoutRightX;
    private RelativeLayout layoutLeftX;
    private RelativeLayout layoutRightOne;
    private RelativeLayout layoutLeftOne;

    private TextView firstPartEq;
    private TextView secondPartEq;

    private String firstPart;
    private String secondPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        newQuestion     = (Button) findViewById(R.id.newQuestionButton);

        leftXAdd        = (ImageButton) findViewById(R.id.leftXAdd);
        leftXSub        = (ImageButton) findViewById(R.id.leftXSub);
        rightXAdd       = (ImageButton) findViewById(R.id.rightXAdd);
        rightXSub       = (ImageButton) findViewById(R.id.rightXSub);
        leftOneAdd      = (ImageButton) findViewById(R.id.leftOneAdd);
        leftOneSub      = (ImageButton) findViewById(R.id.leftOneSub);
        rightOneAdd     = (ImageButton) findViewById(R.id.rightOneAdd);
        rightOneSub     = (ImageButton) findViewById(R.id.rightOneSub);

        layoutLeftX     = (RelativeLayout) findViewById(R.id.layoutRightX);
        layoutRightX    = (RelativeLayout) findViewById(R.id.layoutRightX);
        layoutLeftOne   = (RelativeLayout) findViewById(R.id.layoutLeftOne);
        layoutRightOne  = (RelativeLayout) findViewById(R.id.layoutRightOne);

        firstPartEq     = (TextView) findViewById(R.id.firstEqTextView);
        secondPartEq    = (TextView) findViewById(R.id.secondEqTextView);

        newQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Equation eq = EquationGeneration.generateEquation("MAIN");
                firstPart = eq.getPart(1);
                secondPart = eq.getPart(2);

                firstPartEq.setText(firstPart);
                secondPartEq.setText(secondPart);
            }
        });

        //TODO: Create custom relative layout or method to atleast get the existing images in it.
        leftXAdd.setOnClickListener(new AlgeOpsButtonsOnClickListener(this, Constants.OPS_ADD_X, layoutLeftX));
    }
}
