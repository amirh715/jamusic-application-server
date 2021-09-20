/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jamapplicationserver.core.domain;

import com.jamapplicationserver.core.logic.*;
import java.time.*;
import java.time.format.DateTimeParseException;
import com.github.eloyzone.jalalicalendar.*;
import com.jamapplicationserver.utils.PersianDigitsMapper;
import java.lang.Math;
import java.util.TimeZone;

/**
 *
 * @author amirhossein
 * toJalali() - Converts LocalDateTime to a string representation of Jalali date and time.
 */
public class DateTime extends ValueObject<LocalDateTime> {
    
    private final LocalDateTime value;
    
    private DateTime(LocalDateTime value) {
        this.value = value;
    }
    
    @Override
    public LocalDateTime getValue() {
        return this.value;
    }
    
    public static Result<DateTime> create(LocalDateTime value) {
        return Result.ok(new DateTime(value));
    }
    
    public static DateTime createWithoutValidation(LocalDateTime value) {
        return new DateTime(value);
    }
    
    public static Result<DateTime> create(String value) {
        if(value == null) return Result.fail(new ValidationError("Datetime is required"));
        try {
            final DateTime instance =
                    DateTime.create(LocalDateTime.parse(value)).getValue();
            return Result.ok(instance);
        } catch(DateTimeParseException e) {
            return Result.fail(new ValidationError("Invalid datetime"));
        }
        
    }
    
    public static DateTime createNow() {
        return new DateTime(LocalDateTime.now());
    }
    
    public String toJalali() {
        
        final DateConverter converter = new DateConverter();
        final JalaliDate jalaliDate = converter.gregorianToJalali(
                value.getYear(),
                value.getMonth(),
                value.getDayOfMonth()
        );
        final JalaliDateFormatter dateFormatter =
                new JalaliDateFormatter(
                        "yyyy/mm/dd",
                        JalaliDateFormatter.FORMAT_IN_PERSIAN
                );
        final String date = jalaliDate.format(dateFormatter);
        
        final int hour = value.getHour();
        final int min = value.getMinute();
        final int sec = value.getSecond();
        
        final String persianHour = PersianDigitsMapper.toPersian(hour);
        final String persianMin = PersianDigitsMapper.toPersian(min);
        final String persianSec = PersianDigitsMapper.toPersian(sec);
        
        final String time =
                (hour > 9 ? persianHour : "۰" + persianHour) + ":" +
                (min > 9 ? persianMin : "۰" + persianMin) + ":" +
                (sec > 9 ? persianSec : "۰" + persianSec);
        
        return date + " - " + time;
        
    }
    
    public String toJalali(TimeZone timezone) {

        final ZonedDateTime value = this.getValue().atZone(timezone.toZoneId());
        
        final DateConverter converter = new DateConverter();
        final JalaliDate jalaliDate = converter.gregorianToJalali(
                value.getYear(),
                value.getMinute(),
                value.getDayOfMonth()
        );
        
        final JalaliDateFormatter dateFormatter =
                new JalaliDateFormatter(
                        "yyyy/mm/dd",
                        JalaliDateFormatter.FORMAT_IN_PERSIAN
                );
        final String date = jalaliDate.format(dateFormatter);
        
        final int hour = value.getHour();
        final int min = value.getMinute();
        final int sec = value.getSecond();
        
        final String persianHour = PersianDigitsMapper.toPersian(hour);
        final String persianMin = PersianDigitsMapper.toPersian(min);
        final String persianSec = PersianDigitsMapper.toPersian(sec);
        
        final String time =
                (hour > 9 ? persianHour : "۰" + persianHour) + ":" +
                (min > 9 ? persianMin : "۰" + persianMin) + ":" +
                (sec > 9 ? persianSec : "۰" + persianSec);
        
        return date + " - " + time;
    }
    
    public String diffFrom(DateTime dateTime) {
        
        final String yearsString = "سال";
        final String monthsString = "ماه";
        final String daysString = "روز";
        final String hoursString = "ساعت";
        final String minsString = "دقیقه";
        
        final Duration duration = Duration.between(value, dateTime.value);

        final long seconds = duration.toSeconds();
        final long absSeconds = Math.abs(seconds);
        
        String yearsPart;
        String monthsPart;
        String daysPart;
        String hoursPart;
        String minsPart;
        
        String phrase;
        
        if(absSeconds < 10)
            phrase = "لحظاتی";
        else if(absSeconds < 60)
            phrase = "کمتر از یک دقیقه";
        else if(absSeconds < 3600) {
            minsPart = PersianDigitsMapper.toPersian((int) absSeconds / 60);
            phrase = minsPart + " " + minsString;
        } else if(absSeconds < 86400) {
            hoursPart = PersianDigitsMapper.toPersian((int) absSeconds / 3600);
            minsPart = PersianDigitsMapper.toPersian((int) absSeconds / 60 % 60);
            phrase = hoursPart + " " + hoursString + " " + minsPart + " " + minsString;
        } else if(absSeconds < 2592000) {
            daysPart = PersianDigitsMapper.toPersian((int) absSeconds / 86400);
            hoursPart = PersianDigitsMapper.toPersian((int) absSeconds / 3600 % 24);
            phrase = daysPart + " " + daysString + " " + hoursPart + " " + hoursString;
        } else if(absSeconds < 31104000) {
            monthsPart = PersianDigitsMapper.toPersian((int) absSeconds / 2592000);
            phrase = monthsPart + " " + monthsString;
        } else {
            yearsPart = "بیش از یک سال";
            phrase = yearsPart + " " + yearsString;
        }
        
        phrase = phrase + " " + (seconds > 0 ? "پیش" : "دیگر");

        return phrase;
    }
    
    public String diffFromNow() {
        return diffFrom(DateTime.createNow());
    }
    
}
