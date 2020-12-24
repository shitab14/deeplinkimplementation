package com.mr_mir.deeplinkimplementation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleDynamicLink()
    }

    fun handleDynamicLink() {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                // Get deep link from result (may be null if no link is found)
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }

                // for the deep link redirecting to https://www.linkedin.com/in/shitabmir/?myparam=dynamiclinktest
                if (deepLink != null && deepLink.getBooleanQueryParameter("myparam", false)) {
                    Log.e("External Deep Shit", "Found Param: $deepLink")
                    val feature = deepLink.getQueryParameter("myparam")
                    if(feature == "dynamiclinktest") {
                        val intent = Intent(this, TestTresActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "No Deep Link", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Log.e("External Deep Shit", "Found No Feature")
                }
            }
            .addOnFailureListener(this) { e ->
                Log.e(
                    "Deep Link",
                    "getDynamicLink:onFailure",
                    e
                )
            }
    }

}