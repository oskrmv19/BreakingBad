package com.oskr19.breakingbad.modules.characters.domain

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.oskr19.breakingbad.core.domain.toArrayStringFromJson
import com.oskr19.breakingbad.core.domain.toJsonString
import java.sql.Timestamp
import java.util.Calendar

class CharacterDTO(
    @SerializedName("char_id")
    var charId: Int,

    @SerializedName("name")
    var name: String,

    @SerializedName("birthday")
    var birthday: String,

    @SerializedName("occupation")
    var occupation: ArrayList<String> = arrayListOf(),

    @SerializedName("img")
    var img: String,

    @SerializedName("status")
    var status: String,

    @SerializedName("nickname")
    var nickname: String,

    @SerializedName("portrayed")
    var portrayed: String
): Parcelable {
    var isFavorite = false
    var createdAt = Timestamp(Calendar.getInstance().time.time)

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?.toArrayStringFromJson()?: arrayListOf(),
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:""
    ) {
        isFavorite = parcel.readByte() != 0.toByte()
        createdAt = Timestamp(parcel.readLong())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(charId)
        parcel.writeString(name)
        parcel.writeString(birthday)
        parcel.writeString(occupation.toJsonString())
        parcel.writeString(img)
        parcel.writeString(status)
        parcel.writeString(nickname)
        parcel.writeString(portrayed)
        parcel.writeByte(if (isFavorite) 1 else 0)
        parcel.writeLong(createdAt.time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CharacterDTO> {
        override fun createFromParcel(parcel: Parcel): CharacterDTO {
            return CharacterDTO(parcel)
        }

        override fun newArray(size: Int): Array<CharacterDTO?> {
            return arrayOfNulls(size)
        }
    }
}