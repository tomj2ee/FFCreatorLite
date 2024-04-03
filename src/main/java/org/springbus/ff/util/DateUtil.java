package org.springbus.ff.util;

public class DateUtil {

    /**
     * Convert seconds to hh:mm:ss format
     * @param {number} sec - second
     * @return {string} hh:mm:ss result
     * @public
     */
   public static String secondsToHms(int sec) {
       int hours = sec / 3600;
       int minutes = (sec - hours * 3600) / 60;
       int seconds = sec - hours * 3600 - minutes * 60;

       if (hours < 10) {
           hours = '0' + hours;
       }

       if (minutes < 10) {
           minutes = '0' + minutes;
       }

       if (seconds < 10) {
           seconds = '0' + seconds;
       }

       return hours + ":" + minutes + ":" + seconds;
   }

    /**
     * Convert hh:mm:ss format time to seconds
     * @param {string} hms - hh:mm:ss format time
     * @return {number} seconds second
     * @public
     */
    public  static int hmsToSeconds(String hms) {
        String[] a = hms.split(":");
        int seconds = Integer.parseInt(a[0]) * 60 * 60 + Integer.parseInt(a[1]) * 60 + Integer.parseInt(a[2]);
        return seconds;
    }

    /**
     * Convert time to millisecond format
     * @param {number} time - second time
     * @return {string} millisecondt
     * @public
     */
   public static  int toMilliseconds(int time) {
       if (time == 0) return 0;
       return time < 100 ? time * 1000 : time;
   }

    /**
     * Convert time to second format
     * @param {number} time - millisecondt time
     * @return {string} second
     * @public
     */
    public  static int toSeconds(int time) {
        if (time == 0) return 0;
        return time > 100 ? time / 1000 : time;
    }
}
