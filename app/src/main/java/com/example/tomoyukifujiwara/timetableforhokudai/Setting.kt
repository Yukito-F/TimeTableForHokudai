package com.example.tomoyukifujiwara.timetableforhokudai

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Setting: RealmObject() {
    @PrimaryKey
    var name: String = ""
    var value: Boolean = true
}