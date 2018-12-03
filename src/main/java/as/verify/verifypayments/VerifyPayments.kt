package `as`.verify.verifypayments

import android.content.Context
import android.content.Intent

class VerifyPayments(val context: Context, val sessionId: String, val publicKey: String, val onComplete:((Object)->Void)? = null, val onClose: ((Object)->Void)? = null) {
    fun show() {
        val intent = Intent(context, ModalScreen::class.java)
        intent.putExtra("sessionId", sessionId)
        intent.putExtra("publicKey", publicKey)
        context.startActivity(intent)
    }
}

