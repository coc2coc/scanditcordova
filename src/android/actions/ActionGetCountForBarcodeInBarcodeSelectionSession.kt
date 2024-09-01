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

class ActionGetCountForBarcodeInBarcodeSelectionSession(
    private val barcodeSelectionModule: BarcodeSelectionModule
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        val selectionIdentifier = args.getString(0)
        val selectionCount = barcodeSelectionModule.getBarcodeCount(selectionIdentifier)
        callbackContext.success(selectionCount)
    }
}
