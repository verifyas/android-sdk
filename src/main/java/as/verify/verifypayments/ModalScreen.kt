package `as`.verify.verifypayments

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
            .appendQueryParameter("publicKey", "pk_test_QzDNuDfbzX3BcA96Wi33UcEWexRX3jPT")
            .appendQueryParameter("sessionId", "ses_lRtstoNFzm8M")
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
