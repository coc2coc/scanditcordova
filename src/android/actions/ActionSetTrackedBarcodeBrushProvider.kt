/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2023- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.cordova.core.utils.CordovaEventEmitter
import com.scandit.datacapture.cordova.core.utils.CordovaResult
import com.scandit.datacapture.frameworks.barcode.selection.BarcodeSelectionModule
import com.scandit.datacapture.frameworks.barcode.selection.listeners.FrameworksBarcodeSelectionTrackedBrushProvider
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionSetTrackedBarcodeBrushProvider(
    private val barcodeSelectionModule: BarcodeSelectionModule,
    private val eventEmitter: CordovaEventEmitter
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        eventEmitter.registerCallback(
            FrameworksBarcodeSelectionTrackedBrushProvider.BRUSH_FOR_TRACKED_BARCODE_EVENT_NAME,
            callbackContext
        )
        barcodeSelectionModule.setTrackedBarcodeBrushProvider(CordovaResult(callbackContext))
    }
}
