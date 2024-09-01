/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2023- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.cordova.core.utils.CordovaEventEmitter
import com.scandit.datacapture.frameworks.barcode.selection.BarcodeSelectionModule
import com.scandit.datacapture.frameworks.barcode.selection.listeners.FrameworksBarcodeSelectionAimedBrushProvider
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionRemoveAimedBarcodeBrushProvider(
    private val barcodeSelectionModule: BarcodeSelectionModule,
    private val eventEmitter: CordovaEventEmitter
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        eventEmitter.unregisterCallback(
            FrameworksBarcodeSelectionAimedBrushProvider.BRUSH_FOR_AIMED_BARCODE_EVENT_NAME
        )
        barcodeSelectionModule.removeAimedBarcodeBrushProvider()
        callbackContext.success()
    }
}
