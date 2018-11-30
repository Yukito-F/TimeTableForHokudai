package com.example.tomoyukifujiwara.timetableforhokudai

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Lecture: RealmObject() {
    @PrimaryKey
    var dayAndTime: Int = 0
    var title: String = ""
    var room: String = ""
    var teacher: String = ""
    var memo: String = ""
    var color: Int = 0
//    var auto: Boolean = false
}
