package com.example.tomoyukifujiwara.timetableforhokudai

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
//import io.realm.RealmList

//open class Timetable: RealmObject() {
//    @PrimaryKey
//    var id: Long = 0
//    var name: String = ""
//    var lectures: RealmList<Lecture>? = null
//    var option: Boolean = false
//}

open class Lecture: RealmObject() {
    @PrimaryKey
    var dayAndTime: String = ""
    var title: String = ""
    var room: String = ""
    var teacher: String = ""
    var memo: String = ""
    var color: Int = 0
//    var auto: Boolean = false
}