package com.ziehro.luckyday;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.grpc.Context;


public class addDataFragment extends Fragment {


    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private static FirebaseFirestore mFirestore;



    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String uid = user.getUid();
    //String uid = "bob";


    final Calendar c = Calendar.getInstance();
    final int hour = c.get(Calendar.HOUR_OF_DAY);
    final int minute = c.get(Calendar.MINUTE);
    final int second = c.get(Calendar.SECOND);
    final int date = c.get(Calendar.DATE);
    final int month = c.get(Calendar.MONTH);
    String time;
    final String monthname=(String)android.text.format.DateFormat.format("MMMM", new Date());
    String moonDayString = "1";
    String moonDayStringAddDataPage = "1";
    MoonPhase moonPhase1 = new MoonPhase(c);
    final double moonPhaseData = MoonPhase.phase(MoonPhase.calendarToJD(c));
    DecimalFormat df = new DecimalFormat("#.##");
    double moonPhaseData2 = Double.parseDouble(df.format(moonPhaseData));
    final String moonAgeString = String.valueOf(moonPhaseData2);
    final String moonPhaseString = moonPhase1.getPhaseIndexString(moonPhase1.getPhaseIndex());
    final public String didItWork = "no";





    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_add_data, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView moonDayDisplay = (TextView)view.findViewById(R.id.moonDayTV);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user !=null) {
            ImageView profilePic = (ImageView) view.findViewById(R.id.profilePicAddData);
            Picasso.get().load(user.getPhotoUrl()).into(profilePic);
        }
        else {
            ImageView profilePic = (ImageView) view.findViewById(R.id.profilePicAddData);
            profilePic.setImageDrawable(getResources().getDrawable(R.drawable.signed_out));
        }

        moonDayString = moonPhase1.getMoonAgeAsDaysOnlyInt();
        moonDayStringAddDataPage = moonPhase1.getMoonAgeAsDaysOnly();
        moonDayDisplay.setText(moonDayStringAddDataPage);





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



        Button greenLight=(Button)view.findViewById(R.id.greenLightButton);
        greenLight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postGreenLight(uid,moonDayString);
                Toast.makeText(getContext(), "Green Light!", Toast.LENGTH_SHORT).show();

            }
        });

        Button redLight=(Button)view.findViewById(R.id.redLightButton);
        redLight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postRedLight(uid,moonDayString);
                Toast.makeText(getContext(), "Red Light!", Toast.LENGTH_SHORT).show();
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

    public static void postRedLight(String uid, String moonDayString) {

        mFirestore = FirebaseFirestore.getInstance();
        String cats = "cats";


        Map<String, Integer> redlights = new HashMap<>();
        redlights.put("RedLights", 1);
        redlights.put("GreenLights", 0);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("RedGreen").document(uid).collection("Data").document(moonDayString);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        mFirestore.collection("RedGreen").document(uid).collection("Data").document(moonDayString).update("RedLights", FieldValue.increment(1)).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(@NonNull Object o) {

                            }


                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Toast.makeText(getContext(), "FireStore Failed", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Log.d(TAG, "Document does not exist!");
                        mFirestore.collection("RedGreen").document(uid).collection("Data").document(moonDayString).set(redlights);
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    public static void postGreenLight(String uid, String moonDayString) {

        mFirestore = FirebaseFirestore.getInstance();

        Map<String, Integer> greenlights = new HashMap<>();
        greenlights.put("GreenLights", 1);
        greenlights.put("RedLights", 0);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("RedGreen").document(uid).collection("Data").document(moonDayString);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        mFirestore.collection("RedGreen").document(uid).collection("Data").document(moonDayString).update("GreenLights", FieldValue.increment(1)).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(@NonNull Object o) {
                                //Toast.makeText(addDataFragment.this.getActivity(), "u", Toast.LENGTH_LONG);

                            }


                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Toast.makeText(getContext(), "FireStore Failed", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Log.d(TAG, "Document does not exist!");
                        mFirestore.collection("RedGreen").document(uid).collection("Data").document(moonDayString).set(greenlights);
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }
}