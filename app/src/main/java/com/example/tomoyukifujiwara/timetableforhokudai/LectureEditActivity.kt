package com.example.tomoyukifujiwara.timetableforhokudai

import android.annotation.SuppressLint
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_lecture_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class LectureEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lecture_edit)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = ""
        toolbar.setBackgroundResource(R.color.colorAccent)
        setSupportActionBar(toolbar)

        val intent = intent.getIntArrayExtra("LECTURE_INFO")
        val timetableid = 100 * intent[0]
        val id = intent[1]
        val dayTimeList: Map<Int, Int> = mapOf(R.id.mon1 to 11, R.id.mon2 to 12, R.id.mon3 to 13, R.id.mon4 to 14, R.id.mon5 to 15, R.id.mon6 to 16,
                                               R.id.tue1 to 21, R.id.tue2 to 22, R.id.tue3 to 23, R.id.tue4 to 24, R.id.tue5 to 25, R.id.tue6 to 26,
                                               R.id.wed1 to 31, R.id.wed2 to 32, R.id.wed3 to 33, R.id.wed4 to 34, R.id.wed5 to 35, R.id.wed6 to 36,
                                               R.id.thu1 to 41, R.id.thu2 to 42, R.id.thu3 to 43, R.id.thu4 to 44, R.id.thu5 to 45, R.id.thu6 to 46,
                                               R.id.fri1 to 51, R.id.fri2 to 52, R.id.fri3 to 53, R.id.fri4 to 54, R.id.fri5 to 55, R.id.fri6 to 55)
        val dayStringList: Map<Int, String> = mapOf(1 to "月曜日", 2 to "火曜日", 3 to "水曜日", 4 to "木曜日", 5 to "金曜日")
        val timeStringList: Map<Int, String> = mapOf(1 to "１限", 2 to "２限", 3 to "３限", 4 to "４限", 5 to "５限", 6 to "６限")

        var dayTime = dayTimeList.getOrElse(id, {11})
        var day = dayTime/10
        var time = dayTime - (dayTime/10)*10
        var selectedColor = 0

        realm = Realm.getDefaultInstance()
        var lecture = realm.where<Lecture>().equalTo("dayAndTime", dayTime).findFirst()
        titleEdit.setText(lecture?.title)
        teacherEdit.setText(lecture?.teacher)
        roomEdit.setText(lecture?.room)
        memoEdit.setText(lecture?.memo)
        val index = when (lecture?.color) {
            R.color.blue -> 1
            R.color.red -> 2
            R.color.yellow -> 3
            R.color.green -> 4
            else -> 0
        }
        colorSpinner.setSelection(index)

        var dayString = dayStringList.get(day)
        var timeString = timeStringList.get(time)
        var title = "$dayString  $timeString"
        toolbar.title = title

        daySpinner.setSelection(dayTime/10)
        daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, int: Long) {
                val spinner = parent as Spinner
                day = position
                dayTime = day*10+time
                dayString = dayStringList.getOrElse(day, {"月曜日"})
                title = "$dayString  $timeString"
                toolbar.title = title

                var lecture = realm.where<Lecture>().equalTo("dayAndTime", timetableid + dayTime).findFirst()
                if (lecture?.dayAndTime == null) {
                    realm.executeTransaction {
                        lecture = realm.createObject<Lecture>(dayTime)
                    }
                    delete.visibility = View.INVISIBLE
                } else {
                    if (lecture?.title == "" && lecture?.teacher == "" && lecture?.room == "" && lecture?.memo == "" && lecture?.color == 0) {
                        delete.visibility = View.INVISIBLE
                    } else {
                        delete.visibility = View.VISIBLE
                    }
                }
            }
        }

        timeSpinner.setSelection(dayTime - (dayTime/10)*10)
        timeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, int: Long) {
                val spinner = parent as Spinner
                time = position
                dayTime = day*10 + time
                timeString = timeStringList.getOrElse(time, {"１限"})
                title = "$dayString  $timeString"
                toolbar.title = title

                var lecture = realm.where<Lecture>().equalTo("dayAndTime", timetableid + dayTime).findFirst()

                if (lecture?.dayAndTime == null) {
                    realm.executeTransaction {
                        lecture = realm.createObject<Lecture>(dayTime)
                    }
                    delete.visibility = View.INVISIBLE
                } else {
                    if (lecture?.title == "" && lecture?.teacher == "" && lecture?.room == "" && lecture?.memo == "" && lecture?.color == 0) {
                        delete.visibility = View.INVISIBLE
                    } else {
                        delete.visibility = View.VISIBLE
                    }
                }
            }
        }


        colorSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, int: Long) {
                val spinner = parent as Spinner
                val selectedColorText = spinner.selectedItem as String
                selectedColor = when (selectedColorText) {
                    "青" -> R.color.blue
                    "赤" -> R.color.red
                    "黄" -> R.color.yellow
                    "緑" -> R.color.green
                    else -> 0
                }
                colorSpinner.setBackgroundResource(selectedColor)
            }
        }


        save.setOnClickListener {
            var lecture = realm.where<Lecture>().equalTo("dayAndTime", dayTime).findFirst()
            realm.executeTransaction {
                lecture?.title = titleEdit.text.toString()
                lecture?.teacher = teacherEdit.text.toString()
                lecture?.room = roomEdit.text.toString()
                lecture?.memo = memoEdit.text.toString()
                lecture?.color = selectedColor
            }
            alert("編集しました" ) {
                yesButton { finish() }
            }.show()
        }

        delete.setOnClickListener {
            var lecture = realm.where<Lecture>().equalTo("dayAndTime", dayTime).findFirst()
            realm.executeTransaction {
                lecture?.deleteFromRealm()
            }
            alert ( "削除しました" ) {
                yesButton { finish() }
            }.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.edit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item?.itemId) {
            R.id.close -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
