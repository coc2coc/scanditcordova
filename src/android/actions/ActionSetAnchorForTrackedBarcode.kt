/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2020- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.cordova.core.errors.JsonParseError
import com.scandit.datacapture.frameworks.barcode.tracking.BarcodeTrackingModule
import org.apache.cordova.CallbackContext
import org.json.JSONArray
import org.json.JSONException

class ActionSetAnchorForTrackedBarcode(
    private val barcodeTrackingModule: BarcodeTrackingModule
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        try {
            val argument = args.getJSONObject(0)
            val payload = hashMapOf<String, Any?>(
                "anchor" to argument.getString(FIELD_ANCHOR),
                "identifier" to argument.getInt(FIELD_TRACKED_BARCODE_ID),
                "sessionFrameSequenceID" to if (argument.has(FIELD_FRAME_SEQUENCE_ID)) {
                    argument.getLong(FIELD_FRAME_SEQUENCE_ID)
                } else null
            )
            barcodeTrackingModule.setAnchorForTrackedBarcode(payload)

            callbackContext.success()
        } catch (e: JSONException) {
            callbackContext.error(JsonParseError(e.message).toString())
        } catch (e: RuntimeException) {
            callbackContext.error(JsonParseError(e.message).toString())
        }
    }

    companion object {
        private const val FIELD_ANCHOR = "anchor"
        private const val FIELD_TRACKED_BARCODE_ID = "trackedBarcodeID"
        private const val FIELD_FRAME_SEQUENCE_ID = "sessionFrameSequenceID"
    }
}
