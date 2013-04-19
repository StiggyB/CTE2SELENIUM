package de.haw_hamburg.ti.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeAssist {

    private static Long stamp1 = null;
    private static Long stamp2 = null;

    private TimeAssist() {
    };

    /**
     * Get the actual Date.
     * 
     * @return String representation of the actual Date
     */
    public static String getDate() {
        //
        // Create a DateFormatter object for displaying date information.
        //
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

        //
        // Get date and time information in milliseconds
        //
        long now = System.currentTimeMillis();

        //
        // Create a calendar object that will convert the date and time value
        // in milliseconds to date. We use the setTimeInMillis() method of the
        // Calendar object.
        //

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(now);

        System.out.println(formatter.format(calendar.getTime()));
        return formatter.format(calendar.getTime());
    }

    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * Time passed between stamp1 and stamp2 in Seconds
     * 
     * @param stamp1
     * @param stamp2
     * @return
     */
    public static double getTimePassed(double stamp1, double stamp2) {
        return (stamp2 - stamp1) / 1000;
    }

    public static void setInternalStampOne() {
        TimeAssist.stamp1 = getTimeStamp();
    }

    public static void setInternalStampTwo() {
        TimeAssist.stamp2 = getTimeStamp();
    }

    /**
     * Set two stamps over the Methods {@link #setInternalStampOne(long)
     * setInternalStampOne} and {@link #setInternalStampTwo(long)
     * setInternalStampTwo}. Afterwards you can call this method to get the dime
     * difference in seconds.
     * 
     * @return TimePassed, null if one stamp has not been set
     */
    public static Double getInternalTimePassed() {
        System.out.println("stamp1:"+stamp1+" | stamp2:"+stamp2);
        System.out.println("timepassed:"+ (stamp2-stamp1));
        return (stamp1 == null) || (stamp2 == null) ? null : getTimePassed(
                stamp1, stamp2);
    }

}
