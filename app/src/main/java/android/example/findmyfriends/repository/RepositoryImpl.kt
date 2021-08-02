package android.example.findmyfriends.repository

import android.example.findmyfriends.data.database.entity.UserInfo
import android.example.findmyfriends.model.local.source.LocalSourceImpl
import android.example.findmyfriends.model.remote.source.RemoteSourceImpl
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val localSource: LocalSourceImpl, private val remoteSource: RemoteSourceImpl) : Repository {

    override suspend fun loadAllData(users: List<UserInfo>) = localSource.loadAllData(users)

    override suspend fun loadMapData(users: List<UserInfo>) = remoteSource.loadMapData(localSource.retrieveData())

    override fun downloadData(token: String): List<UserInfo> {
        return remoteSource.getResponse(token)
    }

    override fun destroyDataBase() = localSource.destroyDataBase()
}
