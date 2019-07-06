package com.$username$.goalslist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    private ArrayList<Item> items;


    public MyListAdapter(Context ctx, ArrayList<Item> items) {
        this.ctx = ctx;
        this.items = items;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.my_list_item, parent, false);
        }

         final Item item = (Item) getItem(position);
        ((TextView) view.findViewById(R.id.name)).setText(item.getItemName());

        Button delete = view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                items.remove(item);
                Toast.makeText(ctx, "Вы выполнили пункт! Вы - молодец!", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        return view;
    }



}
