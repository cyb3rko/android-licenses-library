package com.cyb3rko.androidlicenses

import android.content.Context
import android.text.Html
import android.text.Spanned
import android.util.Log

import androidx.core.text.toSpanned

object AndroidLicenses {
    private lateinit var appContext: Context
    private val licenseCache: MutableMap<String, Spanned> = mutableMapOf()

    @JvmStatic
    fun init(context: Context) {
        this.appContext = context
    }

    @JvmStatic
    fun get(licenseName: String): Spanned {
        return License.findByName(licenseName)?.getSpanned(appContext) ?: let {
            Log.e("AndroidLicenses", "License not found for: $licenseName")
            "License not found".toSpanned()
        }
    }

    enum class License(val licenseName: String) {
        APACHE_2_0("apache_2.0"),
        APACHE_2_0_PLAIN("apache_2.0_plain"),
        CC_BY_3_0("cc_by_3.0"),
        CC_BY_3_0_PLAIN("cc_by_3.0_plain"),
        CC_BY_4_0("cc_by_4.0"),
        CC_BY_4_0_PLAIN("cc_by_4.0_plain"),
        CC_BY_SA_3_0("cc_by_sa_3.0"),
        CC_BY_SA_3_0_PLAIN("cc_by_sa_3.0_plain"),
        CC_BY_SA_4_0("cc_by_sa_4.0"),
        CC_BY_SA_4_0_PLAIN("cc_by_sa_4.0_plain"),
        MIT("mit"),
        MIT_PLAIN("mit_plain");

        fun getSpanned(context: Context): Spanned {
            val cachedLicense = licenseCache[licenseName]

            if (cachedLicense != null) {
                Log.d("AndroidLicenses", "Cached license Spanned object restored: $licenseName")
            }

            return cachedLicense ?: if (licenseName.endsWith("_plain", true)) {
                context.assets.open("$licenseName.txt").bufferedReader().readText().toSpanned()
            } else {
                @Suppress("DEPRECATION")
                Html.fromHtml(context.assets.open("$licenseName.html").bufferedReader().use { it.readText() })
            }.apply {
                Log.d("AndroidLicenses", "New Spanned object created for: $licenseName")
                licenseCache[licenseName] = this
            }
        }

        companion object {
            internal fun findByName(name: String): License? {
                return values().firstOrNull {
                    it.licenseName == name
                }
            }
        }
    }
}
