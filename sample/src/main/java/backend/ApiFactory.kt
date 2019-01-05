package com.verifypayments.android.sample.backend

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Alexey Danilov (danikula@gmail.com).
 */
class ApiFactory {

    companion object {
        fun createApi(): Api {
            return Retrofit.Builder()
                    .baseUrl("https://api.verifypayments.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build().create(Api::class.java)
        }
    }
}
