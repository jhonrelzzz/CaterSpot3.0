package com.intprog32.caterspot30.Data

import android.os.Parcel
import android.os.Parcelable

data class BookingData(
    val event: String,
    val pickDate: String,
    val pickLocation: String,
    val pickTime: String,
    val noOfGuests: Int,
    val customRequest: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString()?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: ""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(event)
        parcel.writeString(pickDate)
        parcel.writeString(pickLocation)
        parcel.writeInt(noOfGuests)
        parcel.writeString(customRequest)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookingData> {
        override fun createFromParcel(parcel: Parcel): BookingData {
            return BookingData(parcel)
        }

        override fun newArray(size: Int): Array<BookingData?> {
            return arrayOfNulls(size)
        }
    }
}