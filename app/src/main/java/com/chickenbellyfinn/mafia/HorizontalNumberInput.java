package com.chickenbellyfinn.mafia;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Akshay on 7/18/2015.
 */
public class HorizontalNumberInput extends LinearLayout {

    interface Listener {
        public void add();
        public void subtract();
    }

    private TextView label;
    private Button minus;
    private Button plus;
    private TextView valueText;

    private Listener listener;

    public HorizontalNumberInput(Context context){
        this(context, null);
    }

    public HorizontalNumberInput(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public HorizontalNumberInput(Context context,AttributeSet attrs, int style){
        super(context, attrs, style);
        init();
    }

    private void init(){
        addView(inflate(getContext(), R.layout.horizontal_number_input, null));

        label = (TextView)findViewById(R.id.label);
        minus = (Button)findViewById(R.id.minus);
        plus = (Button)findViewById(R.id.plus);
        valueText = (TextView)findViewById(R.id.value);


        plus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) listener.add();
            }
        });

        minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) listener.subtract();
            }
        });


    }

    public void setLabel(String s){
        label.setText(s);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public void setValue(int newValue){
        valueText.setText(newValue+"");
    }

    public void setAddEnabled(boolean enabled){
        plus.setEnabled(enabled);
    }

    public void setSubtractEnabled(boolean enabled){
        minus.setEnabled(enabled);
    }

    public Role getRole(){
        return (Role)getTag();
    }

}
