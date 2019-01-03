package com.verifypayments.android

import android.content.Context
import android.content.Intent
import org.json.JSONObject

class VerifyPayments(val context: Context, val sessionId: String, val publicKey: String, val onComplete:((JSONObject)->Unit)? = null , val onClose: (()->Void)? = null, val nextScreen: Intent) {
    fun show() {
        val intent = Intent(context, ModalScreen::class.java)
        val reference = PaymentsHandler.addPayment(this)
        intent.putExtra("paymentReference", reference)
        context.startActivity(intent)
    }
}

