/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2020- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.frameworks.barcode.tracking.BarcodeTrackingModule
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionClearTrackedBarcodeViews(
    private val barcodeTrackingModule: BarcodeTrackingModule
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        barcodeTrackingModule.clearAdvancedOverlayTrackedBarcodeViews()
        callbackContext.success()
    }
}
