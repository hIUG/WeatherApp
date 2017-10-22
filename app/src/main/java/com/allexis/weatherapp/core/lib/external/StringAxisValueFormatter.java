package com.allexis.weatherapp.core.lib.external;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.Map;

/**
 * Created by allexis on 10/19/17.
 */

public class StringAxisValueFormatter implements IAxisValueFormatter {

    private Map<Integer, String> values;

    public StringAxisValueFormatter(Map<Integer, String> values) {
        this.values = values;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return values.get((int) value);
    }
}
