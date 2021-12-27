package com.ziehro.luckyday;

import android.content.Intent;
import android.os.Bundle;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class addDataFragment extends Fragment {


    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;



    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_add_data, container, false);
    }





    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user !=null) {
            ImageView profilePic = (ImageView) view.findViewById(R.id.profilePicAddData);
            Picasso.get().load(user.getPhotoUrl()).into(profilePic);
        }
        else {
            ImageView profilePic = (ImageView) view.findViewById(R.id.profilePicAddData);
            profilePic.setImageDrawable(getResources().getDrawable(R.drawable.signed_out));
        }



        Button addLottery=(Button)view.findViewById(R.id.addLottery);
        addLottery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Intent n=new Intent(getContext(), addLottery.class);
                //startActivity(n);

                Intent n=new Intent(getContext(), addLottery.class);
                startActivity(n);


            }
        });

        Button signInPage=(Button)view.findViewById(R.id.sign_in_page_button);
        signInPage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent n=new Intent(getContext(), FirebaseUIActivity.class);
                startActivity(n);
            }
        });

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(addDataFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });
    }
}