package android.example.findmyfriends.retrofitservice

import android.example.findmyfriends.vkdata.friendsinfo.GetVkFriendsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface VkFriendsService {

    @GET
    fun getFriendList(@Url request: String) : Call<GetVkFriendsData>
}

