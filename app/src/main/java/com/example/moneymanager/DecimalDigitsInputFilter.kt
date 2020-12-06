package com.example.moneymanager

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Input filter that limits the number of decimal digits that are allowed to be
 * entered.
 */
class DecimalDigitsInputFilter(private val decimalDigits:Int) : InputFilter {

    override fun filter(source:CharSequence, start:Int, end:Int, dest:Spanned, dstart:Int, dend:Int):CharSequence? {
        var dotPos:Int = -1;
        var len:Int = dest.length;
        for (i in 0..len) {
        var c:Char = dest.get(i)
        if (c == '.' || c == ',') {
            dotPos = i;
            break;
        }
    }
        if (dotPos >= 0) {

            // protects against many dots
            if (source.equals(".") || source.equals(","))
            {
                return "";
            }
            // if the text is entered before the dot
            if (dend <= dotPos) {
                return null;
            }
            if (len - dotPos > decimalDigits) {
                return "";
            }
        }

        return null;
    }
}