/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2024- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.frameworks.barcode.tracking.BarcodeTrackingModule
import com.scandit.datacapture.cordova.core.utils.CordovaEventEmitter
import com.scandit.datacapture.frameworks.barcode.tracking.listeners.FrameworksBarcodeTrackingAdvancedOverlayListener
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionUnregisterAdvancedOverlayListener(
    private val barcodeTrackingModule: BarcodeTrackingModule,
    private val eventEmitter: CordovaEventEmitter
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        eventEmitter.unregisterCallback(
            FrameworksBarcodeTrackingAdvancedOverlayListener.EVENT_ANCHOR_FOR_TRACKED_BARCODE
        )
        eventEmitter.unregisterCallback(
            FrameworksBarcodeTrackingAdvancedOverlayListener.EVENT_OFFSET_FOR_TRACKED_BARCODE
        )
        eventEmitter.unregisterCallback(
            FrameworksBarcodeTrackingAdvancedOverlayListener.EVENT_WIDGET_FOR_TRACKED_BARCODE        
        )
        eventEmitter.unregisterCallback(
            FrameworksBarcodeTrackingAdvancedOverlayListener.EVENT_DID_TAP_VIEW_FOR_TRACKED_BARCODE
        )
        barcodeTrackingModule.removeAdvancedOverlayListener()
        callbackContext.success()
    }
}