class Service {
    private val chats = mutableListOf<Chat>()

    fun getUnreadChatsCount(userId: Int): Int = chats.asSequence()
        .filter { chat -> chat.userId == userId }
        .filter { chat -> chat.isUnread }
        .count()


    fun readMessages(userId: Int, chatId: Int, companionId: Int): Boolean {
        chats.find { chat -> chat.chatId == chatId && chat.companionId == companionId && chat.userId == userId}?.let { chat ->
            chat.messageList.map { message -> message.apply { isRead = true }}
            chat.isUnread = false
            return true
        }
        println("Чата с chatId $chatId нет")
        return false
    }


    fun getListOfMessages(userId: Int, chatId: Int, messageId: Int, count: Int): List<Message>? {
        return chats.find { chat -> chatId == chat.chatId && userId == chat.userId }?.messageList?.asSequence()
            ?.filter { m -> m.messageId > messageId - 1 }
            ?.take(count)
            ?.map { m -> m.apply { isRead = true } }
            ?.toList()
            ?: emptyList()
    }

    fun createMessage(userId: Int, chatId: Int, text: String): Boolean {
        chats.find { chat -> chatId == chat.chatId && userId == chat.userId}?. apply {
            val lastMessageId = messageList[messageList.lastIndex].messageId + 1
            messageList.add(Message(lastMessageId, text, true, false))
            return true
        }
        println("Чата с chatId $chatId не найдено")
        return false
    }

    fun getChats(userId: Int): List<Chat> = chats
        .filter { chat -> userId == chat.userId }

    fun deleteMessage(userId: Int, chatId: Int, messageId: Int): Boolean {
        chats.find { chat -> chatId == chat.chatId && userId == chat.userId }?.apply {
            if (messageList.size > 1) {
                messageList.find { message -> messageId == message.messageId }?.apply {
                    return messageList.remove(this)
                }
            } else if (messageList.size <= 1) {
                return chats.remove(this)
            }
        }
        return false
    }

    fun deleteChat(userId: Int, chatId: Int): Boolean {
        chats.find { chat -> chatId == chat.chatId && userId == chat.userId }?.apply {
            return chats.remove(this)
        }
        return false
    }

    fun createChat(userId: Int, companionId: Int, text: String): Boolean {
        val lastChatId = if (chats.isEmpty()) 1 else chats.last().chatId + 1
        val messages = mutableListOf<Message>()
        messages.add(Message(1, text, isSent = true, isRead = false))
        chats.add(Chat(lastChatId, userId, companionId, messages, true))
        return true
    }
}