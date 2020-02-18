package en.ubb.trippie_kotlin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "word_table")
data class Word (

    @SerializedName("name")
    @ColumnInfo(name = "word") var word: String,

    @SerializedName("schedule")
    @ColumnInfo(name = "opening_hours") var hours: String?,

    @SerializedName("price")
    @ColumnInfo(name = "ticket_price") var ticket_price: Int?,

    @SerializedName("id")
    @ColumnInfo(name = "remote_id") var remote_id: Int=-1
) : Serializable
{
    @PrimaryKey (autoGenerate = true) var localId: Int=0
}

//data class Word (@PrimaryKey @ColumnInfo(name = "word") val word: String)