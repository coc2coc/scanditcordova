/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2019- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.cordova.core.utils.CordovaEventEmitter
import com.scandit.datacapture.cordova.core.utils.successAndKeepCallback
import com.scandit.datacapture.frameworks.barcode.tracking.BarcodeTrackingModule
import com.scandit.datacapture.frameworks.barcode.tracking.listeners.FrameworksBarcodeTrackingBasicOverlayListener
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionSubscribeBasicOverlay(
    private val barcodeTrackingModule: BarcodeTrackingModule,
    private val eventEmitter: CordovaEventEmitter
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        eventEmitter.registerCallback(
            FrameworksBarcodeTrackingBasicOverlayListener.EVENT_ON_TRACKED_BARCODE_TAPPED,
            callbackContext
        )
        eventEmitter.registerCallback(
            FrameworksBarcodeTrackingBasicOverlayListener.EVENT_SET_BRUSH_FOR_TRACKED_BARCODE,
            callbackContext
        )
        barcodeTrackingModule.addBasicOverlayListener()
        callbackContext.successAndKeepCallback()
    }
}
