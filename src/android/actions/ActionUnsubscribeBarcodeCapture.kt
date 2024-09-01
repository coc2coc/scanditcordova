/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2024- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.cordova.core.utils.CordovaEventEmitter
import com.scandit.datacapture.cordova.core.utils.successAndKeepCallback
import com.scandit.datacapture.frameworks.barcode.capture.BarcodeCaptureModule
import com.scandit.datacapture.frameworks.barcode.capture.listeners.FrameworksBarcodeCaptureListener
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionUnsubscribeBarcodeCapture(
    private val barcodeCaptureModule: BarcodeCaptureModule,
    private val eventEmitter: CordovaEventEmitter
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        eventEmitter.unregisterCallback(
            FrameworksBarcodeCaptureListener.ON_SESSION_UPDATED_EVENT_NAME
        )
        eventEmitter.unregisterCallback(
            FrameworksBarcodeCaptureListener.ON_BARCODE_SCANNED_EVENT_NAME
        )
        barcodeCaptureModule.removeListener()
        callbackContext.success()
    }
}