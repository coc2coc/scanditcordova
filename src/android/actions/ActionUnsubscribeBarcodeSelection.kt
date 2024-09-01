/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2024- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.cordova.core.utils.CordovaEventEmitter
import com.scandit.datacapture.cordova.core.utils.successAndKeepCallback
import com.scandit.datacapture.frameworks.barcode.selection.BarcodeSelectionModule
import com.scandit.datacapture.frameworks.barcode.selection.listeners.FrameworksBarcodeSelectionListener
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionUnsubscribeBarcodeSelection(
    private val barcodeSelectionModule: BarcodeSelectionModule,
    private val eventEmitter: CordovaEventEmitter
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        eventEmitter.unregisterCallback(
            FrameworksBarcodeSelectionListener.ON_SESSION_UPDATE_EVENT_NAME
        )
        eventEmitter.unregisterCallback(
            FrameworksBarcodeSelectionListener.ON_SELECTION_UPDATE_EVENT_NAME
        )
        barcodeSelectionModule.removeListener()
        callbackContext.success()
    }
}
