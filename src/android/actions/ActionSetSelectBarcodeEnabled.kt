/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2024- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.cordova.core.utils.CordovaResult
import com.scandit.datacapture.frameworks.barcode.selection.BarcodeSelectionModule
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionSetSelectBarcodeEnabled(
    private val barcodeSelectionModule: BarcodeSelectionModule
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        val argument = args.getJSONObject(0)
        val enabled = argument.optBoolean("enabled", false)
        val barcodeJson = argument["barcode"].toString()
        barcodeSelectionModule.setSelectBarcodeEnabled(barcodeJson, enabled, CordovaResult(callbackContext))
    }
}