package android.example.findmyfriends.data.repository

import android.example.findmyfriends.data.source.local.model.UserInfo
import android.example.findmyfriends.data.source.local.LocalSourceImpl
import android.example.findmyfriends.data.source.remote.RemoteSourceImpl
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val localSource: LocalSourceImpl, private val remoteSource: RemoteSourceImpl) :
    Repository {

    override suspend fun loadAllDataToDataBase(users: List<UserInfo>) = localSource.loadAllDataToDataBase(users)

    override suspend fun loadMapData(users: List<UserInfo>) = remoteSource.loadMapData(localSource.retrieveDataFromDataBase())

    override fun downloadData(token: String): List<UserInfo> {
        return remoteSource.getResponse(token)
    }

    override fun destroyDataBase() = localSource.destroyDataBase()
}
