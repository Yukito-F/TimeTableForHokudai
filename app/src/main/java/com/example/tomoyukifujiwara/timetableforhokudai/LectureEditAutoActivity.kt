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

class LectureEditAutoActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lecture_edit)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = ""
        toolbar.setBackgroundResource(R.color.colorAccent)
//        window.setStatusBarColor(R.color.colorAccentDark)
        setSupportActionBar(toolbar)

        val id = intent.getIntExtra("LECTURE_ID", 0)
        val dayTime: Int = when (id) {
            R.id.mon1 -> 1
            R.id.mon2 -> 2
            R.id.mon3 -> 3
            R.id.mon4 -> 4
            R.id.mon5 -> 5
            R.id.mon6 -> 6
            R.id.tue1 -> 7
            R.id.tue2 -> 8
            R.id.tue3 -> 9
            R.id.tue4 -> 10
            R.id.tue5 -> 11
            R.id.tue6 -> 12
            R.id.wed1 -> 13
            R.id.wed2 -> 14
            R.id.wed3 -> 15
            R.id.wed4 -> 16
            R.id.wed5 -> 17
            R.id.wed6 -> 18
            R.id.thu1 -> 19
            R.id.thu2 -> 20
            R.id.thu3 -> 21
            R.id.thu4 -> 22
            R.id.thu5 -> 23
            R.id.thu6 -> 24
            R.id.fri1 -> 25
            R.id.fri2 -> 26
            R.id.fri3 -> 27
            R.id.fri4 -> 28
            R.id.fri5 -> 29
            R.id.fri6 -> 30
            else -> 0
        }

        var check: Boolean = true
        var dayAndTime: String = ""

        realm = Realm.getDefaultInstance()
        daySpinner.setSelection(dayTime)
        daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, int: Long) {
                val spinner = parent as Spinner
                dayAndTime = spinner.selectedItem as String
                toolbar.title = dayAndTime

                var lecture = realm.where<Lecture>().equalTo("dayAndTime", dayAndTime).findFirst()

                if (check) {
                    titleEdit.setText(lecture?.title)
                    teacherEdit.setText(lecture?.teacher)
                    roomEdit.setText(lecture?.room)
                    memoEdit.setText(lecture?.memo)
                    check = false
                }

                if (lecture?.dayAndTime == null) {
                    realm.executeTransaction {
                        lecture = realm.createObject<Lecture>(dayAndTime)
                    }
                    delete.visibility = View.INVISIBLE
                } else {
                    if (lecture?.title == "" && lecture?.teacher == "" && lecture?.room == "" && lecture?.memo == "") {
                        delete.visibility = View.INVISIBLE
                    } else {
                        delete.visibility = View.VISIBLE
                    }
                }
            }
        }

        save.setOnClickListener {
            var lecture = realm.where<Lecture>().equalTo("dayAndTime", dayAndTime).findFirst()
            realm.executeTransaction {
                lecture?.title = titleEdit.text.toString()
                lecture?.teacher = teacherEdit.text.toString()
                lecture?.room = roomEdit.text.toString()
                lecture?.memo = memoEdit.text.toString()
            }
            alert("編集しました" ) {
                yesButton { finish() }
            }.show()
        }

        delete.setOnClickListener {
            var lecture = realm.where<Lecture>().equalTo("dayAndTime", dayAndTime).findFirst()
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
