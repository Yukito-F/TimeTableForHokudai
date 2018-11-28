package com.example.tomoyukifujiwara.timetableforhokudai

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Timetable: RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var title: String = ""
}