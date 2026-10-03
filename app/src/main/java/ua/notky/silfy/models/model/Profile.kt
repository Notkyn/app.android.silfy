package ua.notky.silfy.models.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import ua.notky.silfy.models.enums.AppLanguage

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Entity(
    tableName = "profile",
    indices = [
        Index(value = ["_id"], unique = true)
    ]
)
@Parcelize
data class Profile(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Int?,

    @ColumnInfo(name = "name")
    val name: String,

    /** Translation language and UI language of the profile. Can't be changed after creation */
    @ColumnInfo(name = "language")
    val language: AppLanguage,

    /** Index in R.array.avatar_colors */
    @ColumnInfo(name = "avatar_color")
    val avatarColor: Int,

    /** Uri of the profile photo, `null` — show the first letter on [avatarColor] */
    @ColumnInfo(name = "photo")
    val photo: String?,

    @ColumnInfo(name = "create_time")
    val createTime: Long
) : Parcelable {

    companion object {
        /** Size of R.array.avatar_colors */
        const val AVATAR_COLORS_COUNT = 6
    }
}
