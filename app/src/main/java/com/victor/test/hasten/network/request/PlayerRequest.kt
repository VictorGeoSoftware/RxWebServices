package com.victor.test.hasten.network.request

import com.victor.test.hasten.data.PlayersGroup
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.QueryMap
import java.util.HashMap

/**
 * Created by victorpalmacarrasco on 17/2/18.
 * ${APP_NAME}
 */
interface PlayerRequest {
    @Headers("Content-Type: application/json;charset=UTF-8")

    @GET("bins/66851")
    fun getPlayerListRx(): Observable<ArrayList<PlayersGroup>>

    @GET("bins/66851")
    fun getPlayerList(): Response<ArrayList<PlayersGroup>>
}