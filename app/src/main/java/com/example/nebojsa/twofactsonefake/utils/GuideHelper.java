package com.example.nebojsa.twofactsonefake.utils;

import android.app.Activity;
import android.view.View;

import com.example.nebojsa.twofactsonefake.R;

import uk.co.deanwild.materialshowcaseview.IShowcaseListener;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class GuideHelper {
    public static void displayGuide(Activity activity, View view, String message, IShowcaseListener listener) {
        new MaterialShowcaseView.Builder(activity)
                .setTarget(view)
                .setListener(listener)
                .setDismissText("GOT IT")
                .setContentText(message)
                .setDismissOnTouch(true)
//                .setMaskColour(R.color.guides_color)
                .setDelay(Constants.GUIDE_DISPLAY_DELAY)
                .show();
    }
}
