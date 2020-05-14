package com.biobogota.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.biobogota.Adaptador;
import com.biobogota.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    ListView lista;
    String[][] datos = {
            {"Jorge Carpintero","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut faucibus massa sed arcu posuere tempor. Donec vel magna accumsan, auctor neque iaculis, rutrum magna. Morbi tempor hendrerit orci et eleifend. Curabitur pellentesque viverra nisl. Vestibulum."},
            {"Andres Gil","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut faucibus massa sed arcu posuere tempor. Donec vel magna accumsan, auctor neque iaculis, rutrum magna. Morbi tempor hendrerit orci et eleifend. Curabitur pellentesque viverra nisl. Vestibulum."},
            {"Alejandra Alezones","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut faucibus massa sed arcu posuere tempor. Donec vel magna accumsan, auctor neque iaculis, rutrum magna. Morbi tempor hendrerit orci et eleifend. Curabitur pellentesque viverra nisl. Vestibulum."},
            {"Daniela Rojas","Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut faucibus massa sed arcu posuere tempor. Donec vel magna accumsan, auctor neque iaculis, rutrum magna. Morbi tempor hendrerit orci et eleifend. Curabitur pellentesque viverra nisl. Vestibulum."},
    };
    int[] datosImg = {R.drawable.unnamed,R.drawable.naturalezaa,R.drawable.restaurante_ecologico,R.drawable.senderos_ecologicos};
    int[] datosImgP = {R.drawable.personal_user,R.drawable.personal_user,R.drawable.profilewomen,R.drawable.profilewomen};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        lista = root.findViewById(R.id.ListNoticias);
        lista.setAdapter(new Adaptador(getContext(),datos,datosImg,datosImgP));

        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }
}