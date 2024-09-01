/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2023- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.actions

import com.scandit.datacapture.cordova.barcode.handlers.BarcodeFindViewHandler
import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.frameworks.barcode.find.BarcodeFindModule
import org.apache.cordova.CallbackContext
import org.json.JSONArray

class ActionCreateFindView(
    private val barcodeFindModule: BarcodeFindModule,
    private val barcodeFindViewHandler: BarcodeFindViewHandler
) : Action {

    override fun run(args: JSONArray, callbackContext: CallbackContext) {
        val viewJson = args.toString()
        val container = barcodeFindViewHandler.prepareContainer()

        val result = barcodeFindModule.getView(container, viewJson)
        if (result.isFailure) {
            callbackContext.error(
                result.exceptionOrNull()?.message
                    ?: "Unable to create the BarcodeFindView from the given json=$viewJson"
            )
            return
        }

        barcodeFindViewHandler.addBarcodeFindViewContainer(container)
        barcodeFindViewHandler.render()
        callbackContext.success()
    }
}
