package net.simplyadvanced.funfacts;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Danial on 12/5/2014.
 */
public class ColorWheel {

    public String mColors[] = {
            "#39add1", // light blue
            "#3079ab", // dark blue
            "#c25975", // mauve
            "#e15258", // red
            "#f9845b", // orange
            "#838cc7", // lavender
            "#7d669e", // purple
            "#53bbb4", // aqua
            "#51b46d", // green
            "#e0ab18", // mustard
            "#637a91", // dark gray
            "#f092b0", // pink
            "#b7c0c7"  // light gray
    };

    private Random mRandom;

    public ColorWheel() {
        mRandom = new Random();
    }

    /**  */
    public int getColor() {
        return Color.parseColor(mColors[mRandom.nextInt(mColors.length)]);
    }

}
