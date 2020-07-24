package com.sanitizer.sanitizeme;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import  java.util.Arrays;
import java.util.ArrayList;


public class CategoryAdapter<callback> extends BaseAdapter {

    static class ViewHolder {
        ImageView catImage;
        TextView catName;
        CheckBox chBox;
        int id;
    }

    private Context context;
    private LayoutInflater inflater;
    private String[] categoryNames;
    private int[] categoryImages;
    private boolean[] thumbnailsselection = new boolean[8];


    ArrayList<String> selectedname = new ArrayList<String>();

    public interface AdapterCallback {
        void getSelectedNameList(ArrayList<String> selectedname);
    }

    final AdapterCallback callback;

    public CategoryAdapter(Context c, String[] categoryNames, int[] categoryImages, AdapterCallback callback) {
        Arrays.fill(thumbnailsselection, false);
        context = c;
        this.categoryImages = categoryImages;
        this.categoryNames = categoryNames;
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return categoryImages.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View gridView = convertView;
        final ViewHolder holder;

        if (gridView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.category_item, null);
            holder.chBox = (CheckBox) gridView.findViewById(R.id.checkBox2);
            holder.catImage = (ImageView) gridView.findViewById(R.id.item_image);
            holder.catName = (TextView) gridView.findViewById(R.id.item_text);
            gridView.setTag(holder);
        } else {
            holder = (ViewHolder) gridView.getTag();
        }

        holder.chBox.setId(position);
        holder.catImage.setId(position);
        holder.catImage.setImageResource(categoryImages[position]);
        holder.catName.setText(categoryNames[position]);
//        imageView.setImageResource(categoryImages[position]);
//        textView.setText(categoryNames[position]);
        holder.chBox.setChecked(thumbnailsselection[position]);
        holder.chBox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;


                if (thumbnailsselection[position]) {
                    thumbnailsselection[position] = false;

                    selectedname.remove(categoryNames[position]);

                } else {
                    thumbnailsselection[position] = true;

                    selectedname.add(categoryNames[position]);

                }
                if (callback != null) {
                 callback.getSelectedNameList(selectedname);
                }

            }
        });


        holder.id = position;

        return gridView;


    }
//        if (inflater == null) {
//            inflater = (LayoutInflater) context
//                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
//        }
//        if(convertView == null) {
//            convertView = inflater.inflate(R.layout.category_item, null);
//        }
//
//        ImageView imageView = convertView.findViewById(R.id.item_image);
//        TextView textView = convertView.findViewById(R.id.item_text);
//        CheckBox checkBox = convertView.findViewById(R.id.checkBox2);
//
//        imageView.setImageResource(categoryImages[position]);
//        textView.setText(categoryNames[position]);
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    // add into arraylist
//                    selectedname.add(categoryNames[position]);
//                }else{
//                    // remove from arraylist
//                    selectedname.remove(categoryNames[position]);
//                }
//
//                if (callback != null) {
//                    callback.getSelectedNameList(selectedname);
//                }
//            }
//
//        });
////
//
//        return convertView;
//    }

}