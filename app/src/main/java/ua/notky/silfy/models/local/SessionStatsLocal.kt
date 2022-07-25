package ua.notky.silfy.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.notky.silfy.models.model.ResultTraining

/**
 * @project Silfy
 * @author Yevgeniy Zarechniy on 25.07.2022
 * @email evgeniy.zarechnyi@4k.com.ua
 */

@Entity(tableName = "session_stats")
data class SessionStatsLocal(

    @PrimaryKey(autoGenerate = false)
    val id: String,

    @ColumnInfo(name = "start_time")
    val startTime: Long,

    @ColumnInfo(name = "update_time")
    val updateTime: Long,

    @ColumnInfo(name = "finish_reason_type")
    val finishReasonType: Int,

    @ColumnInfo(name = "category_ids")
    val categoryIds: List<Int>,

    @ColumnInfo(name = "word_ids")
    val wordIds: List<Int>,

    @ColumnInfo(name = "result_map")
    val resultMap: MutableMap<Int, ResultTraining>,

    @ColumnInfo(name = "user_id")
    val userId: Int
)