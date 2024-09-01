/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2021- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.frameworks.barcode.selection.BarcodeSelectionModule
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionResetBarcodeSelectionSession(
    private val barcodeSelectionModule: BarcodeSelectionModule
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        barcodeSelectionModule.resetLatestSession(null)
        callbackContext.success()
    }
}
