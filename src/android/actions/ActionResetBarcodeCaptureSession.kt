/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2022- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.frameworks.barcode.capture.BarcodeCaptureModule
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionResetBarcodeCaptureSession(
    private val barcodeCaptureModule: BarcodeCaptureModule
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        barcodeCaptureModule.resetSession(null)
        callbackContext.success()
    }
}
