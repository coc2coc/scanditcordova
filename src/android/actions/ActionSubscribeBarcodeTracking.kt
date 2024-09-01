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
import com.scandit.datacapture.frameworks.barcode.tracking.listeners.FrameworksBarcodeTrackingListener
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionSubscribeBarcodeTracking(
    private val barcodeTrackingModule: BarcodeTrackingModule,
    private val eventEmitter: CordovaEventEmitter
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        eventEmitter.registerCallback(
            FrameworksBarcodeTrackingListener.ON_SESSION_UPDATED_EVENT_NAME,
            callbackContext
        )
        barcodeTrackingModule.addBarcodeTrackingListener()
        callbackContext.successAndKeepCallback()
    }
}
