package com.bslsjdk.operit.integrations.http.bridge

import com.bslsjdk.operit.services.ChatServiceCore

internal class WebChatActionBridge(
    private val core: ChatServiceCore
) {
    fun manuallyUpdateMemory() {
        core.getMessageCoordinationDelegate().manuallyUpdateMemory()
    }

    fun manuallySummarizeConversation() {
        core.getMessageCoordinationDelegate().manuallySummarizeConversation()
    }
}
