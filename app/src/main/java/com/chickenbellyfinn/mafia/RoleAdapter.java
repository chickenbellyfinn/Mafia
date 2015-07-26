package com.chickenbellyfinn.mafia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Akshay on 7/19/2015.
 */
public class RoleAdapter extends BaseAdapter {

    class RoleItemTag {
        TextView label;
        HorizontalNumberInput input;
    }

    private Context context;
    private GameSettings settings;

    public RoleAdapter(Context c, GameSettings settings){
        this.context = c;
        this.settings = settings;
    }

    @Override
    public View getView(int position, View convert, ViewGroup parent){
        View view = convert;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.role_item, null);
            TextView roleNameView = (TextView)view.findViewById(R.id.roleLabel);
            HorizontalNumberInput input  = (HorizontalNumberInput)view.findViewById(R.id.input);
            RoleItemTag tag = new RoleItemTag();
            tag.label = roleNameView;
            tag.input = input;
            view.setTag(tag);
        }

        final Role item = settings.roles.get(position);
        RoleItemTag tag = (RoleItemTag)view.getTag();
        tag.label.setText(item.name);

        tag.input.setValue(item.count);
        tag.input.setAddEnabled(settings.canAdd(item));
        tag.input.setSubtractEnabled(settings.canSubtract(item));

        tag.input.setListener(new HorizontalNumberInput.Listener() {
            @Override
            public void add() {
                settings.add(item);
            }

            @Override
            public void subtract() {
                settings.subtract(item);
            }
        });

        return view;

    }

    @Override
    public Object getItem(int pos){
        return settings.roles.get(pos);
    }

    @Override
    public int getCount() {
        return settings.roles.size();
    }

    @Override
    public long getItemId(int pos){
        return pos;
    }
}
