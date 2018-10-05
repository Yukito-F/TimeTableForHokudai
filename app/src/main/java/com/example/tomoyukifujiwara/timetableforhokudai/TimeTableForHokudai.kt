package com.example.tomoyukifujiwara.timetableforhokudai

import android.app.Application
import io.realm.Realm

class TimeTableForHokudai: Application() {
    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
    }
}