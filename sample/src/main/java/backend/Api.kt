package com.verifypayments.android.sample.backend

import com.verifypayments.android.sample.model.Session
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Verify Payments API endpoint.
 *
 * DON'T USE this approach in production code. You MUST fetch session id from <b>your backend<b>.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
interface Api {

    @FormUrlEncoded
    @POST("sessions")
    fun createSession(
            @Header("Authorization") auth: String,
            @Field("currency") currency: String,
            @Field("amount") amount: Int,
            @Field("description") description: String
    ): Observable<Session>
}
