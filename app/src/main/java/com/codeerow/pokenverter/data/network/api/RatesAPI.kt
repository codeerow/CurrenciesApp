package com.codeerow.pokenverter.data.network.api

import com.codeerow.pokenverter.data.network.dto.RatesResponse
import io.reactivex.Single
import retrofit2.http.*

interface RatesAPI {

    @GET("latest")
    fun read(@Query("base") anchor: String?): Single<RatesResponse>
}