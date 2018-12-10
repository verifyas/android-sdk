package `as`.verify.verifypayments

import android.content.Context
import android.content.Intent
import org.json.JSONObject
import java.io.Serializable

class VerifyPayments(val context: Context, val sessionId: String, val publicKey: String, val onComplete:((JSONObject)->Unit)? = null , val onClose: (()->Void)? = null) : Serializable{
    fun show() {
        val intent = Intent(context, ModalScreen::class.java)
        val reference = PaymentsHandler.addPayment(this)
        intent.putExtra("paymentReference", reference)
        context.startActivity(intent)
    }
}

