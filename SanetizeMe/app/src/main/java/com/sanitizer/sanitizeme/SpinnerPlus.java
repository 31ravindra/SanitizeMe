package com.sanitizer.sanitizeme;


import android.content.Context;
import android.util.AttributeSet;


public class SpinnerPlus extends androidx.appcompat.widget.AppCompatSpinner {
    OnItemSelectedListener listener;

    public SpinnerPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);
        if (listener != null)
            listener.onItemSelected(null, null, position, 0);
    }

    public void setOnItemSelectedEvenIfUnchangedListener(
            OnItemSelectedListener listener) {
        this.listener = listener;
    }

}
