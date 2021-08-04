package com.everdeng.android.app.wanandroid.ui.article.adapter.bean

import android.os.Parcel
import android.os.Parcelable

class ArticleItem(): Parcelable {
    var id: Int = 0
    var user: String = ""
    var title: String = ""
    var time: String = ""
    var sort: String = ""
    var liked: Boolean = false
    var description: String = ""
    var cover: String = ""
    var type: Int = 0
    var articleUrl = ""

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        user = parcel.readString()!!
        title = parcel.readString()!!
        time = parcel.readString()!!
        sort = parcel.readString()!!
        liked = parcel.readByte() != 0.toByte()
        description = parcel.readString()!!
        cover = parcel.readString()!!
        type = parcel.readInt()
        articleUrl = parcel.readString()!!
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeString(user)
        dest?.writeString(title)
        dest?.writeString(time)
        dest?.writeString(sort)
        dest?.writeByte((if (liked) 1 else 0).toByte())
        dest?.writeString(description)
        dest?.writeString(cover)
        dest?.writeInt(type)
        dest?.writeString(articleUrl)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ArticleItem> {
        override fun createFromParcel(parcel: Parcel): ArticleItem {
            return ArticleItem(parcel)
        }

        override fun newArray(size: Int): Array<ArticleItem?> {
            return arrayOfNulls(size)
        }
    }
}