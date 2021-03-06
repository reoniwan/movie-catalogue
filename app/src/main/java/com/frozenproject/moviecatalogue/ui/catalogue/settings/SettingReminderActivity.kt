package com.frozenproject.moviecatalogue.ui.catalogue.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.frozenproject.moviecatalogue.R
import kotlinx.android.synthetic.main.activity_setting_reminder.*

class SettingReminderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_reminder)

        supportFragmentManager.beginTransaction()
            .add(R.id.setting_reminder, SettingReminderFragment())
            .commit()

        setSupportActionBar(toolbar_settings)
        val actionBar = supportActionBar
        actionBar?.title = getString(R.string.settings)

        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
