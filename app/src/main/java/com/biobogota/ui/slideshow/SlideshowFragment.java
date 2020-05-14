package com.biobogota.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.biobogota.R;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView titulo = root.findViewById(R.id.txttitulo);
        final TextView tdescrip = root.findViewById(R.id.txtdescription);

        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                titulo.setText("Quebrada La Vieja");
                tdescrip.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque scelerisque mauris erat, at lacinia enim gravida non. Ut sed lacinia eros, id molestie dolor. Aliquam enim felis, ultricies eget varius eget, dictum non augue. Donec tempor turpis eu nibh mattis, sit amet rutrum felis ornare. Quisque vel neque a sem venenatis suscipit a vitae odio. Proin laoreet ipsum elit, et malesuada risus volutpat sit amet. Integer a elementum nisi, in elementum odio. Maecenas pharetra eget.");
            }
        });
        return root;
    }
}