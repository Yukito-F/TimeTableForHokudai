package com.example.tomoyukifujiwara.timetableforhokudai

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.CheckBox
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val mode = intent.getIntExtra("MODE", 0)

        val toolbar = findViewById<View>(R.id.toolbar2) as Toolbar
        toolbar.title = "設定"
        if (mode == -1) {
            toolbar.setBackgroundResource(R.color.colorAccent)
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        realm = Realm.getDefaultInstance()

        checkBox(first.id, "first")
        checkBox(second.id, "second")
        checkBox(third.id, "third")
        checkBox(fourth.id, "fourth")
        checkBox(fifth.id, "fifth")
        checkBox(sixth.id, "sixth")
    }

    private fun checkBox(id: Int, time: String) {
        val checkBox: CheckBox = findViewById(id)
        val setting = realm.where<Setting>().equalTo("name", time).findFirst()
        checkBox.isChecked = setting!!.value
        checkBox.setOnClickListener(View.OnClickListener
        {
            realm.executeTransaction {
                setting?.value = checkBox.isChecked()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
