package com.cyb3rko.androidlicensessample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View

import androidx.appcompat.app.AppCompatActivity

import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.cyb3rko.androidlicenses.AndroidLicenses

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidLicenses.init(applicationContext)
    }

    fun onClick(view: View?) {
        when (view?.id) {
            R.id.apache_2_0 -> showLicense(AndroidLicenses.APACHE_2_0)
            R.id.apache_2_0_plain -> showLicense(AndroidLicenses.APACHE_2_0_PLAIN)
            R.id.cc_by_3_0 -> showLicense(AndroidLicenses.CC_BY_3_0)
            R.id.cc_by_3_0_plain -> showLicense(AndroidLicenses.CC_BY_3_0_PLAIN)
            R.id.cc_by_4_0 -> showLicense(AndroidLicenses.CC_BY_4_0)
            R.id.cc_by_4_0_plain -> showLicense(AndroidLicenses.CC_BY_4_0_PLAIN)
            R.id.cc_by_sa_3_0 -> showLicense(AndroidLicenses.CC_BY_SA_3_0)
            R.id.cc_by_sa_3_0_plain -> showLicense(AndroidLicenses.CC_BY_SA_3_0_PLAIN)
            R.id.cc_by_sa_4_0 -> showLicense(AndroidLicenses.CC_BY_SA_4_0)
            R.id.cc_by_sa_4_0_plain -> showLicense(AndroidLicenses.CC_BY_SA_4_0_PLAIN)
            R.id.mit -> showLicense(AndroidLicenses.MIT)
            R.id.mit_plain -> showLicense(AndroidLicenses.MIT_PLAIN)
        }
    }

    private fun showLicense(license: String) {
        MaterialDialog(this, BottomSheet()).show {
            message(0, AndroidLicenses.get(license))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_about -> startActivity(Intent(applicationContext, About::class.java))
            R.id.action_github -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/cyb3rko/android-licenses-library")))
        }

        return super.onOptionsItemSelected(item)
    }
}
