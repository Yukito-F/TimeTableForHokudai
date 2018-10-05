package com.example.tomoyukifujiwara.timetableforhokudai

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.time_table_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var realm: Realm
    var mode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = "時間割"
        setSupportActionBar(toolbar)

        realm = Realm.getDefaultInstance()
        realmSet("first")
        realmSet("second")
        realmSet("third")
        realmSet("fourth")
        realmSet("fifth")
        realmSet("sixth")

        clickCell(mon1)
        clickCell(mon2)
        clickCell(mon3)
        clickCell(mon4)
        clickCell(mon5)
        clickCell(mon6)
        clickCell(tue1)
        clickCell(tue2)
        clickCell(tue3)
        clickCell(tue4)
        clickCell(tue5)
        clickCell(tue6)
        clickCell(wed1)
        clickCell(wed2)
        clickCell(wed3)
        clickCell(wed4)
        clickCell(wed5)
        clickCell(wed6)
        clickCell(thu1)
        clickCell(thu2)
        clickCell(thu3)
        clickCell(thu4)
        clickCell(thu5)
        clickCell(thu6)
        clickCell(fri1)
        clickCell(fri2)
        clickCell(fri3)
        clickCell(fri4)
        clickCell(fri5)
        clickCell(fri6)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    private fun realmSet(time: String) {
        val test = realm.where<Setting>().equalTo("name", time).findFirst()
        if (test?.name == null) {
            realm.executeTransaction {
                realm.createObject<Setting>(time)
            }
        }
    }

    private fun clickCell(view: View?) {
        view?.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (mode == 1) {
                    return true
                }
                view.setOnClickListener { startActivity<LectureEditActivity>("LECTURE_ID" to view.id) }
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        if (checker("first")) {
                first.visibility = View.VISIBLE
                setCell(mon1, titleMon1, roomMon1)
                setCell(tue1, titleTue1, roomTue1)
                setCell(wed1, titleWed1, roomWed1)
                setCell(thu1, titleThu1, roomThu1)
                setCell(fri1, titleFri1, roomFri1)
            } else {
                first.visibility = View.GONE
            }

        if (checker("second")) {
                second.visibility = View.VISIBLE
                setCell(mon2, titleMon2, roomMon2)
                setCell(tue2, titleTue2, roomTue2)
                setCell(wed2, titleWed2, roomWed2)
                setCell(thu2, titleThu2, roomThu2)
                setCell(fri2, titleFri2, roomFri2)
            } else {
                second.visibility = View.GONE
            }

        if (checker("third")) {
                third.visibility = View.VISIBLE
                setCell(mon3, titleMon3, roomMon3)
                setCell(tue3, titleTue3, roomTue3)
                setCell(wed3, titleWed3, roomWed3)
                setCell(thu3, titleThu3, roomThu3)
                setCell(fri3, titleFri3, roomFri3)
            } else {
                third.visibility = View.GONE
        }

        if (checker("fourth")) {
                fourth.visibility = View.VISIBLE
                setCell(mon4, titleMon4, roomMon4)
                setCell(tue4, titleTue4, roomTue4)
                setCell(wed4, titleWed4, roomWed4)
                setCell(thu4, titleThu4, roomThu4)
                setCell(fri4, titleFri4, roomFri4)
            } else {
                fourth.visibility = View.GONE
            }
        if (checker("fifth")) {
                fifth.visibility = View.VISIBLE
                setCell(mon5, titleMon5, roomMon5)
                setCell(tue5, titleTue5, roomTue5)
                setCell(wed5, titleWed5, roomWed5)
                setCell(thu5, titleThu5, roomThu5)
                setCell(fri5, titleFri5, roomFri5)
            } else {
                fifth.visibility = View.GONE
            }
        if (checker("sixth")) {
                sixth.visibility = View.VISIBLE
                setCell(mon6, titleMon6, roomMon6)
                setCell(tue6, titleTue6, roomTue6)
                setCell(wed6, titleWed6, roomWed6)
                setCell(thu6, titleThu6, roomThu6)
                setCell(fri6, titleFri6, roomFri6)
            } else {
                sixth.visibility = View.GONE
            }
    }

    private fun checker(time: String): Boolean {
        val setting = realm.where<Setting>().equalTo("name", time).findFirst()
        return setting!!.value
    }

    private fun setCell(cell: LinearLayout, title: TextView, room: TextView) {
        val dayAndTime = when (cell.id) {
            R.id.mon1 -> "月曜日　１限"
            R.id.mon2 -> "月曜日　２限"
            R.id.mon3 -> "月曜日　３限"
            R.id.mon4 -> "月曜日　４限"
            R.id.mon5 -> "月曜日　５限"
            R.id.mon6 -> "月曜日　６限"
            R.id.tue1 -> "火曜日　１限"
            R.id.tue2 -> "火曜日　２限"
            R.id.tue3 -> "火曜日　３限"
            R.id.tue4 -> "火曜日　４限"
            R.id.tue5 -> "火曜日　５限"
            R.id.tue6 -> "火曜日　６限"
            R.id.wed1 -> "水曜日　１限"
            R.id.wed2 -> "水曜日　２限"
            R.id.wed3 -> "水曜日　３限"
            R.id.wed4 -> "水曜日　４限"
            R.id.wed5 -> "水曜日　５限"
            R.id.wed6 -> "水曜日　６限"
            R.id.thu1 -> "木曜日　１限"
            R.id.thu2 -> "木曜日　２限"
            R.id.thu3 -> "木曜日　３限"
            R.id.thu4 -> "木曜日　４限"
            R.id.thu5 -> "木曜日　５限"
            R.id.thu6 -> "木曜日　６限"
            R.id.fri1 -> "金曜日　１限"
            R.id.fri2 -> "金曜日　２限"
            R.id.fri3 -> "金曜日　３限"
            R.id.fri4 -> "金曜日　４限"
            R.id.fri5 -> "金曜日　５限"
            R.id.fri6 -> "金曜日　６限"
            else -> "曜日　　時限"
        }
        val lecture = realm.where<Lecture>().equalTo("dayAndTime", dayAndTime).findFirst()
        title.text = lecture?.title
        room.text = lecture?.room
        if (lecture?.dayAndTime != null) {
            title.setBackgroundResource(lecture!!.color)
            room.setBackgroundResource(lecture!!.color)
        } else {
            title.setBackgroundResource(0)
            room.setBackgroundResource(0)
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.edit -> {
                mode *= -1
                if (mode == -1) {
                    toolbar.subtitle = "編集中"
                    toolbar.setBackgroundResource(R.color.colorAccent)
//                    window.setStatusBarColor(R.color.colorAccentDark)
                } else {
                    toolbar.subtitle = ""
                    toolbar.setBackgroundResource(R.color.colorPrimary)
//                    window.setStatusBarColor(R.color.colorPrimaryDark)
                }
                return true
            }
            R.id.setting -> {
                startActivity<SettingsActivity>("MODE" to mode)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
