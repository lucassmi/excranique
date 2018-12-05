package com.bonobocorp.joker.litrocent.Vue;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.bonobocorp.joker.litrocent.R;

public class ChampText extends android.support.v7.widget.AppCompatTextView{

    public String idveh;
    public Context context;

    public ChampText(Context context){
        super(context);
        TypedArray ta = context.getTheme().obtainStyledAttributes(R.styleable.ChampText);
        idveh = ta.getString(R.styleable.ChampText_idveh);
        try {
            idveh = ta.getString(R.styleable.ChampText_idveh);
        } finally {
            ta.recycle();
        }
    }

    public ChampText(Context context, AttributeSet attribute) {
        super(context, attribute);
        TypedArray ta = context.obtainStyledAttributes(attribute, R.styleable.ChampText,0 ,0);
        try {
            idveh = ta.getString(R.styleable.ChampText_idveh);
        } finally {
            ta.recycle();
        }
    }

    public String getIdveh() {
        return idveh;
    }

    public void setIdveh(String idveh) {
        this.idveh = idveh;
        invalidate();
        requestLayout();
    }

}
