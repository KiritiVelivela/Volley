package net.kiriti.peelabus;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kiriti_sai on 15/11/17.
 */

public class CustomAdapter extends ArrayAdapter {

    ArrayList<Item> birdList = new ArrayList<>();

    public CustomAdapter(Context context, int textViewResourceId, ArrayList<Item> objects) {
        super(context, textViewResourceId, objects);
        birdList = objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.main_button, null);
        TextView textView = (TextView) v.findViewById(R.id.textView);
        ImageView imageView = (ImageView) v.findViewById(R.id.icon);
        textView.setText(birdList.get(position).getbirdName());
        Log.i("img","img=="+birdList.get(position).getbirdName());
        Log.i("img","img=="+position);
        imageView.setImageResource(birdList.get(position).getbirdImage());
        return v;

    }
//    Context context;
//    int logos[];
//    LayoutInflater inflter;
//    public CustomAdapter(Context applicationContext, int[] logos) {
//        this.context = applicationContext;
//        this.logos = logos;
//        inflter = (LayoutInflater.from(applicationContext));
//    }
//    @Override
//    public int getCount() {
//        return logos.length;
//    }
//    @Override
//    public Object getItem(int i) {
//        return null;
//    }
//    @Override
//    public long getItemId(int i) {
//        return 0;
//    }
//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        view = inflter.inflate(R.layout.main_button, null); // inflate the layout
//        ImageView icon = (ImageView) view.findViewById(R.id.icon); // get the reference of ImageView
//        icon.setImageResource(logos[i]); // set logo images
//        return view;
//    }
}
