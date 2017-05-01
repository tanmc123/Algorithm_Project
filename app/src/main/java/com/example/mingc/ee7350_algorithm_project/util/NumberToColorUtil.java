package com.example.mingc.ee7350_algorithm_project.util;

import android.graphics.Color;

import org.jetbrains.annotations.Contract;

/**
 * Created by Mingc on 4/19/2017.
 */

public class NumberToColorUtil {

    /*
    * Used to convert the color flag in point to real color value
    * Suppose the max number of possible color is 50
    * The color is range from 0xff000000 to 0xffffffff
    * First two digit represent for transparency
    * The other six digits are R, G, B value respectively
    * This function find a way to range the color more separately
    * */
    @Contract(pure = true)
    public static int numberToColor(int number){
        if(number == 0){
            return Color.BLACK;
        }
        if(number == 1){
            return 0xFFFFFFFF; // White
        }
        int base = 0xFF000000;
        switch (number % 3){
            case 2:
                base = 0xFFFF0000; // Red
                break;
            case 0:
                base = 0xFF00FF00; // Green
                break;
            case 1:
                base = 0xFF0000FF; // Blue
                break;
        }
        int color = base + (number / 4) * (0x00000066);
        return color;
    }
}
