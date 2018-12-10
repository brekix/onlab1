package hu.mate.onlab.Home

import android.os.Bundle
import android.preference.PreferenceActivity
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatDelegate
import hu.mate.onlab.R

class PrefActivity : PreferenceActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // https://storiesandroid.wordpress.com/2015/10/06/android-settings-using-preference-fragments/
        @Suppress("DEPRECATION")
        addPreferencesFromResource(R.xml.prefs)

        // manifest \ parentActivityName not working
        setupActionBar()
    }

    private val delegate: AppCompatDelegate by lazy {
        AppCompatDelegate.create(this, null)
    }
    private val supportActionBar: ActionBar?
        get() = delegate.supportActionBar

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }




}