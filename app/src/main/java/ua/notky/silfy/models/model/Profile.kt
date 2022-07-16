package ua.notky.silfy.models.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 19.06.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Entity(
    tableName = "profile",
    indices = [
        Index(value = ["_id"], unique = true),
        Index(value = ["email"], unique = true)
    ]
)
@Parcelize
data class Profile(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Int?,

    @ColumnInfo(name = "first_name")
    val firstName: String?,

    @ColumnInfo(name = "last_name")
    val lastName: String?,

    @ColumnInfo(name = "avatar")
    val avatar: String?,

    @ColumnInfo(name = "email")
    val email: String?,

    @ColumnInfo(name = "create_time")
    val createTime: Long?
) : Parcelable