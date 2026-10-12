package com.bslsjdk.operit.plugins.workflow

import com.bslsjdk.operit.data.repository.WorkflowRepository
import com.bslsjdk.operit.plugins.OperitPlugin
import com.bslsjdk.operit.plugins.lifecycle.AppLifecycleEvent
import com.bslsjdk.operit.plugins.lifecycle.AppLifecycleHookParams
import com.bslsjdk.operit.plugins.lifecycle.AppLifecycleHookPlugin
import com.bslsjdk.operit.plugins.lifecycle.AppLifecycleHookPluginRegistry
import com.bslsjdk.operit.util.AppLogger

private object WorkflowAppLifecycleHookPlugin : AppLifecycleHookPlugin {
    private const val TAG = "WorkflowLifecyclePlugin"
    @Volatile
    private var firstActivityStartHandled = false

    override val id: String = "builtin.workflow.app-lifecycle"

    override suspend fun onEvent(
        event: AppLifecycleEvent,
        params: AppLifecycleHookParams
    ) {
        try {
            when (event) {
                AppLifecycleEvent.APPLICATION_CREATE -> {
                    firstActivityStartHandled = false
                }

                AppLifecycleEvent.ACTIVITY_START -> {
                    if (firstActivityStartHandled) {
                        return
                    }
                    firstActivityStartHandled = true
                    WorkflowRepository(params.context.applicationContext)
                        .triggerWorkflowsByColdStartAppOpen(
                            extras =
                                params.extras.mapValues { (_, value) ->
                                    value?.toString().orEmpty()
                                }
                        )
                }

                else -> Unit
            }
        } catch (e: Exception) {
            AppLogger.e(TAG, "Failed to process workflow app-open trigger: ${event.wireName}", e)
        }
    }
}

object WorkflowLifecyclePlugin : OperitPlugin {
    override val id: String = "builtin.workflow.lifecycle"

    override fun register() {
        AppLifecycleHookPluginRegistry.register(WorkflowAppLifecycleHookPlugin)
    }
}
