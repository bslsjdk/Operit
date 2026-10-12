package com.bslsjdk.operit.services

import com.bslsjdk.operit.data.model.ChatMessage

interface ChatServiceUiBridge {
    fun updateWebServerForCurrentChat(chatId: String)
    fun resetAttachmentPanelState()
    fun clearReplyToMessage()
    fun getReplyToMessage(): ChatMessage?
}

object EmptyChatServiceUiBridge : ChatServiceUiBridge {
    override fun updateWebServerForCurrentChat(chatId: String) = Unit

    override fun resetAttachmentPanelState() = Unit

    override fun clearReplyToMessage() = Unit

    override fun getReplyToMessage(): ChatMessage? = null
}
