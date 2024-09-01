/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2019- Scandit AG. All rights reserved.
 */

package com.scandit.datacapture.cordova.barcode.factories

import com.scandit.datacapture.cordova.barcode.actions.*
import com.scandit.datacapture.cordova.barcode.handlers.BarcodeFindViewHandler
import com.scandit.datacapture.cordova.core.actions.Action
import com.scandit.datacapture.cordova.core.errors.InvalidActionNameError
import com.scandit.datacapture.cordova.core.factories.ActionFactory
import com.scandit.datacapture.cordova.core.utils.CordovaEventEmitter
import com.scandit.datacapture.frameworks.barcode.BarcodeModule
import com.scandit.datacapture.frameworks.barcode.capture.BarcodeCaptureModule
import com.scandit.datacapture.frameworks.barcode.find.BarcodeFindModule
import com.scandit.datacapture.frameworks.barcode.selection.BarcodeSelectionModule
import com.scandit.datacapture.frameworks.barcode.tracking.BarcodeTrackingModule

class BarcodeCaptureActionFactory(
    private val barcodeModule: BarcodeModule,
    private val barcodeCaptureModule: BarcodeCaptureModule,
    private val barcodeTrackingModule: BarcodeTrackingModule,
    private val barcodeSelectionModule: BarcodeSelectionModule,
    private val barcodeFindModule: BarcodeFindModule,
    private val barcodeFindViewHandler: BarcodeFindViewHandler,
    private val eventEmitter: CordovaEventEmitter
) : ActionFactory {

    private val availableActions: Map<String, Action> by lazy {
        mapOf(
            INJECT_DEFAULTS to createActionInjectDefaults(),
            SUBSCRIBE_BARCODE_CAPTURE to createActionSubscribeBarcodeCapture(),
            UNSUBSCRIBE_BARCODE_CAPTURE to createActionUnsubscribeBarcodeCapture(),
            SUBSCRIBE_BARCODE_TRACKING to createActionSubscribeBarcodeTracking(),
            UNREGISTER_BARCODE_TRACKING to createActionUnregisterBarcodeTrackingListener(),
            SUBSCRIBE_BARCODE_SELECTION to createActionSubscribeBarcodeSelection(),
            UNSUBSCRIBE_BARCODE_SELECTION to createActionUnsubscribeBarcodeSelection(),
            ACTION_GET_COUNT_FOR_BARCODE_IN_BARCODE_SELECTION_SESSION to
                createActionGetCountForBarcodeInBarcodeSelectionSession(),
            ACTION_RESET_BARCODE_CAPTURE_SESSION to createActionResetBarcodeCaptureSession(),
            ACTION_RESET_BARCODE_TRACKING_SESSION to createActionResetBarcodeTrackingSession(),
            ACTION_RESET_BARCODE_SELECTION_SESSION to createActionResetBarcodeSelectionSession(),
            ACTION_RESET_BARCODE_SELECTION to createActionResetBarcodeSelection(),
            ACTION_UNFREEZE_CAMERA_IN_BARCODE_SELECTION to 
                createActionUnfreezeCameraInBarcodeSelection(),
            SELECT_AIMED_BARCODE to createActionSelectAimedBarcode(),
            INCREASE_COUNT_FOR_BARCODES_IN_BARCODE_SELECTION to createActionIncreaseCountForBarcodesInBarcodeSelection(),
            UNSELECT_BARCODES to createActionUnselectBarcodes(),
            SET_SELECT_BARCODE_ENABLED to createSetSelectBarcodeEnabled(),
            SUBSCRIBE_BASIC_OVERLAY_LISTENER to createActionSubscribeBasicOverlay(),
            UNREGISTER_BASIC_OVERLAY_LISTENER to createActionUnregisterBasicOverlayListener(),
            SUBSCRIBE_ADVANCED_OVERLAY_LISTENER to createActionSubscribeAdvancedOverlay(),
            UNREGISTER_ADVANCED_OVERLAY_LISTENER to createActionUnregisterAdvancedOverlayListener(),
            
            SET_BRUSH_FOR_TRACKED_BARCODE to createActionSetBrushForTrackedBarcode(),
            CLEAR_TRACKED_BARCODE_BRUSHES to createActionClearTrackedBarcodeBrushes(),
            SET_VIEW_FOR_TRACKED_BARCODE to createActionSetViewForTrackedBarcode(),
            SET_OFFSET_FOR_TRACKED_BARCODE to createActionSetOffsetForTrackedBarcode(),
            SET_ANCHOR_FOR_TRACKED_BARCODE to createActionSetAnchorForTrackedBarcode(),
            CLEAR_TRACKED_BARCODE_VIEWS to createActionClearTrackedBarcodeViews(),

            FINISH_BARCODE_CAPTURE_DID_UPDATE_SESSION to ActionFinishBarcodeCaptureSessionUpdate(
                barcodeCaptureModule
            ),
            FINISH_BARCODE_CAPTURE_DID_CAPTURE to ActionFinishBarcodeCaptureDidScan(
                barcodeCaptureModule
            ),
            FINISH_BARCODE_SELECTION_DID_UPDATE_SELECTION to
                ActionFinishBarcodeSelectionDidUpdateSelection(barcodeSelectionModule),
            FINISH_BARCODE_SELECTION_DID_UPDATE_SESSION to
                ActionFinishBarcodeSelectionDidUpdateSession(barcodeSelectionModule),
            FINISH_BARCODE_TRACKING_DID_UPDATE_SESSION to
                ActionFinishBarcodeTrackingDidUpdateSession(barcodeTrackingModule),
            ACTION_CREATE_FIND_VIEW to createActionCreateFindView(),
            ACTION_UPDATE_FIND_VIEW to createActionUpdateFindView(),
            ACTION_UPDATE_FIND_MODE to createActionUpdateFindMode(),
            ACTION_REGISTER_FIND_LISTENER to createActionRegisterBarcodeFindListener(),
            ACTION_UNREGISTER_FIND_LISTENER to createActionUnregisterBarcodeFindListener(),
            ACTION_REGISTER_FIND_VIEW_LISTENER to createActionRegisterBarcodeFindViewListener(),
            ACTION_UNREGISTER_FIND_VIEW_LISTENER to createActionUnregisterBarcodeFindViewListener(),
            ACTION_FIND_VIEW_ON_PAUSE to createActionFindViewOnPause(),
            ACTION_FIND_VIEW_ON_RESUME to createActionFindViewOnResume(),
            ACTION_FIND_SET_ITEM_LIST to createActionFindSetItemList(),
            ACTION_FIND_VIEW_PAUSE_SEARCHING to createActionFindViewPauseSearching(),
            ACTION_FIND_VIEW_START_SEARCHING to createActionFindViewStartSearching(),
            ACTION_FIND_VIEW_STOP_SEARCHING to createActionFindViewStopSearching(),
            ACTION_FIND_MODE_PAUSE to createActionFindModePause(),
            ACTION_FIND_MODE_START to createActionFindModeStart(),
            ACTION_FIND_MODE_STOP to createActionFindModeStop(),
            ACTION_FIND_VIEW_SHOW to createActionFindViewShow(),
            ACTION_FIND_VIEW_HIDE to createActionFindViewHide(),
            ACTION_SET_BARCODE_CAPTURE_MODE_ENABLED_STATE to
                createActionSetBarcodeCaptureModeEnabledState(),
            ACTION_SET_BARCODE_SELECTION_MODE_ENABLED_STATE to
                createActionSetBarcodeSelectionModeEnabledState(),
            ACTION_SET_BARCODE_TRACKING_MODE_ENABLED_STATE to
                createActionSetBarcodeTrackingModeEnabledState(),
            ACTION_UPDATE_BC_MODE to createActionUpdateBCMode(),
            ACTION_UPDATE_BC_OVERLAY to createActionUpdateBCOverlay(),
            ACTION_APPLY_BC_MODE_SETTINGS to createActionApplyBCModeSettings(),

            ACTION_UPDATE_BS_MODE to createActionUpdateBSMode(),
            ACTION_UPDATE_BS_BASIC_OVERLAY to createActionUpdateBSBasicOverlay(),
            ACTION_APPLY_BS_MODE_SETTINGS to createActionApplyBSModeSettings(),
            ACTION_SET_TEXT_FOR_AIM_TO_SELECT_AUTO_HINT to
                createActionSetTextForAimToSelectAutoHint(),
            ACTION_REMOVE_AIMED_BARCODE_BRUSH_PROVIDER to
                createActionRemoveAimedBarcodeBrushProvider(),
            ACTION_SET_AIMED_BARCODE_RUSH_PROVIDER to createActionSetAimedBarcodeBrushProvider(),
            ACTION_FINISH_BRUSH_FOR_AIMED_BARCODE_CALLBACK to
                createActionFinishBrushForAimedBarcodeCallback(),
            ACTION_REMOVE_TRACKED_BARCODE_BRUSH_PROVIDER to
                createActionRemoveTrackedBarcodeBrushProvider(),
            ACTION_SET_TRACKED_BARCODE_BRUSH_PROVIDER to
                createActionSetTrackedBarcodeBrushProvider(),
            ACTION_FINISH_BRUSH_FOR_TRACKED_BARCODE_CALLBACK to
                createActionFinishBrushForTrackedBarcodeCallback(),

            ACTION_UPDATE_BT_BASIC_OVERLAY to
                createActionUpdateBarcodeTrackingBasicOverlay(),
            ACTION_UPDATE_BT_ADVANCED_OVERLAY to
                createActionUpdateBarcodeTrackingAdvancedOverlay(),
            ACTION_UPDATE_BT_MODE to createActionUpdateBarcodeTrackingMode(),
            ACTION_APPLY_BT_MODE_SETTINGS to createActionApplyBarcodeTrackingModeSettings(),
        )
    }

    @Throws(InvalidActionNameError::class)
    override fun provideAction(actionName: String): Action =
        availableActions[actionName] ?: throw InvalidActionNameError(actionName)

    override fun canBeRunWithoutCameraPermission(actionName: String): Boolean =
        actionName !in ACTIONS_REQUIRING_CAMERA

    private fun createActionInjectDefaults(): Action = ActionInjectDefaults(
        barcodeModule,
        barcodeCaptureModule,
        barcodeTrackingModule,
        barcodeSelectionModule,
        barcodeFindModule
    )

    private fun createActionSubscribeBarcodeCapture(): Action = ActionSubscribeBarcodeCapture(
        barcodeCaptureModule, eventEmitter
    )

    private fun createActionUnsubscribeBarcodeCapture(): Action = ActionUnsubscribeBarcodeCapture(
        barcodeCaptureModule, eventEmitter
    )

    private fun createActionSubscribeBarcodeTracking(): Action = ActionSubscribeBarcodeTracking(
        barcodeTrackingModule, eventEmitter
    )

    private fun createActionUnregisterBarcodeTrackingListener(): Action = ActionUnregisterBarcodeTrackingListener(
        barcodeTrackingModule, eventEmitter
    )

    private fun createActionSubscribeBarcodeSelection(): Action = ActionSubscribeBarcodeSelection(
        barcodeSelectionModule, eventEmitter
    )

    private fun createActionUnsubscribeBarcodeSelection(): Action = ActionUnsubscribeBarcodeSelection(
        barcodeSelectionModule, eventEmitter
    )

    private fun createActionSubscribeBasicOverlay(): Action = ActionSubscribeBasicOverlay(
        barcodeTrackingModule, eventEmitter
    )

    private fun createActionUnregisterBasicOverlayListener(): Action = ActionUnregisterBasicOverlayListener(
        barcodeTrackingModule, eventEmitter
    )

    private fun createActionSubscribeAdvancedOverlay(): Action = ActionSubscribeAdvancedOverlay(
        barcodeTrackingModule, eventEmitter
    )

    private fun createActionUnregisterAdvancedOverlayListener(): Action = ActionUnregisterAdvancedOverlayListener(
        barcodeTrackingModule, eventEmitter
    )

    private fun createActionSetBrushForTrackedBarcode(): Action = ActionSetBrushForTrackedBarcode(
        barcodeTrackingModule
    )

    private fun createActionClearTrackedBarcodeBrushes(): Action = ActionClearTrackedBarcodeBrushes(
        barcodeTrackingModule
    )

    private fun createActionSetViewForTrackedBarcode(): Action = ActionSetViewForTrackedBarcode(
        barcodeTrackingModule
    )

    private fun createActionSetOffsetForTrackedBarcode(): Action = ActionSetOffsetForTrackedBarcode(
        barcodeTrackingModule
    )

    private fun createActionSetAnchorForTrackedBarcode(): Action = ActionSetAnchorForTrackedBarcode(
        barcodeTrackingModule
    )

    private fun createActionClearTrackedBarcodeViews(): Action = ActionClearTrackedBarcodeViews(
        barcodeTrackingModule
    )

    private fun createActionResetBarcodeSelection(): Action = ActionResetBarcodeSelection(
        barcodeSelectionModule
    )

    private fun createActionGetCountForBarcodeInBarcodeSelectionSession(): Action =
        ActionGetCountForBarcodeInBarcodeSelectionSession(
            barcodeSelectionModule
        )

    private fun createActionResetBarcodeCaptureSession(): Action = ActionResetBarcodeCaptureSession(
        barcodeCaptureModule
    )

    private fun createActionResetBarcodeTrackingSession(): Action =
        ActionResetBarcodeTrackingSession(
            barcodeTrackingModule
        )

    private fun createActionResetBarcodeSelectionSession(): Action =
        ActionResetBarcodeSelectionSession(
            barcodeSelectionModule
        )

    private fun createActionUnfreezeCameraInBarcodeSelection(): Action =
        ActionUnfreezeCameraInBarcodeSelection(
            barcodeSelectionModule
        )

    private fun createActionSelectAimedBarcode(): Action = 
        ActionSelectAimedBarcode(barcodeSelectionModule)

    private fun createActionIncreaseCountForBarcodesInBarcodeSelection(): Action = 
        ActionIncreaseCountForBarcodesInBarcodeSelection(barcodeSelectionModule)

    private fun createActionUnselectBarcodes(): Action = 
        ActionUnselectBarcodes(barcodeSelectionModule)

    private fun createSetSelectBarcodeEnabled(): Action = 
        ActionSetSelectBarcodeEnabled(barcodeSelectionModule)

    private fun createActionCreateFindView(): Action =
        ActionCreateFindView(barcodeFindModule, barcodeFindViewHandler)

    private fun createActionUpdateFindView(): Action =
        ActionUpdateFindView(barcodeFindModule)

    private fun createActionUpdateFindMode(): Action =
        ActionUpdateFindMode(barcodeFindModule)

    private fun createActionRegisterBarcodeFindListener(): Action =
        ActionRegisterBarcodeFindListener(barcodeFindModule, eventEmitter)

    private fun createActionUnregisterBarcodeFindListener(): Action =
        ActionUnregisterBarcodeFindListener(barcodeFindModule, eventEmitter)

    private fun createActionRegisterBarcodeFindViewListener(): Action =
        ActionRegisterBarcodeFindViewListener(barcodeFindModule, eventEmitter)

    private fun createActionUnregisterBarcodeFindViewListener(): Action =
        ActionUnregisterBarcodeFindViewListener(barcodeFindModule, eventEmitter)

    private fun createActionFindViewOnPause(): Action =
        ActionFindViewOnPause(barcodeFindModule)

    private fun createActionFindViewOnResume(): Action =
        ActionFindViewOnResume(barcodeFindModule)

    private fun createActionFindSetItemList(): Action =
        ActionFindSetItemList(barcodeFindModule)

    private fun createActionFindViewPauseSearching(): Action =
        ActionFindViewPauseSearching(barcodeFindModule)

    private fun createActionFindViewStartSearching(): Action =
        ActionFindViewStartSearching(barcodeFindModule)

    private fun createActionFindViewStopSearching(): Action =
        ActionFindViewStopSearching(barcodeFindModule)

    private fun createActionFindModePause(): Action =
        ActionFindModePause(barcodeFindModule)

    private fun createActionFindModeStart(): Action =
        ActionFindModeStart(barcodeFindModule)

    private fun createActionFindModeStop(): Action =
        ActionFindModeStop(barcodeFindModule)

    private fun createActionFindViewShow(): Action =
        ActionFindViewShow(barcodeFindViewHandler)

    private fun createActionFindViewHide(): Action =
        ActionFindViewHide(barcodeFindViewHandler)

    private fun createActionSetBarcodeCaptureModeEnabledState(): Action =
        ActionSetBarcodeCaptureModeEnabledState(barcodeCaptureModule)

    private fun createActionSetBarcodeSelectionModeEnabledState(): Action =
        ActionSetBarcodeSelectionModeEnabledState(barcodeSelectionModule)

    private fun createActionSetBarcodeTrackingModeEnabledState(): Action =
        ActionSetBarcodeTrackingModeEnabledState(barcodeTrackingModule)

    private fun createActionUpdateBCOverlay(): Action =
        ActionUpdateBarcodeCaptureOverlay(barcodeCaptureModule)

    private fun createActionUpdateBCMode(): Action =
        ActionUpdateBarcodeCaptureMode(barcodeCaptureModule)

    private fun createActionApplyBCModeSettings(): Action =
        ActionApplyBarcodeCaptureModeSettings(barcodeCaptureModule)

    private fun createActionUpdateBSBasicOverlay(): Action =
        ActionUpdateBarcodeSelectionBasicOverlay(barcodeSelectionModule)

    private fun createActionUpdateBSMode(): Action =
        ActionUpdateBarcodeSelectionMode(barcodeSelectionModule)

    private fun createActionApplyBSModeSettings(): Action =
        ActionApplyBarcodeSelectionModeSettings(barcodeSelectionModule)

    private fun createActionSetTextForAimToSelectAutoHint(): Action =
        ActionSetTextForAimToSelectAutoHint(barcodeSelectionModule)

    private fun createActionRemoveAimedBarcodeBrushProvider(): Action =
        ActionRemoveAimedBarcodeBrushProvider(barcodeSelectionModule, eventEmitter)

    private fun createActionSetAimedBarcodeBrushProvider(): Action =
        ActionSetAimedBarcodeBrushProvider(barcodeSelectionModule, eventEmitter)

    private fun createActionFinishBrushForAimedBarcodeCallback(): Action =
        ActionFinishBrushForAimedBarcodeCallback(barcodeSelectionModule)

    private fun createActionRemoveTrackedBarcodeBrushProvider(): Action =
        ActionRemoveTrackedBarcodeBrushProvider(barcodeSelectionModule, eventEmitter)

    private fun createActionSetTrackedBarcodeBrushProvider(): Action =
        ActionSetTrackedBarcodeBrushProvider(barcodeSelectionModule, eventEmitter)

    private fun createActionFinishBrushForTrackedBarcodeCallback(): Action =
        ActionFinishBrushForTrackedBarcodeCallback(barcodeSelectionModule)

    private fun createActionUpdateBarcodeTrackingBasicOverlay(): Action =
        ActionUpdateBarcodeTrackingBasicOverlay(barcodeTrackingModule)

    private fun createActionUpdateBarcodeTrackingAdvancedOverlay(): Action =
        ActionUpdateBarcodeTrackingAdvancedOverlay(barcodeTrackingModule)

    private fun createActionUpdateBarcodeTrackingMode(): Action =
        ActionUpdateBarcodeTrackingMode(barcodeTrackingModule)

    private fun createActionApplyBarcodeTrackingModeSettings(): Action =
        ActionApplyBarcodeTrackingModeSettings(barcodeTrackingModule)

    companion object {
        private const val INJECT_DEFAULTS = "getDefaults"
        private const val SUBSCRIBE_BARCODE_CAPTURE = "subscribeBarcodeCaptureListener"
        private const val UNSUBSCRIBE_BARCODE_CAPTURE = "unsubscribeBarcodeCaptureListener"
        private const val SUBSCRIBE_BARCODE_TRACKING = "subscribeBarcodeTrackingListener"
        private const val UNREGISTER_BARCODE_TRACKING = "unregisterBarcodeTrackingListener"
        private const val SUBSCRIBE_BARCODE_SELECTION = "subscribeBarcodeSelectionListener"
        private const val UNSUBSCRIBE_BARCODE_SELECTION = "unsubscribeBarcodeSelectionListener"

        private const val FINISH_BARCODE_CAPTURE_DID_UPDATE_SESSION =
            "finishBarcodeCaptureDidUpdateSession"
        private const val FINISH_BARCODE_CAPTURE_DID_CAPTURE = "finishBarcodeCaptureDidScan"

        private const val FINISH_BARCODE_SELECTION_DID_UPDATE_SELECTION =
            "finishBarcodeSelectionDidUpdateSelection"
        private const val FINISH_BARCODE_SELECTION_DID_UPDATE_SESSION =
            "finishBarcodeSelectionDidUpdateSession"

        private const val FINISH_BARCODE_TRACKING_DID_UPDATE_SESSION =
            "finishBarcodeTrackingDidUpdateSession"

        private const val SUBSCRIBE_BASIC_OVERLAY_LISTENER =
            "subscribeBarcodeTrackingBasicOverlayListener"
        private const val UNREGISTER_BASIC_OVERLAY_LISTENER = "unregisterBarcodeTrackingBasicOverlayListener"
        private const val SET_BRUSH_FOR_TRACKED_BARCODE = "setBrushForTrackedBarcode"
        private const val CLEAR_TRACKED_BARCODE_BRUSHES = "clearTrackedBarcodeBrushes"

        private const val SUBSCRIBE_ADVANCED_OVERLAY_LISTENER =
            "subscribeBarcodeTrackingAdvancedOverlayListener"
        private const val UNREGISTER_ADVANCED_OVERLAY_LISTENER = 
            "unregisterBarcodeTrackingAdvancedOverlayListener"
        const val SET_VIEW_FOR_TRACKED_BARCODE = "setViewForTrackedBarcode"
        const val SET_OFFSET_FOR_TRACKED_BARCODE = "setOffsetForTrackedBarcode"
        const val SET_ANCHOR_FOR_TRACKED_BARCODE = "setAnchorForTrackedBarcode"
        const val CLEAR_TRACKED_BARCODE_VIEWS = "clearTrackedBarcodeViews"

        const val ACTION_GET_COUNT_FOR_BARCODE_IN_BARCODE_SELECTION_SESSION =
            "getCountForBarcodeInBarcodeSelectionSession"
        const val ACTION_RESET_BARCODE_CAPTURE_SESSION = "resetBarcodeCaptureSession"
        const val ACTION_RESET_BARCODE_TRACKING_SESSION = "resetBarcodeTrackingSession"
        const val ACTION_RESET_BARCODE_SELECTION_SESSION = "resetBarcodeSelectionSession"
        const val ACTION_RESET_BARCODE_SELECTION = "resetBarcodeSelection"
        const val ACTION_UNFREEZE_CAMERA_IN_BARCODE_SELECTION = "unfreezeCameraInBarcodeSelection"
        const val SELECT_AIMED_BARCODE = "selectAimedBarcode"
        const val INCREASE_COUNT_FOR_BARCODES_IN_BARCODE_SELECTION = "increaseCountForBarcodes"
        const val UNSELECT_BARCODES = "unselectBarcodes"
        const val SET_SELECT_BARCODE_ENABLED = "setSelectBarcodeEnabled"

        const val ACTION_CREATE_FIND_VIEW = "createFindView"
        const val ACTION_UPDATE_FIND_VIEW = "updateFindView"
        const val ACTION_UPDATE_FIND_MODE = "updateFindMode"
        const val ACTION_REGISTER_FIND_LISTENER = "registerBarcodeFindListener"
        const val ACTION_UNREGISTER_FIND_LISTENER = "unregisterBarcodeFindListener"
        const val ACTION_REGISTER_FIND_VIEW_LISTENER = "registerBarcodeFindViewListener"
        const val ACTION_UNREGISTER_FIND_VIEW_LISTENER = "unregisterBarcodeFindViewListener"
        const val ACTION_FIND_VIEW_ON_PAUSE = "barcodeFindViewOnPause"
        const val ACTION_FIND_VIEW_ON_RESUME = "barcodeFindViewOnResume"
        const val ACTION_FIND_SET_ITEM_LIST = "barcodeFindSetItemList"
        const val ACTION_FIND_VIEW_STOP_SEARCHING = "barcodeFindViewStopSearching"
        const val ACTION_FIND_VIEW_START_SEARCHING = "barcodeFindViewStartSearching"
        const val ACTION_FIND_VIEW_PAUSE_SEARCHING = "barcodeFindViewPauseSearching"
        const val ACTION_FIND_MODE_START = "barcodeFindModeStart"
        const val ACTION_FIND_MODE_PAUSE = "barcodeFindModePause"
        const val ACTION_FIND_MODE_STOP = "barcodeFindModeStop"
        const val ACTION_FIND_VIEW_SHOW = "showFindView"
        const val ACTION_FIND_VIEW_HIDE = "hideFindView"

        private const val ACTION_UPDATE_BC_OVERLAY = "updateBarcodeCaptureOverlay"
        private const val ACTION_UPDATE_BC_MODE = "updateBarcodeCaptureMode"
        private const val ACTION_APPLY_BC_MODE_SETTINGS = "applyBarcodeCaptureModeSettings"
        private const val ACTION_UPDATE_BS_BASIC_OVERLAY = "updateBarcodeSelectionBasicOverlay"
        private const val ACTION_UPDATE_BS_MODE = "updateBarcodeSelectionMode"
        private const val ACTION_APPLY_BS_MODE_SETTINGS = "applyBarcodeSelectionModeSettings"

        private const val ACTION_SET_TEXT_FOR_AIM_TO_SELECT_AUTO_HINT =
            "setTextForAimToSelectAutoHint"
        private const val ACTION_REMOVE_AIMED_BARCODE_BRUSH_PROVIDER =
            "removeAimedBarcodeBrushProvider"
        private const val ACTION_SET_AIMED_BARCODE_RUSH_PROVIDER =
            "setAimedBarcodeBrushProvider"
        private const val ACTION_FINISH_BRUSH_FOR_AIMED_BARCODE_CALLBACK =
            "finishBrushForAimedBarcodeCallback"
        private const val ACTION_REMOVE_TRACKED_BARCODE_BRUSH_PROVIDER =
            "removeTrackedBarcodeBrushProvider"
        private const val ACTION_SET_TRACKED_BARCODE_BRUSH_PROVIDER =
            "setTrackedBarcodeBrushProvider"
        private const val ACTION_FINISH_BRUSH_FOR_TRACKED_BARCODE_CALLBACK =
            "finishBrushForTrackedBarcodeCallback"

        const val ACTION_SET_BARCODE_CAPTURE_MODE_ENABLED_STATE =
            "setBarcodeCaptureModeEnabledState"
        const val ACTION_SET_BARCODE_SELECTION_MODE_ENABLED_STATE =
            "setBarcodeSelectionModeEnabledState"
        const val ACTION_SET_BARCODE_TRACKING_MODE_ENABLED_STATE =
            "setBarcodeTrackingModeEnabledState"

        private const val ACTION_UPDATE_BT_ADVANCED_OVERLAY = "updateBarcodeTrackingAdvancedOverlay"
        private const val ACTION_UPDATE_BT_BASIC_OVERLAY = "updateBarcodeTrackingBasicOverlay"
        private const val ACTION_UPDATE_BT_MODE = "updateBarcodeTrackingMode"
        private const val ACTION_APPLY_BT_MODE_SETTINGS = "applyBarcodeTrackingModeSettings"

        private val ACTIONS_REQUIRING_CAMERA =
            setOf(
                ACTION_APPLY_BC_MODE_SETTINGS,
                ACTION_APPLY_BS_MODE_SETTINGS,
                ACTION_APPLY_BT_MODE_SETTINGS,
                ACTION_UPDATE_BC_MODE,
                ACTION_UPDATE_BS_MODE,
                ACTION_UPDATE_BT_MODE,
                ACTION_UPDATE_BS_BASIC_OVERLAY,
                ACTION_UPDATE_BC_OVERLAY,
                ACTION_UPDATE_BT_ADVANCED_OVERLAY,
                ACTION_UPDATE_BT_BASIC_OVERLAY
            )
    }
}
