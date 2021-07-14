package android.example.findmyfriends.repository.networkapi

interface RetrofitInterface {

    fun setRequest(token: String)

    fun createService()

    fun runRetrofit(token: String) : Boolean
}