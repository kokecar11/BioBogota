package com.biobogota;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.biobogota.ui.home.HomeFragment;

public class Adaptador extends BaseAdapter {

    private static LayoutInflater inflater = null;

    Context context ;
    String[][]datos;
    int[]datosImg;
    int[]datosImgP;

    public Adaptador(Context context1, String[][]datos, int[] imagenes,int[]imgprofile){

        this.context = context1;
        this.datos = datos;
        this.datosImg = imagenes;
        this.datosImgP = imgprofile;
        inflater = (LayoutInflater)context1.getSystemService(context1.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View vista = inflater.inflate(R.layout.elementos_lista, null);
        TextView user = vista.findViewById(R.id.usertxt);
        TextView post = vista.findViewById(R.id.posttxt);
        ImageView imguser = vista.findViewById(R.id.imgprofilepost);
        ImageView imgpost = vista.findViewById(R.id.imgpost);

        user.setText(datos[position][0]);
        post.setText(datos[position][1]);
        imguser.setImageResource(datosImgP[position]);
        imgpost.setImageResource(datosImg[position]);

        return vista;
    }

    @Override
    public int getCount() {
        return datosImg.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


}
