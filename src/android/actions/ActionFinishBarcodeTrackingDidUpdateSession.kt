/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2023- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.cordova.core.utils.successAndKeepCallback
import com.scandit.datacapture.frameworks.barcode.tracking.BarcodeTrackingModule
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionFinishBarcodeTrackingDidUpdateSession(
    private val barcodeTrackingModule: BarcodeTrackingModule
) : Action {
    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        val payload = args.getJSONObject(0)
        barcodeTrackingModule.finishDidUpdateSession(payload.optBoolean("enabled", true))
        callbackContext.successAndKeepCallback()
    }
}
