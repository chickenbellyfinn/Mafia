package com.chickenbellyfinn.mafia;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.mattkula.secrettextview.SecretTextView;

import java.util.LinkedList;
import java.util.Queue;


public class AssignRolesActivity extends Activity {

    private SecretTextView text;

    private Button hold;
    private Button next;

    private Queue<String> roleQ;

    private boolean holdVisible;
    private boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Mafia");
        setContentView(R.layout.activity_assign_roles);

        String[] roles = getIntent().getStringArrayExtra("roles");
        roleQ = new LinkedList<String>();
        for(String r:roles)roleQ.add(r);

        text = (SecretTextView)findViewById(R.id.secret);
        text.setDuration(500);

        hold = (Button)findViewById(R.id.hold);
        next = (Button)findViewById(R.id.next);

        hold.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    text.show();
                } else if (event.getAction() == MotionEvent.ACTION_UP){
                    text.hide();
                    next.setEnabled(true);
                }

                return true;
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(finished){
                    finish();
                    return;
                }

                if(!holdVisible){
                    text.setText(roleQ.remove());
                    hold.setVisibility(View.VISIBLE);
                    next.setEnabled(false);
                    next.setText("Got it");
                    holdVisible = true;
                } else {
                    hold.setVisibility(View.GONE);
                    next.setText("Show");
                    holdVisible = false;
                }

                if(roleQ.isEmpty()){
                    finished = true;
                    next.setText("Finished");
                }
            }
        });

        holdVisible = false;
        hold.setVisibility(View.GONE);
        next.setText("Show");
    }

}
