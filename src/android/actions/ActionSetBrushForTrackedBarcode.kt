/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2019- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.cordova.core.errors.JsonParseError
import com.scandit.datacapture.frameworks.barcode.tracking.BarcodeTrackingModule
import org.apache.cordova.CallbackContext
import org.json.JSONArray
import org.json.JSONException

class ActionSetBrushForTrackedBarcode(
    private val barcodeTrackingModule: BarcodeTrackingModule
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        try {
            val payload = args.getJSONObject(0)
            barcodeTrackingModule.setBasicOverlayBrushForTrackedBarcode(
                payload.toString()
            )

            callbackContext.success()
        } catch (e: JSONException) {
            callbackContext.error(JsonParseError(e.message).toString())
        } catch (e: RuntimeException) {
            callbackContext.error(JsonParseError(e.message).toString())
        }
    }
}
