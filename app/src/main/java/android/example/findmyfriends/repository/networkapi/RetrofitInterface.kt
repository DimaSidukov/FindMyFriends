package android.example.findmyfriends.repository.networkapi

import retrofit2.Retrofit
import javax.inject.Inject

interface RetrofitInterface {

    fun setRequest(token: String)

    fun createService()

    fun runRetrofit(token: String) : Boolean
}