package com.dicoding.picodiploma.fundamentalsub2.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FollowItems (
        var username: String? = null,
        var avatar: String? = null
) : Parcelable