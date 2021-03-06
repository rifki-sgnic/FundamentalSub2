package com.dicoding.picodiploma.fundamentalsub2.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.dicoding.picodiploma.fundamentalsub2.model.FollowItems
import org.json.JSONArray

class FollowerViewModel : ViewModel() {

    private val listFollower = MutableLiveData<ArrayList<FollowItems>>()

    companion object{
        private val TAG = FollowerViewModel::class.java.simpleName
    }

    fun setListFollower(username: String) {
        val listItems = ArrayList<FollowItems>()

        val url = "https://api.github.com/users/$username/followers"
        AndroidNetworking.get(url)
                .setPriority(Priority.LOW)
                .addHeaders("Authorization", "token 728e000632d471233650f40e1a348666b0731a70")
                .addHeaders("User-Agent", "request")
                .build()
                .getAsJSONArray(object : JSONArrayRequestListener {
                    override fun onResponse(response: JSONArray?) {
                        val arrayResult = JSONArray(response.toString())
                        if (response != null) {
                            try {
                                for (i in 0 until response.length()) {
                                    val objResult = arrayResult.getJSONObject(i)
                                    val users = FollowItems()
                                    users.username = objResult.getString("login")
                                    users.avatar = objResult.getString("avatar_url")
                                    listItems.add(users)
                                }
                                listFollower.postValue(listItems)

                            } catch (e: Exception) {
                                Log.d("Exception", e.message.toString())
                                e.printStackTrace()
                            }
                        }
                    }

                    override fun onError(anError: ANError) {
                        when(anError.errorCode) {
                            401 -> Log.d(TAG, "${anError.errorCode} : Bad Request")
                            403 -> Log.d(TAG, "${anError.errorCode} : Forbidden")
                            404 -> Log.d(TAG, "${anError.errorCode} : Not Found")
                            else -> Log.d(TAG, anError.errorCode.toString())
                        }
                    }

                })
    }

    fun getListFollower(): LiveData<ArrayList<FollowItems>> {
        return listFollower
    }
}