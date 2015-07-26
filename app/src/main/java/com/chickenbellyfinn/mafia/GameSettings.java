package com.chickenbellyfinn.mafia;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akshay on 7/19/2015.
 */
public class GameSettings {

    private static final String TAG = GameSettings.class.getSimpleName();

    interface GameSettingsListener {
        public void settingsChanged();
    }

    public static final int MIN_PLAYERS = 3;
    public static final int MAX_PLAYERS = Integer.MAX_VALUE;
    public static final int INITIAL_PLAYERS = MIN_PLAYERS;

    private GameSettingsListener listener;
    public int players;
    public List<Role> roles;

    public GameSettings(GameSettingsListener listener){
        this.listener = listener;

        players = MIN_PLAYERS;
        roles = new ArrayList<Role>();

        roles.add(new Role("Mafia", 1, MAX_PLAYERS, 1));
        roles.add(new Role("Doctor", 0, MAX_PLAYERS, 0));
        roles.add(new Role("Detective", 0, MAX_PLAYERS, 0));
    }

    public void addPlayer(){
        players++;
        update();
    }

    public void removePlayer(){
        players--;
        players = Math.max(MIN_PLAYERS, players);
        update();
    }

    public void add(Role role){
        role.count++;
        update();
    }

    public void subtract(Role role){
        role.count--;
        update();
    }

    public boolean canAddPlayers(){
        return true;
    }

    public boolean canRemovePlayers(){
        return players > Math.max(MIN_PLAYERS, roleCount());
    }

    public boolean canAdd(Role role){
        int roleCount = roleCount();

        if(roleCount == players || role.count == role.max){
            return false;
        }

        return true;
    }

    public boolean canSubtract(Role role){
        if(role.count == role.min) return false;

        return true;
    }

    public int roleCount(){
        int total = 0;
        for(Role r:roles){
            total += r.count;
        }
        Log.d(TAG, "total="+total);
        return total;
    }

    private void update(){
        if(listener != null) {
            listener.settingsChanged();
        }
    }

    public Role getByName(String name){
        for(Role r:roles){
            if(r.name.equals(name)){
                return r;
            }
        }
        return null;
    }

    public String[] assignRoles(){
        List<String> unshuffled = new ArrayList<String>();
        List<String> shuffled = new ArrayList<String>();
        for(Role r:roles){
            for(int i = 0; i < r.count; i++){
                unshuffled.add(r.name);
            }
        }

        while(unshuffled.size() < players){
            unshuffled.add("Citizen");
        }

        while(unshuffled.size() > 0){
            shuffled.add(unshuffled.remove((int)(Math.random()*unshuffled.size())));
        }

        Log.d(TAG,"d");
        Log.d(TAG, shuffled.toString());

        return shuffled.toArray(new String[]{});
    }


}
