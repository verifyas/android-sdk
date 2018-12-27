package `as`.verify.verifypayments

class PaymentsHandler() {
    companion object {
        val payments = ArrayList<VerifyPayments>()
        fun addPayment(payment: VerifyPayments): Int {
            payments.add(payment)
            return (payments.size - 1)
        }
        fun getPayment(index: Int) : VerifyPayments {
            return payments.get(index)
        }
    }
}