package com.ziehro.luckyday;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
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
import com.google.firebase.firestore.Query;
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
    public String uid = "bob";
    public String NumberOfEntries = "1";
    public String MoonZodiac;
    public String finalUid = uid;





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
        SharedPreferences preferences2 = PreferenceManager.getDefaultSharedPreferences(getContext());
        String flag = preferences2.getString("Flag", "");

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user !=null) {
            uid = user.getUid();
            ImageView profilePic = (ImageView) view.findViewById(R.id.profilePicAddData);
            Picasso.get().load(user.getPhotoUrl()).into(profilePic);
        }
        else {
            uid = "bob";
            ImageView profilePic = (ImageView) view.findViewById(R.id.profilePicAddData);
            profilePic.setImageDrawable(getResources().getDrawable(R.drawable.signed_out));
        }

        if(flag == "1")
        {
            NavHostFragment.findNavController(addDataFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);
        }

        moonDayString = moonPhase1.getMoonAgeAsDaysOnlyInt();
        moonDayStringAddDataPage = moonPhase1.getMoonAgeAsDaysOnly();
        MoonZodiac = moonPhase1.getMoonZodiac();
        moonDayDisplay.setText(moonDayStringAddDataPage + ", " + MoonZodiac);






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

        Button addMajors=(Button)view.findViewById(R.id.addMajors);
        addMajors.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Intent n=new Intent(getContext(), addLottery.class);
                //startActivity(n);
                Toast.makeText(getContext(), "Majors" + "", Toast.LENGTH_SHORT).show();
                Intent n=new Intent(getContext(), addMajors.class);
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
        finalUid = uid;
        greenLight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postGreenLight(finalUid,moonDayString);
                Toast.makeText(getContext(), "Green Light!", Toast.LENGTH_SHORT).show();

            }
        });

        Button redLight=(Button)view.findViewById(R.id.redLightButton);
        finalUid = uid;
        redLight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postRedLight(finalUid,moonDayString);
                Toast.makeText(getContext(), "Red Light!", Toast.LENGTH_SHORT).show();

            }
        });

        Button imBleeding=(Button)view.findViewById(R.id.imBleedingButton);
        String finalUid1 = uid;
        imBleeding.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postImBleeding(finalUid1,moonDayString);
                Toast.makeText(getContext(), "Clean it up!", Toast.LENGTH_SHORT).show();
            }
        });

        Button imCrazy=(Button)view.findViewById(R.id.imCrazyButton);
        //String finalUid1 = uid;
        imCrazy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postImCrazy(finalUid1,moonDayString);
                Toast.makeText(getContext(), "Nuttier than a squirrel turd!", Toast.LENGTH_SHORT).show();
            }
        });

        Button perfectButton=(Button)view.findViewById(R.id.perfectButton);
        //String finalUid1 = uid;
        perfectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                postPerfect(finalUid1,moonDayString);
                Toast.makeText(getContext(), "Ahhhh beauty!", Toast.LENGTH_SHORT).show();
            }
        });

        Button stats = (Button)view.findViewById(R.id.button_first);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(addDataFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
                //Toast.makeText(getContext(), "clicked!", Toast.LENGTH_SHORT).show();


            }
        });



        SeekBar emotions= (SeekBar) view.findViewById(R.id.emotionsSeekbar);
        emotions.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                //Toast.makeText(getContext(), "Emotions Logged", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar stress= (SeekBar) view.findViewById(R.id.stressSeekbar);
        stress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //addDataFunctions.postStress(finalUid1, moonDayString, stress.getProgress());
                //Toast.makeText(getContext(), "Stress Logged", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar energy= (SeekBar) view.findViewById(R.id.energySeekbar);
        energy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //addDataFunctions.postEnergy(finalUid1, moonDayString, energy.getProgress());
                //Toast.makeText(getContext(), "Energy Logged", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        Button sliderSubmit = (Button)view.findViewById(R.id.slider_submit_button);
        sliderSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* SeekBar emotions= (SeekBar) view.findViewById(R.id.emotionsSeekbar);
                SeekBar stress= (SeekBar) view.findViewById(R.id.stressSeekbar);
                SeekBar energy= (SeekBar) view.findViewById(R.id.energySeekbar);*/
                postSliders(finalUid1, moonDayString, emotions.getProgress(), stress.getProgress(), energy.getProgress());
                Toast.makeText(getContext(), "Sliders Logged!", Toast.LENGTH_SHORT).show();


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

    public static void postImBleeding(String uid, String moonDayString) {

        mFirestore = FirebaseFirestore.getInstance();

        Map<String, Integer> imBleeding = new HashMap<>();
        imBleeding.put("ImBleeding", 1);
        //greenlights.put("RedLights", 0);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("ImBleeding").document(uid).collection("Data").document(moonDayString);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        mFirestore.collection("ImBleeding").document(uid).collection("Data").document(moonDayString).update("ImBleeding", FieldValue.increment(1)).addOnSuccessListener(new OnSuccessListener() {
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
                        mFirestore.collection("ImBleeding").document(uid).collection("Data").document(moonDayString).set(imBleeding);
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }
    public void postSliders(String uid, String moonDayString, int progressEmotions, int progressStress, int progressEnergy) {

        mFirestore = FirebaseFirestore.getInstance();

        Map<String, Integer> emotions = new HashMap<>();
        Map<String, Integer> stress = new HashMap<>();
        Map<String, Integer> energy = new HashMap<>();
        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("Human Metrics").document(uid).collection("Data").document(moonDayString);
        DocumentReference docIdRefStress = rootRef.collection("Human Metrics").document(uid).collection("Stress").document(moonDayString);
        DocumentReference docIdRefEnergy = rootRef.collection("Human Metrics").document(uid).collection("Energy").document(moonDayString);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        double counter;
                        counter = document.getDouble("Counter");
                        String CounterUpper = "Emotions"+(int)counter;
                        emotions.put(CounterUpper, progressEmotions);
                        emotions.put("Counter", (int)counter);
                        mFirestore.collection("Human Metrics").document(uid).collection("Data").document(moonDayString).update(CounterUpper, progressEmotions).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(@NonNull Object o) {
                                //Toast.makeText(addDataFragment.this.getActivity(), progressEmotions, Toast.LENGTH_LONG);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "FireStore Failed", Toast.LENGTH_LONG).show();
                            }
                        });
                        mFirestore.collection("Human Metrics").document(uid).collection("Data").document(moonDayString).update("Counter", FieldValue.increment(1));
                        //mFirestore.collection("Human Metrics").document(uid).collection("Data").document(moonDayString).update("Counter", FieldValue.increment(1));
                    } else {
                        Log.d(TAG, "Document does not exist!");
                        double counter = 0;
                        emotions.put("Emotions"+(int)counter, progressEmotions);
                        emotions.put("Counter",(int)counter);
                        mFirestore.collection("Human Metrics").document(uid).collection("Data").document(moonDayString).set(emotions);
                        mFirestore.collection("Human Metrics").document(uid).collection("Data").document(moonDayString).update("Counter", FieldValue.increment(1));
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
        docIdRefStress.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        double counter;
                        counter = document.getDouble("Counter");
                        String CounterUpper = "Stress"+(int)counter;
                        stress.put(CounterUpper, progressStress);
                        stress.put("Counter", (int)counter);
                        mFirestore.collection("Human Metrics").document(uid).collection("Stress").document(moonDayString).update(CounterUpper, progressStress).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(@NonNull Object o) {
                                //Toast.makeText(addDataFragment.this.getActivity(), progressEmotions, Toast.LENGTH_LONG);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "FireStore Failed", Toast.LENGTH_LONG).show();
                            }
                        });
                        mFirestore.collection("Human Metrics").document(uid).collection("Stress").document(moonDayString).update("Counter", FieldValue.increment(1));
                        //mFirestore.collection("Human Metrics").document(uid).collection("Data").document(moonDayString).update("Counter", FieldValue.increment(1));
                    } else {
                        Log.d(TAG, "Document does not exist!");
                        double counter = 0;
                        emotions.put("Stress"+(int)counter, progressStress);
                        emotions.put("Counter",(int)counter);
                        mFirestore.collection("Human Metrics").document(uid).collection("Stress").document(moonDayString).set(emotions);
                        mFirestore.collection("Human Metrics").document(uid).collection("Stress").document(moonDayString).update("Counter", FieldValue.increment(1));
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
        docIdRefEnergy.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        double counter;
                        counter = document.getDouble("Counter");
          //change this line below
                        String CounterUpper = "Stress"+(int)counter;
                        energy.put(CounterUpper, progressEnergy);
                        energy.put("Counter", (int)counter);
                        mFirestore.collection("Human Metrics").document(uid).collection("Energy").document(moonDayString).update(CounterUpper, progressEnergy).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(@NonNull Object o) {
                                //Toast.makeText(addDataFragment.this.getActivity(), progressEmotions, Toast.LENGTH_LONG);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "FireStore Failed", Toast.LENGTH_LONG).show();
                            }
                        });
                        mFirestore.collection("Human Metrics").document(uid).collection("Energy").document(moonDayString).update("Counter", FieldValue.increment(1));
                        //mFirestore.collection("Human Metrics").document(uid).collection("Data").document(moonDayString).update("Counter", FieldValue.increment(1));
                    } else {
                        Log.d(TAG, "Document does not exist!");
                        double counter = 0;
                        emotions.put("Emotions"+(int)counter, progressEmotions);
                        emotions.put("Counter",(int)counter);
                        mFirestore.collection("Human Metrics").document(uid).collection("Energy").document(moonDayString).set(energy);
                        mFirestore.collection("Human Metrics").document(uid).collection("Energy").document(moonDayString).update("Counter", FieldValue.increment(1));
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    public static void postImCrazy(String uid, String moonDayString) {

        mFirestore = FirebaseFirestore.getInstance();

        Map<String, Integer> imCrazy = new HashMap<>();
        imCrazy.put("ImCrazy", 1);
        //greenlights.put("RedLights", 0);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("ImCrazy").document(uid).collection("Data").document(moonDayString);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        mFirestore.collection("ImCrazy").document(uid).collection("Data").document(moonDayString).update("ImCrazy", FieldValue.increment(1)).addOnSuccessListener(new OnSuccessListener() {
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
                        mFirestore.collection("ImCrazy").document(uid).collection("Data").document(moonDayString).set(imCrazy);
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    public static void postPerfect(String uid, String moonDayString) {

        mFirestore = FirebaseFirestore.getInstance();

        Map<String, Integer> Perfect = new HashMap<>();
        Perfect.put("Perfect", 1);
        //greenlights.put("RedLights", 0);

        FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
        DocumentReference docIdRef = rootRef.collection("Perfect").document(uid).collection("Data").document(moonDayString);
        docIdRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "Document exists!");
                        mFirestore.collection("Perfect").document(uid).collection("Data").document(moonDayString).update("Perfect", FieldValue.increment(1)).addOnSuccessListener(new OnSuccessListener() {
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
                        mFirestore.collection("Perfect").document(uid).collection("Data").document(moonDayString).set(Perfect);
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

}