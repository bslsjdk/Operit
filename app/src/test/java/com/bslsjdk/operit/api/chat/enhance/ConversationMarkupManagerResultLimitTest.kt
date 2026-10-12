package com.bslsjdk.operit.api.chat.enhance

import com.bslsjdk.operit.core.tools.StringResultData
import com.bslsjdk.operit.core.tools.ToolExecutionLimits
import com.bslsjdk.operit.data.model.ToolResult
import com.bslsjdk.operit.util.ChatMarkupRegex
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ConversationMarkupManagerResultLimitTest {

    @Test
    fun `keeps every tool result while bounding each result independently`() {
        val results = listOf(
            ToolResult("first_tool", true, StringResultData("a".repeat(63_000))),
            ToolResult("second_tool", true, StringResultData("b".repeat(63_000)))
        )

        val message = ConversationMarkupManager.buildToolResultMessage(results)
        val resultBlocks = ChatMarkupRegex.toolResultAnyPattern.findAll(message).toList()

        assertEquals(2, resultBlocks.size)
        assertTrue(
            resultBlocks.all {
                it.value.length <= ToolExecutionLimits.MAX_SINGLE_TOOL_RESULT_MESSAGE_CHARS
            }
        )
        assertTrue(message.contains("name=\"first_tool\""))
        assertTrue(message.contains("name=\"second_tool\""))
    }
}
