package com.cyb3rko.androidlicenses

import android.content.Context
import android.text.Html
import android.text.Spanned
import android.util.Log

import androidx.core.text.toSpanned

class AndroidLicenses {

    companion object {
        const val APACHE_2_0 = "apache_2.0"
        const val APACHE_2_0_PLAIN = "apache_2.0_plain"

        const val CC_BY_3_0 = "cc_by_3.0"
        const val CC_BY_3_0_PLAIN = "cc_by_3.0_plain"

        const val CC_BY_4_0 = "cc_by_4.0"
        const val CC_BY_4_0_PLAIN = "cc_by_4.0_plain"

        const val CC_BY_SA_3_0 = "cc_by_sa_3.0"
        const val CC_BY_SA_3_0_PLAIN = "cc_by_sa_3.0_plain"

        const val CC_BY_SA_4_0 = "cc_by_sa_4.0"
        const val CC_BY_SA_4_0_PLAIN = "cc_by_sa_4.0_plain"

        const val MIT = "mit"
        const val MIT_PLAIN = "mit_plain"

        private var apache20: Spanned? = null
        private var apache20Plain: Spanned? = null
        private var ccBy30: Spanned? = null
        private var ccBy30Plain: Spanned? = null
        private var ccBy40: Spanned? = null
        private var ccBy40Plain: Spanned? = null
        private var ccBySa30: Spanned? = null
        private var ccBySa30Plain: Spanned? = null
        private var ccBySa40: Spanned? = null
        private var ccBySa40Plain: Spanned? = null
        private var mit: Spanned? = null
        private var mitPlain: Spanned? = null

        private lateinit var appContext: Context

        fun init(appContext: Context) {
            this.appContext = appContext
        }

        fun get(licenseName: String) : Spanned {
            return when (licenseName) {
                APACHE_2_0,
                APACHE_2_0_PLAIN,
                CC_BY_3_0,
                CC_BY_3_0_PLAIN,
                CC_BY_4_0,
                CC_BY_4_0_PLAIN,
                CC_BY_SA_3_0,
                CC_BY_SA_3_0_PLAIN,
                CC_BY_SA_4_0,
                CC_BY_SA_4_0_PLAIN,
                MIT,
                MIT_PLAIN -> getLicenseText(licenseName)

                else -> {
                    Log.e("AndroidLicenses", "License not found for: $licenseName")
                    "License not found".toSpanned()
                }
            }
        }

        private fun getLicenseText(licenseName: String) : Spanned {
            val spannedObject = getSpannedObject(licenseName)

            if (spannedObject != null) {
                Log.i("AndroidLicenses", "Cached license Spanned object restored: $licenseName")
                return spannedObject
            } else {
                Log.i("AndroidLicenses", "New Spanned object created for: $licenseName")
                return setSpannedObject(licenseName)
            }
        }

        private fun getSpannedObject(licenseName: String) : Spanned? {
            return when (licenseName) {
                APACHE_2_0 -> apache20
                APACHE_2_0_PLAIN -> apache20Plain
                CC_BY_3_0 -> ccBy30
                CC_BY_3_0_PLAIN -> ccBy30Plain
                CC_BY_4_0 -> ccBy40
                CC_BY_4_0_PLAIN -> ccBy40Plain
                CC_BY_SA_3_0 -> ccBySa30
                CC_BY_SA_3_0_PLAIN -> ccBySa30Plain
                CC_BY_SA_4_0 -> ccBySa40
                CC_BY_SA_4_0_PLAIN -> ccBySa40Plain
                MIT -> mit
                MIT_PLAIN -> mitPlain

                else -> null
            }
        }

        private fun setSpannedObject(licenseName: String) : Spanned {
            val newSpannedObject: Spanned
            if (licenseName.endsWith("_plain", true)) {
                newSpannedObject = appContext.assets.open("$licenseName.txt").bufferedReader().readText().toSpanned()
            } else {
                @Suppress("DEPRECATION")
                newSpannedObject = Html.fromHtml(appContext.assets.open("$licenseName.html").bufferedReader().use { it.readText() })
            }

            when (licenseName) {
                APACHE_2_0 -> apache20 = newSpannedObject
                APACHE_2_0_PLAIN -> apache20Plain = newSpannedObject
                CC_BY_3_0 -> ccBy30 = newSpannedObject
                CC_BY_3_0_PLAIN -> ccBy30Plain = newSpannedObject
                CC_BY_4_0 -> ccBy40 = newSpannedObject
                CC_BY_4_0_PLAIN -> ccBy40Plain = newSpannedObject
                CC_BY_SA_3_0 -> ccBySa30 = newSpannedObject
                CC_BY_SA_3_0_PLAIN -> ccBySa30Plain = newSpannedObject
                CC_BY_SA_4_0 -> ccBySa40 = newSpannedObject
                CC_BY_SA_4_0_PLAIN -> ccBySa40Plain = newSpannedObject
                MIT -> mit = newSpannedObject
                MIT_PLAIN -> mitPlain = newSpannedObject
            }

            return newSpannedObject
        }
    }
}
