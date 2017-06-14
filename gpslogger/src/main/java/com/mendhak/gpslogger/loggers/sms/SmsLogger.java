package com.mendhak.gpslogger.loggers.sms;

import android.location.Location;
import android.telephony.SmsManager;

import com.mendhak.gpslogger.common.SerializableLocation;
import com.mendhak.gpslogger.loggers.FileLogger;

import java.util.ArrayList;
import java.util.List;

public class SmsLogger implements FileLogger {

    private final String name = "SMS";
    private List<String> userNums = new ArrayList<>();

    public SmsLogger(List<String> nums) {
        this.userNums = nums;
    }

    @Override
    public void write(Location loc) throws Exception {
        annotate("", loc);
    }

    @Override
    public void annotate(String description, Location loc) throws Exception {

        SmsManager smsManager = SmsManager.getDefault();
        for (String num : userNums) {
            smsManager.sendTextMessage(num, null, getGoogleLink(loc), null, null);
        }
    }

    public String getGoogleLink(Location loc) {
        SerializableLocation sLoc = new SerializableLocation(loc);
        StringBuilder smsText = new StringBuilder();
        smsText.append("https://maps.google.com/maps?q=").
                append(String.valueOf(sLoc.getLatitude())).
                append(",").
                append(String.valueOf(sLoc.getLongitude()));

        smsText.append(System.getProperty("line.separator","\\n"));

        if(smsText.length() < 130)
            smsText.append("alt=" + String.valueOf(sLoc.getAltitude()) + ";");

        if(smsText.length() < 130)
            smsText.append("spd=" + String.valueOf(sLoc.getSpeed()) + ";");

        if(smsText.length() < 130)
            smsText.append("acc=" + String.valueOf(sLoc.getAccuracy()) + ";");

//        logUrl = logUrl.replaceAll("(?i)%lat", String.valueOf(sLoc.getLatitude()));
//        logUrl = logUrl.replaceAll("(?i)%lon", String.valueOf(sLoc.getLongitude()));
//        logUrl = logUrl.replaceAll("(?i)%alt", String.valueOf(sLoc.getAltitude()));
//        logUrl = logUrl.replaceAll("(?i)%acc", String.valueOf(sLoc.getAccuracy()));
//        logUrl = logUrl.replaceAll("(?i)%dir", String.valueOf(sLoc.getBearing()));
//        logUrl = logUrl.replaceAll("(?i)%prov", String.valueOf(sLoc.getProvider()));
//        logUrl = logUrl.replaceAll("(?i)%spd", String.valueOf(sLoc.getSpeed()));
//        logUrl = logUrl.replaceAll("(?i)%timestamp", String.valueOf(sLoc.getTime() / 1000));
//        logUrl = logUrl.replaceAll("(?i)%time", String.valueOf(Strings.getIsoDateTime(new Date(sLoc.getTime()))));
        return smsText.toString();
    }

    @Override
    public String getName() {
        return name;
    }
}


