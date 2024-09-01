/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2023- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.cordova.core.utils.CordovaEventEmitter
import com.scandit.datacapture.cordova.core.utils.CordovaResult
import com.scandit.datacapture.frameworks.barcode.find.BarcodeFindModule
import com.scandit.datacapture.frameworks.barcode.find.listeners.FrameworksBarcodeFindViewUiListener
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionUnregisterBarcodeFindViewListener(
    private val barcodeFindModule: BarcodeFindModule,
    private val eventEmitter: CordovaEventEmitter
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        eventEmitter.unregisterCallback(
            FrameworksBarcodeFindViewUiListener.ON_FINISH_BUTTON_TAPPED_EVENT_NAME
        )

        barcodeFindModule.removeBarcodeFindViewListener(CordovaResult(callbackContext))
    }
}
