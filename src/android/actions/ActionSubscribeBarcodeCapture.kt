/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2019- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.cordova.core.utils.CordovaEventEmitter
import com.scandit.datacapture.cordova.core.utils.successAndKeepCallback
import com.scandit.datacapture.frameworks.barcode.capture.BarcodeCaptureModule
import com.scandit.datacapture.frameworks.barcode.capture.listeners.FrameworksBarcodeCaptureListener
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionSubscribeBarcodeCapture(
    private val barcodeCaptureModule: BarcodeCaptureModule,
    private val eventEmitter: CordovaEventEmitter
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        eventEmitter.registerCallback(
            FrameworksBarcodeCaptureListener.ON_SESSION_UPDATED_EVENT_NAME,
            callbackContext
        )
        eventEmitter.registerCallback(
            FrameworksBarcodeCaptureListener.ON_BARCODE_SCANNED_EVENT_NAME,
            callbackContext
        )
        barcodeCaptureModule.addListener()
        callbackContext.successAndKeepCallback()
    }
}
