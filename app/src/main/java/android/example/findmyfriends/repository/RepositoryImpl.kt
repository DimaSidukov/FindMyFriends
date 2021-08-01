package android.example.findmyfriends.repository

import android.example.findmyfriends.data.database.entity.UserInfo
import android.example.findmyfriends.model.local.source.LocalSourceImpl
import android.example.findmyfriends.model.remote.source.RemoteSourceImpl
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val localSource: LocalSourceImpl, private val remoteSource: RemoteSourceImpl) : Repository{

    override suspend fun retrieveData() = localSource.retrieveData()

    override fun loadMapData(users: List<UserInfo>) = remoteSource.loadMapData(users)

    override suspend fun downloadData(token: String) : Boolean {

        val responseList = remoteSource.getResponse(token)

        return if (responseList.isEmpty()) false
        else {
            localSource.loadAllData(responseList)
            true
        }
    }
}