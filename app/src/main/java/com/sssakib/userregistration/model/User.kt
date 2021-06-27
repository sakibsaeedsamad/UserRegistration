package com.sssakib.userregistration.model

import com.google.gson.annotations.SerializedName


class User {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("mobile")
    var mobile: String? = null

    @SerializedName("email")
    var email: String? = null

    @SerializedName("address")
    var address: String? = null

    @SerializedName("password")
    var password: String

    @SerializedName("id")
    var id: Long = 0

    @SerializedName("image")
    var image: String? = null

    constructor(
        name: String?,
        mobile: String?,
        email: String?,
        address: String?,
        password: String,
        id: Long,
        image: String?
    ) {
        this.name = name
        this.mobile = mobile
        this.email = email
        this.address = address
        this.password = password
        this.id = id
        this.image = image
    }

    constructor(
        name: String?,
        mobile: String?,
        email: String?,
        address: String?,
        password: String,
        image: String?
    ) {
        this.name = name
        this.mobile = mobile
        this.email = email
        this.address = address
        this.password = password
        this.image = image
    }

    constructor(mobile: String?, password: String) {
        this.mobile = mobile
        this.password = password
    }

    constructor(
        email: String?,
        address: String?,
        password: String,
        image: String?
    ) {
        this.email = email
        this.address = address
        this.password = password
        this.image = image
    }


}
