package com.victor.test.hasten

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.victor.test.hasten.data.PlayersGroup
import com.victor.test.hasten.network.request.PlayerRequest
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /**
         * Muy buenas!!
         *
         * Pues aquí te dejo un pequeño ejemplo de como montaría la parte Network de tu app.
         * No me he metido en colar injección de dependencias ni nada,
         * eso ya lo sabes hacer tú! ;-)
         *
         * La consumición del web service es lo interesante, y simplemente se trata de devolver
         * un ArrayList del Objeto que hayas construido para recibir la respuesta del server
         *
         * De paso te dejo un poquito de programación reactiva, para que vayas echando un ojo,
         * no he puesto nada relevante, pero sí es un principio para que veas alguna pequeña
         * diferencia.
         *
         *
         * Abrazos y ENHORABUENAAAA!!! :-D
         *
         */

        val okHttpClient = OkHttpClient.Builder()
                .readTimeout(BuildConfig.TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(BuildConfig.TIME_OUT, TimeUnit.SECONDS)
                .build()

        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.URL_BASE)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) /** NECESARIO PARA RX JAVA */
                .build()

        val requestRx = retrofit.create(PlayerRequest::class.java)

        requestRx.getPlayerListRx()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext( {
                    // TODO :: pon algun ProgressBar
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { t ->
                            Log.i("Jorge", "Lista recibida con ${t.size} items" )

                            for (player in t) {
                                Log.i("Jorge", "Detaller player :: $player" )
                            }
                        },
                        {
                            error ->
                            Log.i("Jorge", "Error en llamada :: ${error.localizedMessage}" )
                        },
                        {
                            // Aqui es cuando acaba los procesos
                            // Idoneo para quitar el waiter
                            Log.i("Jorge", "Acaba llamada")

                            // TODO :: si has puesto ProgressBar, le quitas :-)
                        }
                )
    }
}
