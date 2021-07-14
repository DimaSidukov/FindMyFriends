package android.example.findmyfriends.repository

interface RepositoryInterface {

    fun downloadData(token : String) : Boolean
}