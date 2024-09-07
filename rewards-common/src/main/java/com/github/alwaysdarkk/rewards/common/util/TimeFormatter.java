package com.github.alwaysdarkk.rewards.common.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class TimeFormatter {

    public String format(long value) {
        if (value <= 0) {
            return "0s";
        }

        final long days = TimeUnit.MILLISECONDS.toDays(value);
        final long hours = TimeUnit.MILLISECONDS.toHours(value) - (days * 24);
        final long minutes = TimeUnit.MILLISECONDS.toMinutes(value) - (TimeUnit.MILLISECONDS.toHours(value) * 60);
        final long second = TimeUnit.MILLISECONDS.toSeconds(value) - (TimeUnit.MILLISECONDS.toMinutes(value) * 60);

        final long[] times = {days, hours, minutes, second};

        final String[] names = {"d", "h", "m", "s"};
        final List<String> values = new ArrayList<>();

        for (int index = 0; index < times.length; index++) {
            final long time = times[index];
            if (time > 0) {
                final String name = text(times[index], names[index]);
                values.add(name);
            }
        }

        if (values.size() == 1) {
            return values.get(0);
        }

        return String.join(" ", values);
    }

    public String text(long quantity, String message) {
        return quantity + message;
    }
}