package android.example.findmyfriends.data.source.remote.api

import android.example.findmyfriends.data.source.remote.model.friendsinfo.GetVkFriendsData
import retrofit2.http.GET
import retrofit2.http.Url

interface VkFriendsService {

    @GET
    suspend fun getFriendList(@Url request: String) : GetVkFriendsData
}

