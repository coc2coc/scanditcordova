/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2024- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.cordova.core.utils.CordovaResult
import com.scandit.datacapture.frameworks.barcode.capture.BarcodeCaptureModule
import com.scandit.datacapture.frameworks.barcode.selection.BarcodeSelectionModule
import com.scandit.datacapture.frameworks.barcode.tracking.BarcodeTrackingModule
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionSelectAimedBarcode(
    private val barcodeSelectionModule: BarcodeSelectionModule
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        barcodeSelectionModule.selectAimedBarcode()
        callbackContext.success()
    }
}
