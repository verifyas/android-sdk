package com.verifypayments.android.sample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.verifypayments.android.PaymentActivity
import com.verifypayments.android.sample.backend.ApiFactory
import com.verifypayments.android.sample.model.Session
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_sample_payment.*

class SamplePaymentActivity : AppCompatActivity() {

    private val api = ApiFactory.createApi()
    private var rxDisposable: CompositeDisposable = CompositeDisposable()

    companion object {
        const val PAY_REQUEST_CODE = 42
        const val LOG_TAG = "MainActivity"

        // TODO: Use your own server to get session id. Don't store secret key on mobile client!
        const val SECRET_KEY = "sk_test_qVVcUhRMU5IlsSIdNtl76spasYeTphFL"
        const val PUBLIC_KEY = "pk_test_0tvOQ9iKq3PRaMfJle1myjFxuVEPazJP"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_payment)
    }

    fun onPayClick(view: View) {
        val description = descriptionEditText.text.toString()
        val amount = Integer.parseInt(amountEditText.text.toString())

        val disposable = api.createSession("Token $SECRET_KEY", "AED", amount, description)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSessionFetched, this::onRequestError)
        rxDisposable.add(disposable)
    }

    private fun onRequestError(error: Throwable) {
        Toast.makeText(this, "Error fetching session id", Toast.LENGTH_LONG).show();
        Log.e(LOG_TAG, "Error fetching session id", error)
    }

    private fun onSessionFetched(session: Session) {
        PaymentActivity.startForResult(this, PUBLIC_KEY, session.id, null, true, PAY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val text = if (resultCode == Activity.RESULT_OK) {
            val status = data!!.getStringExtra(PaymentActivity.EXTRA_RESULT_STATUS)
            val id = data.getStringExtra(PaymentActivity.EXTRA_RESULT_ID)
            getString(R.string.result_ok, status, id)
        } else {
            getString(R.string.result_canceled)
        }
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        rxDisposable.clear()
    }
}
