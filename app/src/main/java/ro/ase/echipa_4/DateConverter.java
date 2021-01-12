package ro.ase.echipa_4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.TypeConverter;

import android.os.Bundle;

import java.util.Date;


class DateConvertor {
    @TypeConverter
    public static Date fromTimeStamp(Long timeStamp) {
        return timeStamp != null
                ? new Date(timeStamp)
                : null;
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date != null
                ? date.getTime() : null;
    }
}
