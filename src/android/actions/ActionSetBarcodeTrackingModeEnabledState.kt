/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2019- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.frameworks.barcode.tracking.BarcodeTrackingModule
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionSetBarcodeTrackingModeEnabledState(
    private val barcodeTrackingModule: BarcodeTrackingModule
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        val argument = args.getJSONObject(0)
        barcodeTrackingModule.setModeEnabled(argument.optBoolean("enabled", false))
        callbackContext.success()
    }
}
