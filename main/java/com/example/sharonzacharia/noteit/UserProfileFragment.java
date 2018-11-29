package com.example.sharonzacharia.noteit;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;


import java.util.Objects;

public class UserProfileFragment extends Fragment {
    MaterialButton logoutbtn;
    GoogleSignInClient mGooglesignClient;
    GoogleSignInOptions gso;
    ImageView close;
    AppCompatCheckBox localcopy;
    SharedPreferences sharedPreferences ;
    TextView name ,email ,notes_count;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.profile_fragment,container,false);
        close = v.findViewById(R.id.close);
        ImageView user_image = v.findViewById(R.id.user_pic);
        Picasso.get().load(LoginActivity.USER_IMG).into(user_image);
        name = v.findViewById(R.id.name);
        email = v.findViewById(R.id.email);
        name.setText(LoginActivity.USER_NAME);
        email.setText(LoginActivity.USER_EMAIL);
        notes_count = v.findViewById(R.id.notes_count);
       //

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        mGooglesignClient = GoogleSignIn.getClient(Objects.requireNonNull(getActivity()).getBaseContext(),gso);
        localcopy = v.findViewById(R.id.local_copy_check_box);
        localcopy.setChecked(sharedPreferences.getBoolean("ENABLE_LOCAL_COPY",false));


        notes_count.setText(" " + NotesListAdapter.NOTES_COUNT + " Notes on cloud");
        logoutbtn = v.findViewById(R.id.logout);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });


        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGooglesignClient.signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Intent i = new Intent(getActivity().getBaseContext(),LoginActivity.class);
                        startActivity(i);
                        getActivity().finish();

                    }
                });
            }
        });


        localcopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean b;

                if (localcopy.isChecked())
                {
                    b = true;}
                else
                {
                    b = false;}
                    sharedPreferences.edit().putBoolean("ENABLE_LOCAL_COPY",b).apply();
            }
        });
        return  v;
    }
}
