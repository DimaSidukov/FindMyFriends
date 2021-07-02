package android.example.findmyfriends.model.remote.vk.retrofitservice

import android.example.findmyfriends.model.remote.vk.friendsinfo.GetVkFriendsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface VkFriendsService {

    @GET
    fun getFriendList(@Url request: String) : Call<GetVkFriendsData>
}

