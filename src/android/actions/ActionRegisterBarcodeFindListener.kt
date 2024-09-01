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
import com.scandit.datacapture.frameworks.barcode.find.listeners.FrameworksBarcodeFindListener
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionRegisterBarcodeFindListener(
    private val barcodeFindModule: BarcodeFindModule,
    private val eventEmitter: CordovaEventEmitter
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        eventEmitter.registerCallback(
            FrameworksBarcodeFindListener.ON_SEARCH_PAUSED_EVENT_NAME,
            callbackContext
        )
        eventEmitter.registerCallback(
            FrameworksBarcodeFindListener.ON_SEARCH_STARTED_EVENT_NAME,
            callbackContext
        )
        eventEmitter.registerCallback(
            FrameworksBarcodeFindListener.ON_SEARCH_STARTED_EVENT_NAME,
            callbackContext
        )

        barcodeFindModule.addBarcodeFindListener(CordovaResult(callbackContext))
    }
}
