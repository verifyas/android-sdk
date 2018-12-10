package `as`.verify.verifypayments

import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.start_screen.*
import org.json.JSONObject
import java.io.Serializable

class ModalScreen : AppCompatActivity() {
    var payment : VerifyPayments? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_screen)
        this.payment = PaymentsHandler.getPayment(intent.getIntExtra("paymentReference", -1))

        val link = Uri.parse("https://js.stgverifypayments.com/webview/index.html")
            .buildUpon()
            .appendQueryParameter("publicKey",  this.payment?.publicKey)
            .appendQueryParameter("sessionId", this.payment?.sessionId)
            .build()
        Log.v("publicKey", this.payment?.publicKey)
        Log.v("sessionId", this.payment?.sessionId)
        val webSettings = webview.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        WebView.setWebContentsDebuggingEnabled(true);


        webview.webViewClient = customWebViewClient(progressBar)
        webview.addJavascriptInterface(customJavascriptInterface(this,this.payment as VerifyPayments), "Android")
        webview.loadUrl(link.toString())
    }

}

class customWebViewClient(val loadingIndicator: View): WebViewClient() {
    override fun onPageFinished(view: WebView, url: String) {
        loadingIndicator.setVisibility(View.GONE)
    }
}

class customJavascriptInterface(private val mContext: Context, private val payment: VerifyPayments) {

    /** Show a toast from the web page  */
    @JavascriptInterface
    fun onComplete(result: String?) {
        val objectResult = JSONObject(result)
        payment.onComplete?.invoke(objectResult)

    }
    @JavascriptInterface
    fun onClose() {
        payment.onClose?.invoke()
    }
}