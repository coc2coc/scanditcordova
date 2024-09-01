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

class ActionSetOffsetForTrackedBarcode(
    private val barcodeTrackingModule: BarcodeTrackingModule
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        try {
            val payload = args.getJSONObject(0)
            barcodeTrackingModule.setOffsetForTrackedBarcode(
                hashMapOf(
                    "offset" to payload.getString(FIELD_OFFSET),
                    "identifier" to payload.getInt(FIELD_TRACKED_BARCODE_ID),
                    "sessionFrameSequenceID" to if (payload.has(FIELD_FRAME_SEQUENCE_ID)) {
                        payload.getLong(FIELD_FRAME_SEQUENCE_ID)
                    } else null
                )
            )

            callbackContext.success()
        } catch (e: JSONException) {
            callbackContext.error(JsonParseError(e.message).toString())
        } catch (e: RuntimeException) {
            callbackContext.error(JsonParseError(e.message).toString())
        }
    }

    companion object {
        private const val FIELD_OFFSET = "offset"
        private const val FIELD_TRACKED_BARCODE_ID = "trackedBarcodeID"
        private const val FIELD_FRAME_SEQUENCE_ID = "sessionFrameSequenceID"
    }
}
