package com.verifypayments.android

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.verifypayments.android.PaymentActivity.Companion.EXTRA_RESULT_ID
import com.verifypayments.android.PaymentActivity.Companion.EXTRA_RESULT_STATUS
import kotlinx.android.synthetic.main.activity_payment.*
import java.util.regex.Pattern


class PaymentActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PUBLIC_KEY = "publicKey"
        const val EXTRA_SESSION_ID = "sessionId"
        const val EXTRA_TOKEN = "token"
        const val EXTRA_RESULT_ID = "id"
        const val EXTRA_RESULT_STATUS = "status"

        fun startForResult(activity: Activity, publicKey: String, sessionId: String, token: String?, requestCode: Int) {
            val intent = buildIntent(activity, publicKey, sessionId, token)
            activity.startActivityForResult(intent, requestCode)
        }

        fun startForResult(fragment: Fragment, publicKey: String, sessionId: String, token: String?, requestCode: Int) {
            val activity = fragment.activity
                    ?: throw IllegalStateException("Fragment is detached from activity")
            val intent = buildIntent(activity, publicKey, sessionId, token)
            fragment.startActivityForResult(intent, requestCode)
        }

        private fun buildIntent(context: Context, publicKey: String, sessionId: String, token: String?): Intent {
            return Intent(context, PaymentActivity::class.java)
                    .putExtra(EXTRA_PUBLIC_KEY, publicKey)
                    .putExtra(EXTRA_SESSION_ID, sessionId)
                    .putExtra(EXTRA_TOKEN, token)
        }
    }

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.activity_payment)

        setupWebView()
        val link = buildUri()
        webView.loadUrl(link.toString())
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        WebView.setWebContentsDebuggingEnabled(true)

        webView.webViewClient = LoadingAwareWebViewClient(progressBar)
        webView.addJavascriptInterface(CallbackJavascriptInterface(this), "Android")
    }

    private fun buildUri(): Uri {
        val builder = Uri.parse("https://js.stgverifypayments.com/webview/index.html")
                .buildUpon()
                .appendQueryParameter("publicKey", getRequiredExtra(EXTRA_PUBLIC_KEY))
                .appendQueryParameter("sessionId", getRequiredExtra(EXTRA_SESSION_ID))
                .appendQueryParameter("tokenize", "true")
        val token = intent?.getStringExtra(EXTRA_TOKEN)
        token?.let { builder.appendQueryParameter("token", token) }
        return builder.build()
    }

    private fun getRequiredExtra(name: String): String {
        return if (intent.hasExtra(name)) {
            intent.getStringExtra(name)
        } else {
            throw IllegalArgumentException("There is no extra named $name!")
        }
    }
}

class LoadingAwareWebViewClient(private val loader: View) : WebViewClient() {

    override fun onPageFinished(view: WebView, url: String) {
        loader.visibility = View.GONE
    }
}

class CallbackJavascriptInterface(private val activity: Activity) {

    private var completed = false

    @JavascriptInterface
    fun onComplete(result: String) {
        completed = true
        val resultIntent = Intent()
                .putExtra(EXTRA_RESULT_ID, getJsonValue(result, "id"))
                .putExtra(EXTRA_RESULT_STATUS, getJsonValue(result, "status"))
        activity.setResult(Activity.RESULT_OK, resultIntent)
        activity.finish()
    }

    @JavascriptInterface
    fun onClose() {
        if (!completed) {
            activity.setResult(Activity.RESULT_CANCELED)
            activity.finish()
        }
    }

    // use reg exp to avoid adding dependency on any json library
    private fun getJsonValue(json: String, name: String): String {
        val pattern = Pattern.compile("\"$name\"\\s*:\\s*\"(.*?)\"")
        val matcher = pattern.matcher(json)
        matcher.find()
        return matcher.group(1)
    }
}
