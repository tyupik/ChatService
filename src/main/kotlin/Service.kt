class Service {
    private val chats = mutableListOf<Chat>()

    fun getUnreadChatsCount(userId: Int): Int? {
        val unreadChats = mutableListOf<Chat>()
        for (chat in chats) {
            if (chat.isUnread) unreadChats += chat
        }
        return if (unreadChats.isNotEmpty()) unreadChats.size else {
            println("Непрочитанных чатов нет")
            0
        }
    }


    fun readMessages(userId: Int, chatId: Int, companionId: Int): Boolean {
        for (chat in chats){
            if (chat.chatId == chatId && chat.userId == userId && chat.companionId == companionId){
                for (message in chat.messageList){
                    if (message.isSent) message.isRead = true
                }
                chat.isUnread = false
                return true
            }
        }
        println("Чата с chatId $chatId нет")
        return false
    }

    fun getListOfMessages(userId: Int, chatId: Int, messageId: Int, count: Int): List<Message>? {
        for (chat in chats) {
            if (chat.chatId == chatId && chat.userId == userId) {
                val messages = chat.messageList.filterIndexed { _, message ->
                    message.messageId > messageId - 1
                }
                if (messages.isNotEmpty())
                    return if (messages.size <= count) messages else messages.take(count)
            }
        }
        println("Нет сообщений")
        return null
    }


    fun createMessage(userId: Int, chatId: Int, text: String): Boolean {
        for (chat in chats) {
            if (chat.chatId == chatId && chat.userId == userId) {
                val lastMessageId = chat.messageList.last().messageId + 1
                chat.messageList.add(Message(lastMessageId, text, isSent = true, isRead = false))
                return true
            }
        }
        println("Чата с chatId $chatId не найдено")
        return false
    }


    fun getChats(userId: Int): MutableList<Chat>? {
        val getChats = mutableListOf<Chat>()
        for (chat in chats) {
            if (chat.userId == userId) {
                if (chat.messageList.isEmpty()) {
                    println("Чат с chatId ${chat.chatId} пустой")
                } else getChats.add(chat)
            }
        }
        return if (getChats.isNotEmpty()) getChats else null
    }


    fun deleteMessage(userId: Int, chatId: Int, messageId: Int): Boolean {
        for (chat in chats) {
            if (chat.chatId == chatId && chat.userId == userId) {
                for (message in chat.messageList) {
                    if (message.messageId == messageId) {
                        chat.messageList.remove(message)
                        println("Сообщение с messageId $messageId удалено")
                    }
                    if (chat.messageList.isEmpty()) {
                        println("Чат с chatId ${chat.chatId} удален, т.к. в нем не осталось сообщений")
                        chats.remove(chat)
                    }
                    return true
                }
            }
        }
        return false
    }


    fun deleteChat(userId: Int, chatId: Int): Boolean {
        for (chat in chats) {
            if (chat.userId == userId && chat.chatId == chatId) {
                chats.remove(chat)
                return true
            }
        }
        println("У пользователья с userId $userId нет чата с chatId $chatId")
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