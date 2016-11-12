package com.firecaster.saver;


import android.app.Notification;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.UnderlineSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Collection;
import java.util.HashSet;

public class EventDecorator implements DayViewDecorator {

    private final int color;
    private final HashSet<CalendarDay> dates;


    public EventDecorator(int color, Collection<CalendarDay> dates) {
        this.color = color;
        this.dates = new HashSet<>(dates);
    }


    //called for every date in the calendar to determine if the decorator should be applied to that date.
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    //is called only one time to gather the customizations used for this decorator.
    // This is so we can cache the decorations and efficiently apply them to many days.
    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(10, color));
        view.addSpan(new ForegroundColorSpan(color));
        //view.addSpan(new BackgroundColorSpan(color));

    }
}