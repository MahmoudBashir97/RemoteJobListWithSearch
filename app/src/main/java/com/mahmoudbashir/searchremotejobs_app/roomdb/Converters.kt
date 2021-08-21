package com.mahmoudbashir.searchremotejobs_app.roomdb

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    public fun fromWeatherListToString(tags:List<String>):String?{
        return Gson().toJson(tags)
    }

    @TypeConverter
    public fun fromStringWeatherList(sttags:String):List<String>{
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(sttags,listType)
    }
}