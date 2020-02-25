package com.frozenproject.moviecatalogue.ui.catalogue.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.reminder.DailyReleaseReceiver
import com.frozenproject.moviecatalogue.reminder.DailyReminderReceiver

class SettingReminderFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var daily: String
    private lateinit var release: String
    private lateinit var dailyPreference: SwitchPreferenceCompat
    private lateinit var releasePreference: SwitchPreferenceCompat
    private val dailyReminderReceiver = DailyReminderReceiver()
    private val dailyReleaseReceiver = DailyReleaseReceiver()

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        when (key) {
            daily -> {
                val state = sharedPreferences.getBoolean(daily, false)
                if (state) {
                    dailyReminderReceiver.setDailyReminder(activity!!)
                    Toast.makeText(
                        activity,
                        getString(R.string.reminder_actived),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    dailyReminderReceiver.cancelDailyReminder(activity!!)
                    Toast.makeText(
                        activity,
                        getString(R.string.reminder_deactived),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            release -> {
                val state = sharedPreferences.getBoolean(release, false)
                if (state) {
                    dailyReleaseReceiver.setDailyRelease(activity!!)
                    Toast.makeText(
                        activity,
                        getString(R.string.reminder_actived),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    dailyReleaseReceiver.cancelDailyRelease(activity!!)
                    Toast.makeText(
                        activity,
                        getString(R.string.reminder_deactived),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
//        dailyPreference.isChecked = sharedPreferences.getBoolean(daily,false)
//
//        releasePreference.isChecked = sharedPreferences.getBoolean(release, false)
//
//        if (key == daily){
//            val isActive = sharedPreferences.getBoolean(daily,false)
//            dailyPreference.isChecked = isActive
//
//            if (isActive){
//                dailyReminderReceiver.setDailyReminder(Objects.requireNonNull(activity!!))
//                Toast.makeText(activity, getString(R.string.reminder_actived), Toast.LENGTH_SHORT).show()
//            }else{
//                dailyReminderReceiver.cancelDailyReminder(Objects.requireNonNull(activity!!))
//                Toast.makeText(activity, getString(R.string.reminder_deactived),Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        if (key == release){
//            val isActiveRelease = sharedPreferences.getBoolean(release, false)
//            releasePreference.isChecked = isActiveRelease
//
//            if (isActiveRelease){
//                dailyReleaseReceiver.setDailyRelease(Objects.requireNonNull(activity!!))
//                Toast.makeText(activity, getString(R.string.reminder_actived), Toast.LENGTH_SHORT).show()
//            }else{
//                dailyReleaseReceiver.cancelDailyRelease(Objects.requireNonNull(activity!!))
//                Toast.makeText(activity, getString(R.string.reminder_deactived),Toast.LENGTH_SHORT).show()
//            }
//        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference)
        initData()
//        setSettings()
    }

    private fun initData() {
        daily = resources.getString(R.string.daily)
        release = resources.getString(R.string.key_release)

        dailyPreference = findPreference<SwitchPreferenceCompat>(daily) as SwitchPreferenceCompat
        releasePreference =
            findPreference<SwitchPreferenceCompat>(release) as SwitchPreferenceCompat

    }

//    private fun setSettings() {
//        val sharedPreferences = preferenceManager.sharedPreferences
//
//    }
}