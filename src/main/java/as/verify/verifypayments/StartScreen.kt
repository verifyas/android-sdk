package `as`.verify.verifypayments

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.start_screen.*

class StartScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_screen)

        val link = Uri.parse("https://webview.verifypayments.dev")
            .buildUpon()
            .appendQueryParameter("publicKey", "123")
            .appendQueryParameter("sessionId", "123")
            .build()

        val webSettings = webview.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        WebView.setWebContentsDebuggingEnabled(true);
        webview.webViewClient = WebViewClient()
        webview.loadUrl(link.toString())
    }

}
