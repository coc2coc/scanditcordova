/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2020- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.data

import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import org.json.JSONObject

data class SerializableAdvancedOverlayViewActionData(
    val view: SerializableAdvancedOverlayView?,
    val trackedBarcodeId: Int,
    val sessionFrameSequenceId: Long?
) {

    constructor(json: JSONObject) : this(
        view = SerializableAdvancedOverlayView.fromJson(json.optJSONObject(FIELD_VIEW)),
        trackedBarcodeId = json.getInt(FIELD_TRACKED_BARCODE_ID),
        sessionFrameSequenceId = if (json.has(FIELD_FRAME_SEQUENCE_ID)) {
            json.getLong(FIELD_FRAME_SEQUENCE_ID)
        } else null
    )
}

data class SerializableAdvancedOverlayView(
    val data: String,
    val options: SerializableAdvancedOverlayViewOptions
) {

    constructor(jsonObject: JSONObject) : this(
        data = jsonObject.getString(FIELD_DATA),
        options = SerializableAdvancedOverlayViewOptions.fromJson(
            jsonObject.getJSONObject(FIELD_OPTIONS)
        )
    )

    companion object {
        private const val FIELD_DATA = "data"
        private const val FIELD_OPTIONS = "options"

        fun fromJson(jsonObject: JSONObject?): SerializableAdvancedOverlayView? {
            if (jsonObject == null) return null

            return SerializableAdvancedOverlayView(jsonObject)
        }
    }
}

data class SerializableAdvancedOverlayViewOptions(
    val scale: Float,
    val width: Int,
    val height: Int
) {

    companion object {
        private const val FIELD_WIDTH = "width"
        private const val FIELD_HEIGHT = "height"
        private const val FIELD_SIZE = "size"
        private const val FIELD_SCALE = "scale"

        fun fromJson(json: JSONObject): SerializableAdvancedOverlayViewOptions {
            val scale: Float = json.optDouble(FIELD_SCALE, 1.0).toFloat()

            val size = json.optJSONObject(FIELD_SIZE) ?: JSONObject()
            val width: Int = size.optDouble(FIELD_WIDTH).run {
                if (isNaN()) {
                    WRAP_CONTENT
                } else {
                    (this * scale).toInt()
                }
            }
            val height: Int = size.optDouble(FIELD_HEIGHT).run {
                if (isNaN()) {
                    WRAP_CONTENT
                } else {
                    (this * scale).toInt()
                }
            }

            return SerializableAdvancedOverlayViewOptions(scale, width, height)
        }
    }
}

private const val FIELD_VIEW = "view"
private const val FIELD_TRACKED_BARCODE_ID = "trackedBarcodeID"
private const val FIELD_FRAME_SEQUENCE_ID = "sessionFrameSequenceID"
