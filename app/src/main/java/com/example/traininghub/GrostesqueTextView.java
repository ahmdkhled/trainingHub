package com.example.traininghub;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class GrostesqueTextView extends AppCompatTextView {

    public GrostesqueTextView(Context context) {
        super(context);
        setFont();
    }

    public GrostesqueTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFont();
    }

    public GrostesqueTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFont();
    }

    public void setFont(){
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "bodyGrotesqueFitBold.ttf");
        setTypeface(font);
    }

}
