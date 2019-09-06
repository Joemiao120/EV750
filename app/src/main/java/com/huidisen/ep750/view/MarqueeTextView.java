package com.huidisen.ep750.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * http://www.linuxidc.com/Linux/2014-05/102299.htm
 * Created by lovexiaov on 16/6/10.
 */
public class MarqueeTextView extends TextView {

    public MarqueeTextView(Context context) {
        super(context);
    }

    public MarqueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeTextView(
            Context context, AttributeSet attrs, int defStyle
    ) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

}

