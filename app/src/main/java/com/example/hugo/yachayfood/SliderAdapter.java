package com.example.hugo.yachayfood;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.RelativeLayout;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater LayoutInflater;

    public SliderAdapter (Context context){
        this.context = context;
    }

    //Arrays
    public int [] slide_images = {
            R.drawable.orden,
            R.drawable.fresa,
            R.drawable.sanduche

    };
    public String [] slide_headings = {
            "ORDENA",
            "RECIBE",
            "DISFRUTA"

    };
    public String [] slide_descs = {
            "Ordena en cualquier momento y a cualquier lugar",
            "Recibe tu pedido en el lugar especificado",
            "Disfruta y califica al vendedor, animate a ser parte tambien de los vendedores"

    };


    @Override
    public int getCount() {
        return slide_headings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (RelativeLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = LayoutInflater.inflate(R.layout.slide_layout,container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideHeading = (TextView) view.findViewById(R.id.slide_heading);
        TextView  slideDescription = (TextView) view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_images[position]);
        slideHeading.setText(slide_headings[position]);
        slideDescription.setText(slide_descs[position]);

        container.addView(view);
        return view;
    };


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
