package com.maksystechnologies.customfonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class EditText_Poppins_Semi_Bold extends EditText {

    public EditText_Poppins_Semi_Bold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditText_Poppins_Semi_Bold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditText_Poppins_Semi_Bold(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-SemiBold.ttf");
            setTypeface(tf);
        }
    }

}