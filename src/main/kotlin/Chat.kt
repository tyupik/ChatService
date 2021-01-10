data class Chat(
    val chatId: Int = 0,
    val userId: Int = 0,
    val companionId: Int = 0,
    val messageList: MutableList<Message>,
    var isUnread: Boolean = true
)