package com.biobogota.ui.tools;

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

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        final TextView txttitulo = root.findViewById(R.id.txttitulo);
        final TextView txtdescripcion = root.findViewById(R.id.txtdescription);
        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                txttitulo.setText("Parque Simón Bolivar");
                txtdescripcion.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque scelerisque mauris erat, at lacinia enim gravida non. Ut sed lacinia eros, id molestie dolor. Aliquam enim felis, ultricies eget varius eget, dictum non augue. Donec tempor turpis eu nibh mattis, sit amet rutrum felis ornare. Quisque vel neque a sem venenatis suscipit a vitae odio. Proin laoreet ipsum elit, et malesuada risus volutpat sit amet. Integer a elementum nisi, in elementum odio. Maecenas pharetra eget.");
            }
        });
        return root;
    }
}