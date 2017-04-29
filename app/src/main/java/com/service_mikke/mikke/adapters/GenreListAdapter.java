package com.service_mikke.mikke.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.service_mikke.mikke.R;
import com.service_mikke.mikke.models.Genre;
import com.service_mikke.mikke.models.User;
import com.squareup.picasso.Picasso;

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

        convertView.setTag(genre);
        convertView.setOnClickListener(new GenreRowClick());
        final String genre_name = genre.getName();
        String image_name = genre.getImage();


        int identifier = getContext().getResources().getIdentifier(image_name,"drawable",getContext().getPackageName());
        ImageView genre_image = (ImageView) convertView.findViewById(R.id.genre_imageView);
        TextView genre_name_text_View = (TextView) convertView.findViewById(R.id.genre_name_textView);

        Picasso.with(getContext()).load(identifier).into(genre_image);
        /*if(genre_image != null){
            genre_image.setImageDrawable(null);
        }
        */



        genre_name_text_View.setText(genre_name);
        //genre_image.setImageResource(identifier);

        if(User.getInstance().getSelected_genres().indexOf(genre) >= 0 )
        {
            convertView.findViewById(R.id.checked_image).setVisibility(View.VISIBLE);
            convertView.findViewById(R.id.none_check_image).setVisibility(View.INVISIBLE);
        }else
        {
            convertView.findViewById(R.id.checked_image).setVisibility(View.INVISIBLE);
            convertView.findViewById(R.id.none_check_image).setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    private class GenreRowClick implements View.OnClickListener{
        @Override
        public void onClick(View v){
            Genre genre = (Genre)v.getTag();
            ImageView checked_image = (ImageView)v.findViewById(R.id.checked_image);
            ImageView nonecheck_image = (ImageView)v.findViewById(R.id.none_check_image);
            User user = User.getInstance();

            if(user.hasGenre(genre)){
                user.removeSelectedGenre(genre);
                checked_image.setVisibility(View.INVISIBLE);
                nonecheck_image.setVisibility(View.VISIBLE);
            }else {
                user.addSelectedGenres(genre);
                checked_image.setVisibility(View.VISIBLE);
                nonecheck_image.setVisibility(View.INVISIBLE);
            }
            System.out.println(user.getSelected_genres());
        }
    }
}
