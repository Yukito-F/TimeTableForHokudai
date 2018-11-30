package com.example.tomoyukifujiwara.timetableforhokudai

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.time_table_main.*
import org.jetbrains.anko.startActivity
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var realm: Realm
    var mode = 1
    var timetableid = 0
    private var mTitle: CharSequence? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = "時間割"
        setSupportActionBar(toolbar)

        realm = Realm.getDefaultInstance()
        val arrayOfString: Array<String> = arrayOf("first","second","third","fourth","fifth","sixth")
        for (time in arrayOfString) realmSet(time)

        val arrayOfView: Array<View> = arrayOf(mon1,mon2,mon3,mon4,mon5,mon6,tue1,tue2,tue3,tue4,tue5,tue6,wed1,wed2,wed3,wed4,wed5,wed6,thu1,thu2,thu3,thu4,thu5,thu6,fri1,fri2,fri3,fri4,fri5,fri6)
        for (view in arrayOfView) clickCell(view)

        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

//        val timetables = realm.where<Timetable>().findAll()
//        left_drawer.adapter = TimetableAdapter(timetables)
//
//        left_drawer.setOnItemClickListener { parent, view, position, id ->
//            val timetable = parent.getItemAtPosition(position) as Timetable
//            timetableid = timetable.id
//            onResume()
//            drawer_layout.closeDrawer(GravityCompat.START)
//        }

        val items = Array(20, { i -> "Title-$i" })
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
        timetableList.adapter = adapter
        timetableList.setOnItemClickListener { parent, view, position, id ->
            timetableid = position
            onResume()
            drawer_layout.closeDrawer(GravityCompat.START)
        }

        navSetting.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_settings_black_24dp, 0, 0, 0);
        navEdit.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_edit_black_24dp, 0, 0, 0);
    }

    override fun setTitle(title: CharSequence) {
        mTitle = title
        actionBar!!.setTitle(mTitle)
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
                view.setOnClickListener { startActivity<LectureEditActivity>("LECTURE_INFO" to intArrayOf(timetableid, view.id)) }
                return false
            }
        })
    }

    override fun onResume() {
        super.onResume()
        test.text = timetableid.toString()
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
        val dayTimeList: Map<Int, Int> = mapOf(R.id.mon1 to 11, R.id.mon2 to 12, R.id.mon3 to 13, R.id.mon4 to 14, R.id.mon5 to 15, R.id.mon6 to 16,
                                               R.id.tue1 to 21, R.id.tue2 to 22, R.id.tue3 to 23, R.id.tue4 to 24, R.id.tue5 to 25, R.id.tue6 to 26,
                                               R.id.wed1 to 31, R.id.wed2 to 32, R.id.wed3 to 33, R.id.wed4 to 34, R.id.wed5 to 35, R.id.wed6 to 36,
                                               R.id.thu1 to 41, R.id.thu2 to 42, R.id.thu3 to 43, R.id.thu4 to 44, R.id.thu5 to 45, R.id.thu6 to 46,
                                               R.id.fri1 to 51, R.id.fri2 to 52, R.id.fri3 to 53, R.id.fri4 to 54, R.id.fri5 to 55, R.id.fri6 to 55)
        val dayTime = dayTimeList.getOrElse(cell.id, {11})
        val lecture = realm.where<Lecture>().equalTo("dayAndTime", timetableid*100 + dayTime).findFirst()
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
                } else {
                    toolbar.subtitle = ""
                    toolbar.setBackgroundResource(R.color.colorPrimary)
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
//            R.id.set -> {
//            }
//            R.id.information -> {
//            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

//    private fun addMenuItemInNavMenuDrawer() {
//        val navView = findViewById<View>(R.id.nav_view) as NavigationView
//
//        val menu = navView.menu
//        val submenu = menu.addSubMenu("Timetable List")
//
//        submenu.add("Super Item1")
//        submenu.add("Super Item2")
//        submenu.add("Super Item3")

//        navView.invalidate()
//    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
