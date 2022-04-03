package com.ziehro.luckyday;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

public class customMarkerView extends MarkerView {

    private TextView tvContent;

    public customMarkerView (Context context, int layoutResource) {
        super(context, layoutResource);
        // this markerview only displays a textview
        tvContent = (TextView) findViewById(R.id.tvContent);
    }
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText("Energy " + e.getY());
        super.refreshContent(e, highlight);
    }

    //@Override
    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        return -(getWidth() / 2);
    }

    //@Override
    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }
}