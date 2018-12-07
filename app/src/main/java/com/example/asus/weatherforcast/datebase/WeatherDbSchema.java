package com.example.asus.weatherforcast.datebase;

public class WeatherDbSchema {
    public static  final class WeatherTable{
        public static final String NAME="weathers";

        public static  final class Cols{
            public static final String UUID="uuid";
            public static final String DATE="date";
            public static final String DAY="day";
            public static final String MAX_TEMP="max_temperature";
            public static final String MIN_TEMP="min_temperature";
            public static final String CONDITION_DAY="condition_day";
            public static final String CONDITION_NIGHT="condition_night";
            public static final String CONDITION_CODE_DAY="condition_code_day";
            public static final String CONDITION_CODE_NIGHT="condition_code_night";
            public static final String HUMIDITY="humidity";
            public static final String AIRPRESSURE="air_pressure";
            public static final String WINDSPEED="wind_speed";
            public static final String WINDDIRECTION="wind_direction";
        }
    }
}
