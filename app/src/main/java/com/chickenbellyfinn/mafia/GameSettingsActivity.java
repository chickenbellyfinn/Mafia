package com.chickenbellyfinn.mafia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


public class GameSettingsActivity extends Activity implements GameSettings.GameSettingsListener, HorizontalNumberInput.Listener{

    private static final String TAG = GameSettingsActivity.class.getSimpleName();

    private HorizontalNumberInput playersInput;
    private LinearLayout list;
    private List<HorizontalNumberInput> inputs;
    private Button start;
//    private ListView roleList;
//    private RoleAdapter adapter;

    private GameSettings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Mafia");
        setContentView(R.layout.activity_game_settings);

        settings = new GameSettings(this);

        playersInput = (HorizontalNumberInput)findViewById(R.id.playersInput);
        playersInput.setLabel("Players");
        playersInput.setListener(this);
        list = (LinearLayout)findViewById(R.id.list);
        start = (Button)findViewById(R.id.start);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAssign();
            }
        });

        inputs = new ArrayList<HorizontalNumberInput>();
        for(Role r:settings.roles){
            final HorizontalNumberInput input = new HorizontalNumberInput(this);
            input.setTag(r);
            list.addView(input);
            inputs.add(input);

            input.setListener(new HorizontalNumberInput.Listener() {
                @Override
                public void add() {
                    settings.add(input.getRole());
                }

                @Override
                public void subtract() {
                    settings.subtract(input.getRole());
                }
            });

        }

        settingsChanged();
    }

    public void settingsChanged(){
        Log.d(TAG, "settingsChanged players="+settings.players);
        playersInput.setValue(settings.players);
        playersInput.setAddEnabled(settings.canAddPlayers());
        playersInput.setSubtractEnabled(settings.canRemovePlayers());

        for(HorizontalNumberInput input:inputs){
            Role role = input.getRole();
            input.setLabel(role.name);
            input.setValue(role.count);
            input.setAddEnabled(settings.canAdd(role));
            input.setSubtractEnabled(settings.canSubtract(role));
        }
    }

    @Override
    public void add() {
        Log.d(TAG, "+");
        settings.addPlayer();
    }

    @Override
    public void subtract() {
        Log.d(TAG, "-");
        settings.removePlayer();
    }

    private void startAssign(){

        Intent intent = new Intent(getBaseContext(), AssignRolesActivity.class);
        intent.putExtra("roles", settings.assignRoles());
        startActivity(intent);
    }
}
