package com.cyb3rko.androidlicenses

import android.content.Context
import android.text.Html
import android.text.Spanned
import android.util.Log

import androidx.core.text.toSpanned

class AndroidLicenses {

    companion object {
        @SuppressWarnings("WeakerAccess")
        const val APACHE_2_0 = "apache_2.0"

        @SuppressWarnings("WeakerAccess")
        const val CC_BY_3_0 = "cc_by_3.0"

        @SuppressWarnings("WeakerAccess")
        const val CC_BY_4_0 = "cc_by_4.0"

        @SuppressWarnings("WeakerAccess")
        const val CC_BY_SA_3_0 = "cc_by_sa_3.0"

        @SuppressWarnings("WeakerAccess")
        const val CC_BY_SA_4_0 = "cc_by_sa_4.0"

        @SuppressWarnings("WeakerAccess")
        const val MIT = "mit"

        private var apache20: Spanned? = null
        private var ccBy30: Spanned? = null
        private var ccBy40: Spanned? = null
        private var ccBySa30: Spanned? = null
        private var ccBySa40: Spanned? = null
        private var mit: Spanned? = null

        private lateinit var appContext: Context

        fun init(appContext: Context) {
            this.appContext = appContext
        }

        fun get(licenseName: String) : Spanned {
            return when (licenseName) {
                APACHE_2_0,
                CC_BY_3_0,
                CC_BY_4_0,
                CC_BY_SA_3_0,
                CC_BY_SA_4_0,
                MIT -> getLicenseText(licenseName)

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
                CC_BY_3_0 -> ccBy30
                CC_BY_4_0 -> ccBy40
                CC_BY_SA_3_0 -> ccBySa30
                CC_BY_SA_4_0 -> ccBySa40
                MIT -> mit

                else -> null
            }
        }

        private fun setSpannedObject(licenseName: String) : Spanned {
            @Suppress("DEPRECATION")
            val newSpannedObject = Html.fromHtml(appContext.assets.open("$licenseName.html").bufferedReader().use { it.readText() })

            when (licenseName) {
                APACHE_2_0 -> apache20 = newSpannedObject
                CC_BY_3_0 -> ccBy30 = newSpannedObject
                CC_BY_4_0 -> ccBy40 = newSpannedObject
                CC_BY_SA_3_0 -> ccBySa30 = newSpannedObject
                CC_BY_SA_4_0 -> ccBySa40 = newSpannedObject
                MIT -> mit = newSpannedObject
            }

            return newSpannedObject
        }
    }
}
