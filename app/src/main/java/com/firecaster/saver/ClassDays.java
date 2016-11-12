package com.firecaster.saver;

public class ClassDays {

    String name;
    boolean morning = false;
    boolean evening = false;
    boolean night = false;

    ClassDays() {

    }


    ClassDays(String name, boolean morning, boolean evening, boolean night) {
        this.name = name;
        this.morning = morning;
        this.evening = evening;
        this.night = night;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }

    public boolean isEvening() {
        return evening;
    }

    public void setEvening(boolean evening) {
        this.evening = evening;
    }

    public boolean isNight() {
        return night;
    }

    public void setNight(boolean night) {
        this.night = night;
    }


    @Override
    public String toString() {
        return name + "-" + morning + "-" + evening + "-" + night;
    }
}



