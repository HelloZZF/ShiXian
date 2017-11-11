package com.example.shixian.user;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.shixian.MainActivity;
import com.example.shixian.R;

/**
 * Created by admin on 2017/11/5.
 */

public class LoginFragment extends Fragment {

    private Button Loginbutton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Loginbutton = (Button) view.findViewById(R.id.login_button);
        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
            }
        });

        return view;
    }
}
