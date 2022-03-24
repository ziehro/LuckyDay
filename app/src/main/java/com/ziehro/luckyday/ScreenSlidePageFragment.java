package com.ziehro.luckyday;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ScreenSlidePageFragment extends Fragment {

    private static final String LOG_TAG = "AndroidExample";

    private Zodiac zodiac;

    private TextView textViewEmail;
    private TextView textViewPosition;
    private TextView textViewFullName;

    private static int counter = 0;

    // IMPORTANT:
    // Required default public constructor.
    // If configuration change.
    // For example: User rotate the Phone,
    //  Android will create new Fragment (EmployeePageFragment) via default Constructor
    //  so this.employee will be null.

    public ScreenSlidePageFragment() {
    }
    public ScreenSlidePageFragment(Zodiac zodiac) {
        this.zodiac = zodiac;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(
                R.layout.fragment_zodiac_page, container, false);

        counter++;
        if(counter % 2 == 0) {
            view.setBackgroundColor(Color.parseColor("#ebdef0"));
        } else  {
            view.setBackgroundColor(Color.parseColor("#e8f8f5"));
        }

        this.textViewFullName = view.findViewById(R.id.textview_fullname);
        this.textViewPosition = view.findViewById(R.id.textView_position);
        this.textViewEmail = view.findViewById(R.id.textView_email);

        return view;
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Log.i(LOG_TAG, "onSaveInstanceState: save employee data to Bundle");
        // Convert employee object to Bundle.
        Bundle dataBundle = this.employeeToBundle(this.zodiac);
        outState.putAll(dataBundle);

        super.onSaveInstanceState(outState);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onViewStateRestored");

        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);

        if(this.zodiac == null)  {
            Log.i(LOG_TAG, "Get employee data from savedInstanceState");
            // The state was saved by onSaveInstanceState(Bundle outState) method.
            this.zodiac = this.bundleToZodiac(savedInstanceState);
        }
        this.showInGUI(this.zodiac);
    }

    // Call where View ready.
    private void showInGUI(Zodiac employee)  {
        this.textViewFullName.setText(employee.getFullName());
        this.textViewPosition.setText(employee.getPosition());
        this.textViewEmail.setText(employee.getEmail());
    }

    private Bundle employeeToBundle(Zodiac employee)  {
        Bundle bundle = new Bundle();
        bundle.putString("fullName", employee.getFullName());
        bundle.putString("position", employee.getPosition());
        bundle.putString("email", employee.getEmail());

        return bundle;
    }

    private Zodiac bundleToZodiac(Bundle savedInstanceState) {
        String fullName = savedInstanceState.getString("fullName");
        String position = savedInstanceState.getString("position");
        String email = savedInstanceState.getString("email");
        return new Zodiac(fullName, email, position);
    }
}
