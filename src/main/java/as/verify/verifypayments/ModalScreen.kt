package `as`.verify.verifypayments

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.start_screen.*

class ModalScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_screen)

        val link = Uri.parse("https://js.stgverifypayments.com/webview/index.html")
            .buildUpon()
            .appendQueryParameter("publicKey", intent.getStringExtra("publicKey"))
            .appendQueryParameter("sessionId", intent.getStringExtra("sessionId"))
            .build()
        Log.v("publicKey", intent.getStringExtra("publicKey"))
        Log.v("sessionId", intent.getStringExtra("sessionId") )
        val webSettings = webview.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        WebView.setWebContentsDebuggingEnabled(true);


        webview.webViewClient = customWebViewClient(progressBar)
        webview.loadUrl(link.toString())
    }

}

class customWebViewClient(val loadingIndicator: View): WebViewClient() {
    override fun onPageFinished(view: WebView, url: String) {
        loadingIndicator.setVisibility(View.GONE)
    }
}