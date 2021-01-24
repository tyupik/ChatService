import org.junit.Assert.*
import org.junit.Test

class ServiceTest() {
    private val service = Service()

    @Test
    fun createChat() {
        val result = service.createChat(1, 2, "text")
        assertTrue(result)
    }

    @Test
    fun deleteChat() {
        service.createChat(1, 2, "text")
        val result = service.deleteChat(1, 1)
        assertTrue(result)
    }

    @Test
    fun deleteMessage() {
        service.createChat(1, 2, "text")
        val result = service.deleteMessage(1, 1, 1)
        assertTrue(result)
    }

    @Test
    fun createMessageTrue() {
        service.createChat(1, 2, "text")
        val result = service.createMessage(1, 1, "TEXT")
        assertTrue(result)
    }

    @Test
    fun createMessageFalse() {
        service.createChat(1, 2, "text")
        val result = service.createMessage(1, 2, "TEXT")
        assertTrue(!result)
    }

    @Test
    fun getListOfMessages() {
        service.createChat(1, 2, "text_1")
        service.createMessage(1, 1, "text_2")
        service.createMessage(1, 1, "text_3")
        val result = service.getListOfMessages(1, 1, 2, 1).toString()
        assertEquals("[Message(messageId=2, message=text_2, isSent=true, isRead=true)]", result)
    }

    @Test
    fun getListOfMessagesEmpty() {
        val result = service.getListOfMessages(1, 1, 2, 1)
        if (result != null) {
            assertTrue(result.isEmpty())
        }
    }

    @Test
    fun getChats() {
        service.createChat(1, 2, "text_1")
        service.createChat(1, 3, "text_2")
        val result = service.getChats(1).toString()
        assertEquals(
            "[Chat(chatId=1, userId=1, companionId=2, messageList=[Message(messageId=1, message=text_1, isSent=true, isRead=false)], isUnread=true), Chat(chatId=2, userId=1, companionId=3, messageList=[Message(messageId=1, message=text_2, isSent=true, isRead=false)], isUnread=true)]",
            result
        )
    }

    @Test
    fun getChatsNull() {
        val result = service.getChats(1)
        assertTrue(result.isEmpty())
    }

    @Test
    fun getUnreadChatsCount() {
        service.createChat(1, 2, "text_1")
        service.createChat(1, 3, "text_2")
        service.createChat(1, 4, "text_3")
        service.readMessages(1, 2, 3)
        val result = service.getUnreadChatsCount(1)
        assertEquals(2, result)
    }

    @Test
    fun getUnreadChatsCountNull() {
        val result = service.getUnreadChatsCount(1)
        assertEquals(0, result)
    }
}