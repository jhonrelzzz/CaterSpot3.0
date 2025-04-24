package com.intprog32.caterspot30.Data

import android.os.Parcel
import android.os.Parcelable

data class DishData(
    val name: String,
    val imageResId: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(imageResId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DishData> {
        override fun createFromParcel(parcel: Parcel): DishData {
            return DishData(parcel)
        }

        override fun newArray(size: Int): Array<DishData?> {
            return arrayOfNulls(size)
        }
    }
}