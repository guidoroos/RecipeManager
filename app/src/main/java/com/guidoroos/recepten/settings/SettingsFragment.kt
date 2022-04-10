package com.guidoroos.recepten.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.guidoroos.recepten.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}