package com.huidisen.ep750.utils;

import android.util.Log;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by LoveLife on 2015/2/14.
 */
public class NMEAParserUtils {
    private static final String TAG = "RESULT";
    private static final BigDecimal CONVERTER = new BigDecimal("1.85");

    public static Map<String, String> parser(String data) {
        if (data.indexOf("GPRMC") < 0 || data.indexOf("GPGGA") < 0) {
            Log.i(TAG, "null");
            return null;
        }

        boolean hasGRPRMCData = false;
        boolean hasGPGGAData = false;

        Map<String, String> result = new HashMap<String, String>();
        String[] items = data.split(",");
        BigDecimal speed;

        for (int i = 0; i < items.length; i++) {
            if (items[i].contains("GPRMC")) {

                String dayData = items[i + 9];
                String hourData = items[i + 1];

                result.put("timestamp", dayData + hourData);

                speed = new BigDecimal(items[i + 7]).multiply(CONVERTER);
                result.put("speed", speed.toPlainString());

//                result.put("speed", items[i + 7]);

                if (hasGPGGAData) {
                    break;
                }
                hasGRPRMCData = true;
            } else if (items[i].contains("GPGGA")) {
                result.put("lat", items[i + 2]);
                result.put("lon", items[i + 4]);

                result.put("gpsStatus", items[i + 6]);
                result.put("starCount", items[i + 7]);

                result.put("accuracy", items[i + 8]);

                result.put("alt", items[i + 9]);

                if (hasGRPRMCData) {
                    break;
                }
                hasGPGGAData = true;
            }

        }

        return result;
    }

}
