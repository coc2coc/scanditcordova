/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2019- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.frameworks.barcode.BarcodeModule
import com.scandit.datacapture.frameworks.barcode.capture.BarcodeCaptureModule
import com.scandit.datacapture.frameworks.barcode.find.BarcodeFindModule
import com.scandit.datacapture.frameworks.barcode.selection.BarcodeSelectionModule
import com.scandit.datacapture.frameworks.barcode.tracking.BarcodeTrackingModule
import org.apache.cordova.CallbackContext
import org.json.JSONArray
import org.json.JSONObject

class ActionInjectDefaults(
    private val barcodeModule: BarcodeModule,
    private val barcodeCaptureModule: BarcodeCaptureModule,
    private val barcodeTrackingModule: BarcodeTrackingModule,
    private val barcodeSelectionModule: BarcodeSelectionModule,
    private val barcodeFindModule: BarcodeFindModule
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        val default = JSONObject(
            barcodeModule.getDefaults() +
                mapOf("BarcodeCapture" to barcodeCaptureModule.getDefaults()) +
                mapOf("BarcodeTracking" to barcodeTrackingModule.getDefaults()) +
                mapOf("BarcodeSelection" to barcodeSelectionModule.getDefaults()) +
                mapOf("BarcodeFind" to barcodeFindModule.getDefaults())
        )

        callbackContext.success(default)
    }
}
