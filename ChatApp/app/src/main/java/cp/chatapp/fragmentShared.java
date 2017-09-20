package cp.chatapp;

import java.util.Calendar;

/**
 * Created by Chantal on 18.09.2017.
 */

public class fragmentShared {
    public String date(){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        String mi = "" + min;
        if (min < 10){ mi = "0"+mi;}
        String dateTime = day+"."+month+"."+year+", "+hour+":"+mi;
        return dateTime;
    }
}
