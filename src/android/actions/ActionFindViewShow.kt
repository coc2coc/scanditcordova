/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2023- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.barcode.handlers.BarcodeFindViewHandler
import com.scandit.datacapture.cordova.core.actions.Action
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionFindViewShow(
    private val barcodeFindViewHandler: BarcodeFindViewHandler
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        barcodeFindViewHandler.setVisible()
    }
}
