package com.dicoding.picodiploma.fundamentalsub2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserItems (
    var name: String? = null,
    var username: String? = null,
    var avatar: String? = null,
    var followers: Int? = null,
    var following: Int? = null,
    var repository: Int? = null,
    var location: String? = null,
    var company: String? = null
) : Parcelable