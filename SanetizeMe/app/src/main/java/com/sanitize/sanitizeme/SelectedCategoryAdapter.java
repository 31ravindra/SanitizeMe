package com.sanitize.sanitizeme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class SelectedCategoryAdapter extends BaseAdapter {

    Context context;
    private ArrayList<String> selectedCategory;
    private LayoutInflater inflater;

    public SelectedCategoryAdapter(Context context, ArrayList<String> selectedCategory) {
        this.context = context;
        this.selectedCategory = selectedCategory;
    }

    @Override
    public int getCount() {

        return selectedCategory.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View row=convertView;

        if (inflater == null) {
            inflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.category_item, null);
        }

            row = inflater.inflate(R.layout.selected_cat_list, null, true);
        TextView textViewCountry = (TextView) row.findViewById(R.id.aNametxt);
//        TextView textViewCapital = (TextView) row.findViewById(R.id.textViewCapital);
//        ImageView imageFlag = (ImageView) row.findViewById(R.id.imageViewFlag);

        textViewCountry.setText(selectedCategory.get(position));
//        textViewCapital.setText(capitalNames[position]);
//        imageFlag.setImageResource(imageid[position]);
        return  row;
    }
}
