export interface BlockingModeListenerResult {
    enabled: boolean;
}
export declare const Cordova: {
    pluginName: string;
    defaults: {};
    exec: (success: Function | null, error: Function | null, functionName: string, args: [any] | null) => void;
};
export declare function initializeBarcodeCordova(): void;
export declare enum CordovaFunction {
    SubscribeBarcodeCaptureListener = "subscribeBarcodeCaptureListener",
    UnsubscribeBarcodeCaptureListener = "unsubscribeBarcodeCaptureListener",
    FinishBarcodeCaptureDidScan = "finishBarcodeCaptureDidScan",
    FinishBarcodeCaptureDidUpdateSession = "finishBarcodeCaptureDidUpdateSession",
    SetBarcodeCaptureModeEnabledState = "setBarcodeCaptureModeEnabledState",
    SetBarcodeSelectionModeEnabledState = "setBarcodeSelectionModeEnabledState",
    SubscribeBarcodeSelectionListener = "subscribeBarcodeSelectionListener",
    UnsubscribeBarcodeSelectionListener = "unsubscribeBarcodeSelectionListener",
    GetCountForBarcodeInBarcodeSelectionSession = "getCountForBarcodeInBarcodeSelectionSession",
    ResetBarcodeCaptureSession = "resetBarcodeCaptureSession",
    ResetBarcodeSelectionSession = "resetBarcodeSelectionSession",
    ResetBarcodeSelection = "resetBarcodeSelection",
    UnfreezeCameraInBarcodeSelection = "unfreezeCameraInBarcodeSelection",
    SelectAimedBarcode = "selectAimedBarcode",
    IncreaseCountForBarcodes = "increaseCountForBarcodes",
    UnselectBarcodes = "unselectBarcodes",
    SetSelectBarcodeEnabled = "setSelectBarcodeEnabled",
    FinishBarcodeSelectionDidUpdateSelection = "finishBarcodeSelectionDidUpdateSelection",
    FinishBarcodeSelectionDidUpdateSession = "finishBarcodeSelectionDidUpdateSession",
    UpdateBarcodeSelectionBasicOverlay = "updateBarcodeSelectionBasicOverlay",
    UpdateBarcodeSelectionMode = "updateBarcodeSelectionMode",
    ApplyBarcodeSelectionModeSettings = "applyBarcodeSelectionModeSettings",
    UpdateBarcodeCaptureOverlay = "updateBarcodeCaptureOverlay",
    UpdateBarcodeCaptureMode = "updateBarcodeCaptureMode",
    ApplyBarcodeCaptureModeSettings = "applyBarcodeCaptureModeSettings",
    SetTextForAimToSelectAutoHint = "setTextForAimToSelectAutoHint",
    RemoveAimedBarcodeBrushProvider = "removeAimedBarcodeBrushProvider",
    SetAimedBarcodeBrushProvider = "setAimedBarcodeBrushProvider",
    FinishBrushForAimedBarcodeCallback = "finishBrushForAimedBarcodeCallback",
    RemoveTrackedBarcodeBrushProvider = "removeTrackedBarcodeBrushProvider",
    SetTrackedBarcodeBrushProvider = "setTrackedBarcodeBrushProvider",
    FinishBrushForTrackedBarcodeCallback = "finishBrushForTrackedBarcodeCallback",
    SetViewForTrackedBarcode = "setViewForTrackedBarcode",
    SetAnchorForTrackedBarcode = "setAnchorForTrackedBarcode",
    SetOffsetForTrackedBarcode = "setOffsetForTrackedBarcode",
    ClearTrackedBarcodeViews = "clearTrackedBarcodeViews",
    SetBrushForTrackedBarcode = "setBrushForTrackedBarcode",
    ClearTrackedBarcodeBrushes = "clearTrackedBarcodeBrushes",
    SetBarcodeTrackingModeEnabledState = "setBarcodeTrackingModeEnabledState",
    SubscribeBarcodeTrackingListener = "subscribeBarcodeTrackingListener",
    UnregisterBarcodeTrackingListener = "unregisterBarcodeTrackingListener",
    SubscribeBarcodeTrackingAdvancedOverlayListener = "subscribeBarcodeTrackingAdvancedOverlayListener",
    SubscribeBarcodeTrackingBasicOverlayListener = "subscribeBarcodeTrackingBasicOverlayListener",
    ResetBarcodeTrackingSession = "resetBarcodeTrackingSession",
    UpdateBarcodeTrackingAdvancedOverlay = "updateBarcodeTrackingAdvancedOverlay",
    UpdateBarcodeTrackingBasicOverlay = "updateBarcodeTrackingBasicOverlay",
    UpdateBarcodeTrackingMode = "updateBarcodeTrackingMode",
    ApplyBarcodeTrackingModeSettings = "applyBarcodeTrackingModeSettings",
    FinishBarcodeTrackingDidUpdateSession = "finishBarcodeTrackingDidUpdateSession",
    FinishBarcodeTrackingBrushForTrackedBarcode = "finishBarcodeTrackingBrushForTrackedBarcode",
    UnregisterBarcodeTrackingAdvancedOverlayListener = "unregisterBarcodeTrackingAdvancedOverlayListener",
    UnregisterBarcodeTrackingBasicOverlayListener = "unregisterBarcodeTrackingBasicOverlayListener"
}
