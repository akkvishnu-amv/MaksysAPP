package com.maksystechnologies.customfonts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;


@SuppressLint("AppCompatCustomView")
public class Button_Poppins_Regular extends Button {

    public Button_Poppins_Regular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Button_Poppins_Regular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Button_Poppins_Regular(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Poppins-Regular.ttf");
            setTypeface(tf);
        }
    }

}