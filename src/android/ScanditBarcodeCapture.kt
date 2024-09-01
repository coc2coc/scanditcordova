/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2019- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode

import android.Manifest
import com.scandit.datacapture.cordova.barcode.factories.BarcodeCaptureActionFactory
import com.scandit.datacapture.cordova.barcode.handlers.BarcodeFindViewHandler
import com.scandit.datacapture.cordova.core.ScanditCaptureCore
import com.scandit.datacapture.cordova.core.actions.ActionSend
import com.scandit.datacapture.cordova.core.communication.CameraPermissionGrantedListener
import com.scandit.datacapture.cordova.core.errors.InvalidActionNameError
import com.scandit.datacapture.cordova.core.errors.JsonParseError
import com.scandit.datacapture.cordova.core.factories.ActionFactory
import com.scandit.datacapture.cordova.core.handlers.ActionsHandler
import com.scandit.datacapture.cordova.core.handlers.CameraPermissionsActionsHandlerHelper
import com.scandit.datacapture.cordova.core.utils.CordovaEventEmitter
import com.scandit.datacapture.cordova.core.utils.successAndKeepCallback
import com.scandit.datacapture.frameworks.barcode.BarcodeModule
import com.scandit.datacapture.frameworks.barcode.capture.BarcodeCaptureModule
import com.scandit.datacapture.frameworks.barcode.capture.listeners.FrameworksBarcodeCaptureListener
import com.scandit.datacapture.frameworks.barcode.find.BarcodeFindModule
import com.scandit.datacapture.frameworks.barcode.find.listeners.FrameworksBarcodeFindListener
import com.scandit.datacapture.frameworks.barcode.find.listeners.FrameworksBarcodeFindViewUiListener
import com.scandit.datacapture.frameworks.barcode.selection.BarcodeSelectionModule
import com.scandit.datacapture.frameworks.barcode.selection.listeners.FrameworksBarcodeSelectionAimedBrushProvider
import com.scandit.datacapture.frameworks.barcode.selection.listeners.FrameworksBarcodeSelectionListener
import com.scandit.datacapture.frameworks.barcode.selection.listeners.FrameworksBarcodeSelectionTrackedBrushProvider
import com.scandit.datacapture.frameworks.barcode.tracking.BarcodeTrackingModule
import com.scandit.datacapture.frameworks.barcode.tracking.listeners.FrameworksBarcodeTrackingAdvancedOverlayListener
import com.scandit.datacapture.frameworks.barcode.tracking.listeners.FrameworksBarcodeTrackingBasicOverlayListener
import com.scandit.datacapture.frameworks.barcode.tracking.listeners.FrameworksBarcodeTrackingListener
import org.apache.cordova.CallbackContext
import org.apache.cordova.CordovaPlugin
import org.json.JSONArray
import org.json.JSONObject

class ScanditBarcodeCapture :
    CordovaPlugin(),
    BarcodeActionsListeners,
    CameraPermissionGrantedListener {

    private val eventEmitter = CordovaEventEmitter()
    private val barcodeModule = BarcodeModule()
    private val barcodeCaptureModule = BarcodeCaptureModule(
        FrameworksBarcodeCaptureListener(eventEmitter)
    )
    private val barcodeTrackingModule = BarcodeTrackingModule(
        FrameworksBarcodeTrackingListener(eventEmitter),
        FrameworksBarcodeTrackingBasicOverlayListener(eventEmitter),
        FrameworksBarcodeTrackingAdvancedOverlayListener(eventEmitter)
    )
    private val barcodeSelectionModule = BarcodeSelectionModule(
        FrameworksBarcodeSelectionListener(eventEmitter),
        FrameworksBarcodeSelectionAimedBrushProvider(eventEmitter),
        FrameworksBarcodeSelectionTrackedBrushProvider(eventEmitter)
    )

    private val barcodeFindModule = BarcodeFindModule(
        FrameworksBarcodeFindListener(eventEmitter),
        FrameworksBarcodeFindViewUiListener(eventEmitter)
    )
    private val barcodeFindViewHandler: BarcodeFindViewHandler = BarcodeFindViewHandler()

    private val actionFactory: ActionFactory = BarcodeCaptureActionFactory(
        barcodeModule,
        barcodeCaptureModule,
        barcodeTrackingModule,
        barcodeSelectionModule,
        barcodeFindModule,
        barcodeFindViewHandler,
        eventEmitter
    )
    private val actionsHandler: ActionsHandler = ActionsHandler(
        actionFactory, CameraPermissionsActionsHandlerHelper(actionFactory)
    )

    private var lastBarcodeCaptureEnabledState: Boolean = false
    private var lastBarcodeTrackingEnabledState: Boolean = false
    private var lastBarcodeSelectionEnabledState: Boolean = false
    private var lastBarcodeFindEnabledState: Boolean = false

    override fun pluginInitialize() {
        barcodeFindViewHandler.attachWebView(webView.view, cordova.activity)

        super.pluginInitialize()
        ScanditCaptureCore.addPlugin(serviceName)
        barcodeModule.onCreate(cordova.context)
        barcodeCaptureModule.onCreate(cordova.context)
        barcodeTrackingModule.onCreate(cordova.context)
        barcodeSelectionModule.onCreate(cordova.context)

        if (cordova.hasPermission(Manifest.permission.CAMERA)) {
            onCameraPermissionGranted()
        }
    }

    override fun onStop() {
        lastBarcodeCaptureEnabledState = barcodeCaptureModule.isModeEnabled()
        barcodeCaptureModule.setModeEnabled(false)

        lastBarcodeTrackingEnabledState = barcodeTrackingModule.isModeEnabled()
        barcodeTrackingModule.setModeEnabled(false)

        lastBarcodeSelectionEnabledState = barcodeSelectionModule.isModeEnabled()
        barcodeSelectionModule.setModeEnabled(false)

        lastBarcodeFindEnabledState = barcodeFindModule.isModeEnabled()
        barcodeFindModule.setModeEnabled(false)
    }

    override fun onStart() {
        barcodeCaptureModule.setModeEnabled(lastBarcodeCaptureEnabledState)
        barcodeTrackingModule.setModeEnabled(lastBarcodeTrackingEnabledState)
        barcodeSelectionModule.setModeEnabled(lastBarcodeSelectionEnabledState)
        barcodeFindModule.setModeEnabled(lastBarcodeFindEnabledState)
    }

    override fun onReset() {
        barcodeModule.onDestroy()
        barcodeCaptureModule.onDestroy()
        barcodeTrackingModule.onDestroy()
        barcodeSelectionModule.onDestroy()
        pluginInitialize()
    }

    override fun execute(
        action: String,
        args: JSONArray,
        callbackContext: CallbackContext
    ): Boolean {
        return try {
            actionsHandler.addAction(action, args, callbackContext)
        } catch (e: InvalidActionNameError) {
            println(e)
            false
        } catch (e: Exception) {
            println(e)
            true
        }
    }

    override fun onCameraPermissionGranted() {
        actionsHandler.onCameraPermissionGranted()
    }

    override fun onJsonParseError(error: Throwable, callbackContext: CallbackContext) {
        JsonParseError(error.message).sendResult(callbackContext)
    }

    override fun onSendAction(
        actionName: String,
        message: JSONObject,
        callbackContext: CallbackContext
    ) {
        callbackContext.successAndKeepCallback(message)
    }
}

interface BarcodeActionsListeners : ActionSend.ResultListener
