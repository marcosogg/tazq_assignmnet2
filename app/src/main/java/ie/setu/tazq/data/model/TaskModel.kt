package ie.setu.tazq.data.model

import androidx.room.Entity
import com.google.firebase.firestore.DocumentId
import java.util.Date

@Entity
data class TaskModel(
    @DocumentId val _id: String = "N/A",
    val paymentType: String = "N/A",
    val paymentAmount: Int = 0,
    var message: String = "Go Homer!",
    val dateDonated: Date = Date(),
    val dateModified: Date = Date(),
    var email: String = "joe@bloggs.com"
)

val fakeTask = List(30) { i ->
    TaskModel(
        _id = "12345" + i,
        "PayPal $i",
        i.toInt(),
        "Message $i",
        Date(),
        Date()
    )
}