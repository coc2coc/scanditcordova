/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2024- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.cordova.core.utils.CordovaEventEmitter
import com.scandit.datacapture.frameworks.barcode.tracking.BarcodeTrackingModule
import com.scandit.datacapture.frameworks.barcode.tracking.listeners.FrameworksBarcodeTrackingBasicOverlayListener
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionUnregisterBasicOverlayListener(
    private val barcodeTrackingModule: BarcodeTrackingModule,
    private val eventEmitter: CordovaEventEmitter
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        eventEmitter.unregisterCallback(
            FrameworksBarcodeTrackingBasicOverlayListener.EVENT_ON_TRACKED_BARCODE_TAPPED
        )
        eventEmitter.unregisterCallback(
            FrameworksBarcodeTrackingBasicOverlayListener.EVENT_SET_BRUSH_FOR_TRACKED_BARCODE
        )
        barcodeTrackingModule.removeBasicOverlayListener()
        callbackContext.success()
    }
}