package com.intprog32.caterspot30.Data

import android.os.Parcel
import android.os.Parcelable

data class CatererData(
    val _id: String,
    val name: String,
    val description: String,
    val imageUrl: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(imageUrl)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<CatererData> {
        override fun createFromParcel(parcel: Parcel): CatererData = CatererData(parcel)
        override fun newArray(size: Int): Array<CatererData?> = arrayOfNulls(size)
    }
}
