/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2020- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.scandit.datacapture.cordova.barcode.data.SerializableAdvancedOverlayViewActionData
import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.cordova.core.errors.JsonParseError
import com.scandit.datacapture.frameworks.barcode.tracking.BarcodeTrackingModule
import com.scandit.datacapture.frameworks.core.utils.DefaultMainThread
import com.scandit.datacapture.frameworks.core.utils.MainThread
import org.apache.cordova.CallbackContext
import org.json.JSONArray
import org.json.JSONException

class ActionSetViewForTrackedBarcode(
    private val barcodeTrackingModule: BarcodeTrackingModule,
    private val mainThread: MainThread = DefaultMainThread.getInstance()
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        try {
            val data = SerializableAdvancedOverlayViewActionData(
                args.getJSONObject(0)
            )

            val image = getBitmapFromBase64String(data.view?.data)

            mainThread.runOnMainThread {
                val view = barcodeTrackingModule.getTrackedBarcodeViewFromBitmap(
                    data.trackedBarcodeId,
                    image
                ) ?: return@runOnMainThread

                view.layoutParams = ViewGroup.MarginLayoutParams(
                    data.view?.options?.width ?: WRAP_CONTENT,
                    data.view?.options?.height ?: WRAP_CONTENT
                )

                barcodeTrackingModule.setViewForTrackedBarcode(
                    view,
                    data.trackedBarcodeId,
                    data.sessionFrameSequenceId
                )
            }

            callbackContext.success()
        } catch (e: JSONException) {
            println(e)
            callbackContext.error(JsonParseError(e.message).toString())
        } catch (e: RuntimeException) {
            callbackContext.error(JsonParseError(e.message).toString())
        }
    }

    fun getBitmapFromBase64String(string: String?): Bitmap? {
        string ?: return null

        val index = string.indexOf(",")
        return try {
            val trimmedString = string.removeRange(0, index)
            val bytes = Base64.decode(trimmedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) {
            println(e)
            null
        }
    }
}
