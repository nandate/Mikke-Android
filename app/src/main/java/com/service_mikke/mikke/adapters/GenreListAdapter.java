package com.service_mikke.mikke.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.service_mikke.mikke.R;
import com.service_mikke.mikke.models.Genre;

import java.util.List;

/**
 * Created by takuya on 4/12/17.
 */

public class GenreListAdapter extends ArrayAdapter<Genre>{
    public GenreListAdapter(Context context, List<Genre> genres){
        super(context, R.layout.genre_list_item,genres);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Genre genre = getItem(position);
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.genre_list_item,parent,false);
        }

        String genre_name = genre.getName();
        String image_name = genre.getImage();

        int identifier = getContext().getResources().getIdentifier(image_name,"drawable",getContext().getPackageName());
        ImageView genre_image = (ImageView) convertView.findViewById(R.id.genre_imageView);
        TextView genre_name_text_View = (TextView) convertView.findViewById(R.id.genre_name_textView);

        genre_name_text_View.setText(genre_name);
        genre_image.setImageResource(identifier);

        return convertView;
    }
}
