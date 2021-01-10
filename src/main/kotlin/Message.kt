data class Message (
    val messageId: Int = 0,
    val message: String = "message",
    var isSent: Boolean = true,
    var isRead: Boolean = false
)