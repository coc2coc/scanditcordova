var barcode = require('scandit-cordova-datacapture-barcode.Barcode');
var scanditCordovaDatacaptureCore = require('scandit-cordova-datacapture-core.Core');
var scanditDatacaptureFrameworksCore = require('scandit-cordova-datacapture-core.Core');

var BarcodeCaptureListenerEvent;
(function (BarcodeCaptureListenerEvent) {
    BarcodeCaptureListenerEvent["DidScan"] = "BarcodeCaptureListener.didScan";
    BarcodeCaptureListenerEvent["DidUpdateSession"] = "BarcodeCaptureListener.didUpdateSession";
})(BarcodeCaptureListenerEvent || (BarcodeCaptureListenerEvent = {}));
class NativeBarcodeCaptureListenerProxy extends scanditDatacaptureFrameworksCore.BaseNativeProxy {
    constructor() {
        super(...arguments);
        this.isModeEnabled = () => false;
    }
    static get cordovaExec() {
        return Cordova.exec;
    }
    resetSession() {
        return new Promise((resolve, reject) => {
            NativeBarcodeCaptureListenerProxy.cordovaExec(resolve, reject, CordovaFunction.ResetBarcodeCaptureSession, null);
        });
    }
    registerListenerForEvents() {
        NativeBarcodeCaptureListenerProxy.cordovaExec(this.notifyListeners.bind(this), null, CordovaFunction.SubscribeBarcodeCaptureListener, null);
    }
    setModeEnabledState(enabled) {
        NativeBarcodeCaptureListenerProxy.cordovaExec(null, null, CordovaFunction.SetBarcodeCaptureModeEnabledState, [{ 'enabled': enabled }]);
    }
    unregisterListenerForEvents() {
        NativeBarcodeCaptureListenerProxy.cordovaExec(null, null, CordovaFunction.UnsubscribeBarcodeCaptureListener, null);
    }
    finishDidUpdateSessionCallback(isFinished) {
        NativeBarcodeCaptureListenerProxy.cordovaExec(null, null, CordovaFunction.FinishBarcodeCaptureDidUpdateSession, [{ 'enabled': isFinished }]);
    }
    finishDidScanCallback(isFinished) {
        NativeBarcodeCaptureListenerProxy.cordovaExec(null, null, CordovaFunction.FinishBarcodeCaptureDidScan, [{ 'enabled': isFinished }]);
    }
    updateBarcodeCaptureMode(modeJson) {
        return new Promise((resolve, reject) => {
            NativeBarcodeCaptureListenerProxy.cordovaExec(resolve, reject, CordovaFunction.UpdateBarcodeCaptureMode, [modeJson]);
        });
    }
    applyBarcodeCaptureModeSettings(newSettingsJson) {
        return new Promise((resolve, reject) => {
            NativeBarcodeCaptureListenerProxy.cordovaExec(resolve, reject, CordovaFunction.ApplyBarcodeCaptureModeSettings, [newSettingsJson]);
        });
    }
    updateBarcodeCaptureOverlay(overlayJson) {
        return new Promise((resolve, reject) => {
            NativeBarcodeCaptureListenerProxy.cordovaExec(resolve, reject, CordovaFunction.UpdateBarcodeCaptureOverlay, [overlayJson]);
        });
    }
    emitInCallback(enabled) {
        this.eventEmitter.emit(barcode.BarcodeCaptureListenerEvents.inCallback, enabled);
    }
    notifyListeners(event) {
        const done = () => {
            this.emitInCallback(false);
            return { enabled: this.isModeEnabled() };
        };
        this.emitInCallback(true);
        if (!event) {
            // The event could be undefined/null in case the plugin result did not pass a "message",
            // which could happen e.g. in case of "ok" results, which could signal e.g. successful
            // listener subscriptions.
            return done();
        }
        switch (event.name) {
            case BarcodeCaptureListenerEvent.DidScan:
                this.eventEmitter.emit(barcode.BarcodeCaptureListenerEvents.didScan, JSON.stringify(event.argument));
                break;
            case BarcodeCaptureListenerEvent.DidUpdateSession:
                this.eventEmitter.emit(barcode.BarcodeCaptureListenerEvents.didUpdateSession, JSON.stringify(event.argument));
                break;
        }
        return done();
    }
}

var BarcodeSelectionListenerEvent;
(function (BarcodeSelectionListenerEvent) {
    BarcodeSelectionListenerEvent["DidUpdateSelection"] = "BarcodeSelectionListener.didUpdateSelection";
    BarcodeSelectionListenerEvent["DidUpdateSession"] = "BarcodeSelectionListener.didUpdateSession";
})(BarcodeSelectionListenerEvent || (BarcodeSelectionListenerEvent = {}));
class NativeBarcodeSelectionListenerProxy extends scanditDatacaptureFrameworksCore.BaseNativeProxy {
    static get cordovaExec() {
        return Cordova.exec;
    }
    getCount(selectionIdentifier) {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionListenerProxy.cordovaExec(resolve, reject, CordovaFunction.GetCountForBarcodeInBarcodeSelectionSession, [selectionIdentifier]);
        });
    }
    resetSession() {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionListenerProxy.cordovaExec(resolve, reject, CordovaFunction.ResetBarcodeSelectionSession, null);
        });
    }
    registerListenerForEvents() {
        NativeBarcodeSelectionListenerProxy.cordovaExec(this.notifyListeners.bind(this), null, CordovaFunction.SubscribeBarcodeSelectionListener, null);
    }
    finishDidUpdateSelectionCallback(isEnabled) {
        NativeBarcodeSelectionListenerProxy.cordovaExec(null, null, CordovaFunction.FinishBarcodeSelectionDidUpdateSelection, [{ 'enabled': isEnabled }]);
    }
    finishDidUpdateSessionCallback(isEnabled) {
        NativeBarcodeSelectionListenerProxy.cordovaExec(null, null, CordovaFunction.FinishBarcodeSelectionDidUpdateSession, [{ 'enabled': isEnabled }]);
    }
    unregisterListenerForEvents() {
        NativeBarcodeSelectionListenerProxy.cordovaExec(null, null, CordovaFunction.UnsubscribeBarcodeSelectionListener, null);
    }
    notifyListeners(event) {
        const done = () => {
            this.eventEmitter.emit(barcode.BarcodeSelectionListenerEvents.inCallback, false);
            return { enabled: this.isModeEnabled() };
        };
        this.eventEmitter.emit(barcode.BarcodeSelectionListenerEvents.inCallback, true);
        if (!event) {
            // The event could be undefined/null in case the plugin result did not pass a "message",
            // which could happen e.g. in case of "ok" results, which could signal e.g. successful
            // listener subscriptions.
            return done();
        }
        switch (event.name) {
            case BarcodeSelectionListenerEvent.DidUpdateSelection:
                this.eventEmitter.emit(barcode.BarcodeSelectionListenerEvents.didUpdateSelection, JSON.stringify(event.argument));
                break;
            case BarcodeSelectionListenerEvent.DidUpdateSession:
                this.eventEmitter.emit(barcode.BarcodeSelectionListenerEvents.didUpdateSession, JSON.stringify(event.argument));
                break;
        }
        return done();
    }
}

class NativeBarcodeSelectionProxy extends scanditDatacaptureFrameworksCore.BaseNativeProxy {
    static get cordovaExec() {
        return Cordova.exec;
    }
    unfreezeCamera() {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionProxy.cordovaExec(resolve, reject, CordovaFunction.UnfreezeCameraInBarcodeSelection, null);
        });
    }
    resetMode() {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionProxy.cordovaExec(resolve, reject, CordovaFunction.ResetBarcodeSelection, null);
        });
    }
    selectAimedBarcode() {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionProxy.cordovaExec(resolve, reject, CordovaFunction.SelectAimedBarcode, null);
        });
    }
    unselectBarcodes(barcodesStr) {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionProxy.cordovaExec(resolve, reject, CordovaFunction.UnselectBarcodes, [barcodesStr]);
        });
    }
    setSelectBarcodeEnabled(barcodeStr, enabled) {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionProxy.cordovaExec(resolve, reject, CordovaFunction.SetSelectBarcodeEnabled, [[{ barcode: barcodeStr, enabled }]]);
        });
    }
    increaseCountForBarcodes(barcodeStr) {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionProxy.cordovaExec(resolve, reject, CordovaFunction.IncreaseCountForBarcodes, [barcodeStr]);
        });
    }
    setModeEnabledState(enabled) {
        NativeBarcodeSelectionProxy.cordovaExec(null, null, CordovaFunction.SetBarcodeSelectionModeEnabledState, [{ 'enabled': enabled }]);
    }
    updateBarcodeSelectionMode(modeJson) {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionProxy.cordovaExec(resolve, reject, CordovaFunction.UpdateBarcodeSelectionMode, [modeJson]);
        });
    }
    applyBarcodeSelectionModeSettings(newSettingsJson) {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionProxy.cordovaExec(resolve, reject, CordovaFunction.ApplyBarcodeSelectionModeSettings, [newSettingsJson]);
        });
    }
}

class NativeBarcodeSelectionOverlayProxy extends scanditDatacaptureFrameworksCore.BaseNativeProxy {
    static get cordovaExec() {
        return Cordova.exec;
    }
    setTextForAimToSelectAutoHint(text) {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.SetTextForAimToSelectAutoHint, [{ 'text': text }]);
        });
    }
    removeAimedBarcodeBrushProvider() {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.RemoveAimedBarcodeBrushProvider, null);
        });
    }
    setAimedBarcodeBrushProvider() {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.SetAimedBarcodeBrushProvider, null);
        });
    }
    finishBrushForAimedBarcodeCallback(brushStr, selectionIdentifier) {
        NativeBarcodeSelectionOverlayProxy.cordovaExec(null, null, CordovaFunction.FinishBrushForAimedBarcodeCallback, [{ 'brush': brushStr, 'selectionIdentifier': selectionIdentifier }]);
    }
    removeTrackedBarcodeBrushProvider() {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.RemoveTrackedBarcodeBrushProvider, null);
        });
    }
    setTrackedBarcodeBrushProvider() {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.SetTrackedBarcodeBrushProvider, null);
        });
    }
    finishBrushForTrackedBarcodeCallback(brushStr, selectionIdentifier) {
        NativeBarcodeSelectionOverlayProxy.cordovaExec(null, null, CordovaFunction.FinishBrushForTrackedBarcodeCallback, [{ 'brush': brushStr, 'selectionIdentifier': selectionIdentifier }]);
    }
    updateBarcodeSelectionBasicOverlay(overlayJson) {
        return new Promise((resolve, reject) => {
            NativeBarcodeSelectionOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.UpdateBarcodeSelectionBasicOverlay, [overlayJson]);
        });
    }
    subscribeBrushForTrackedBarcode() {
        // setTrackedBarcodeBrushProvider is also subscribing for events on Cordova
    }
    subscribeBrushForAimedBarcode() {
        // setAimedBarcodeBrushProvider is also subscribing for events on Cordova
    }
    notifyListeners(event) {
        const done = () => {
            this.eventEmitter.emit(barcode.BarcodeSelectionBrushProviderEvents.inCallback, false);
            return { enabled: this.isModeEnabled() };
        };
        this.eventEmitter.emit(barcode.BarcodeSelectionBrushProviderEvents.inCallback, true);
        if (!event) {
            // The event could be undefined/null in case the plugin result did not pass a "message",
            // which could happen e.g. in case of "ok" results, which could signal e.g. successful
            // listener subscriptions.
            return done();
        }
        switch (event.name) {
            case barcode.BarcodeSelectionBrushProviderEvents.brushForAimedBarcode:
                this.eventEmitter.emit(barcode.BarcodeSelectionBrushProviderEvents.brushForAimedBarcode, JSON.stringify(event.argument));
                break;
            case barcode.BarcodeSelectionBrushProviderEvents.brushForTrackedBarcode:
                this.eventEmitter.emit(barcode.BarcodeSelectionBrushProviderEvents.brushForAimedBarcode, JSON.stringify(event.argument));
                break;
        }
        return done();
    }
}

var BarcodeTrackingListenerEvent;
(function (BarcodeTrackingListenerEvent) {
    BarcodeTrackingListenerEvent["DidUpdateSession"] = "BarcodeTrackingListener.didUpdateSession";
})(BarcodeTrackingListenerEvent || (BarcodeTrackingListenerEvent = {}));
class NativeBarcodeTrackingListenerProxy extends scanditDatacaptureFrameworksCore.BaseNativeProxy {
    constructor() {
        super(...arguments);
        this.isModeEnabled = () => false;
    }
    static get cordovaExec() {
        return Cordova.exec;
    }
    resetSession() {
        return new Promise((resolve, reject) => {
            NativeBarcodeTrackingListenerProxy.cordovaExec(resolve, reject, CordovaFunction.ResetBarcodeTrackingSession, null);
        });
    }
    registerListenerForEvents() {
        NativeBarcodeTrackingListenerProxy.cordovaExec(this.notifyListeners.bind(this), null, CordovaFunction.SubscribeBarcodeTrackingListener, null);
    }
    unregisterListenerForEvents() {
        NativeBarcodeTrackingListenerProxy.cordovaExec(this.notifyListeners.bind(this), null, CordovaFunction.UnregisterBarcodeTrackingListener, null);
    }
    setModeEnabledState(enabled) {
        NativeBarcodeTrackingListenerProxy.cordovaExec(null, null, CordovaFunction.SetBarcodeTrackingModeEnabledState, [{ 'enabled': enabled }]);
    }
    updateBarcodeTrackingMode(modeJson) {
        return new Promise((resolve, reject) => {
            NativeBarcodeTrackingListenerProxy.cordovaExec(resolve, reject, CordovaFunction.UpdateBarcodeTrackingMode, [modeJson]);
        });
    }
    applyBarcodeTrackingModeSettings(newSettingsJson) {
        return new Promise((resolve, reject) => {
            NativeBarcodeTrackingListenerProxy.cordovaExec(resolve, reject, CordovaFunction.ApplyBarcodeTrackingModeSettings, [newSettingsJson]);
        });
    }
    finishDidUpdateSessionCallback(enabled) {
        NativeBarcodeTrackingListenerProxy.cordovaExec(null, null, CordovaFunction.FinishBarcodeTrackingDidUpdateSession, [{ 'enabled': enabled }]);
    }
    notifyListeners(event) {
        const done = () => {
            this.eventEmitter.emit(barcode.BarcodeTrackingListenerEvents.inCallback, false);
            return { enabled: this.isModeEnabled() };
        };
        this.eventEmitter.emit(barcode.BarcodeTrackingListenerEvents.inCallback, true);
        if (!event) {
            // The event could be undefined/null in case the plugin result did not pass a "message",
            // which could happen e.g. in case of "ok" results, which could signal e.g. successful
            // listener subscriptions.
            return done();
        }
        switch (event.name) {
            case BarcodeTrackingListenerEvent.DidUpdateSession:
                this.eventEmitter.emit(barcode.BarcodeTrackingListenerEvents.didUpdateSession, JSON.stringify(event.argument));
                break;
        }
        return done();
    }
}

var BarcodeTrackingBasicOverlayListenerEvent;
(function (BarcodeTrackingBasicOverlayListenerEvent) {
    BarcodeTrackingBasicOverlayListenerEvent["BrushForTrackedBarcode"] = "BarcodeTrackingBasicOverlayListener.brushForTrackedBarcode";
    BarcodeTrackingBasicOverlayListenerEvent["DidTapTrackedBarcode"] = "BarcodeTrackingBasicOverlayListener.didTapTrackedBarcode";
})(BarcodeTrackingBasicOverlayListenerEvent || (BarcodeTrackingBasicOverlayListenerEvent = {}));
class NativeBarcodeTrackingBasicOverlayProxy extends scanditDatacaptureFrameworksCore.BaseNativeProxy {
    static get cordovaExec() {
        return Cordova.exec;
    }
    setBrushForTrackedBarcode(brushJson, trackedBarcodeIdentifier, sessionFrameSequenceID) {
        return new Promise((resolve, reject) => {
            NativeBarcodeTrackingBasicOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.SetBrushForTrackedBarcode, [{
                    brush: brushJson,
                    sessionFrameSequenceID: sessionFrameSequenceID,
                    trackedBarcodeID: trackedBarcodeIdentifier,
                }]);
        });
    }
    clearTrackedBarcodeBrushes() {
        return new Promise((resolve, reject) => {
            NativeBarcodeTrackingBasicOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.ClearTrackedBarcodeBrushes, null);
        });
    }
    registerListenerForBasicOverlayEvents() {
        NativeBarcodeTrackingBasicOverlayProxy.cordovaExec(this.notifyListeners.bind(this), null, CordovaFunction.SubscribeBarcodeTrackingBasicOverlayListener, null);
    }
    updateBarcodeTrackingBasicOverlay(overlayJson) {
        return new Promise((resolve, reject) => {
            NativeBarcodeTrackingBasicOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.UpdateBarcodeTrackingBasicOverlay, [overlayJson]);
        });
    }
    unregisterListenerForBasicOverlayEvents() {
        return new Promise((resolve, reject) => {
            NativeBarcodeTrackingBasicOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.UnregisterBarcodeTrackingBasicOverlayListener, null);
        });
    }
    notifyListeners(event) {
        if (!event) {
            // The event could be undefined/null in case the plugin result did not pass a "message",
            // which could happen e.g. in case of "ok" results, which could signal e.g. successful
            // listener subscriptions.
            return null;
        }
        switch (event.name) {
            case BarcodeTrackingBasicOverlayListenerEvent.BrushForTrackedBarcode:
                this.eventEmitter.emit(barcode.BarcodeTrackingBasicOverlayListenerEvents.brushForTrackedBarcode, JSON.stringify(event.argument));
                break;
            case BarcodeTrackingBasicOverlayListenerEvent.DidTapTrackedBarcode:
                this.eventEmitter.emit(barcode.BarcodeTrackingBasicOverlayListenerEvents.didTapTrackedBarcode, JSON.stringify(event.argument));
                break;
        }
        return null;
    }
}

var BarcodeTrackingAdvancedOverlayListenerEvent;
(function (BarcodeTrackingAdvancedOverlayListenerEvent) {
    BarcodeTrackingAdvancedOverlayListenerEvent["ViewForTrackedBarcode"] = "BarcodeTrackingAdvancedOverlayListener.viewForTrackedBarcode";
    BarcodeTrackingAdvancedOverlayListenerEvent["AnchorForTrackedBarcode"] = "BarcodeTrackingAdvancedOverlayListener.anchorForTrackedBarcode";
    BarcodeTrackingAdvancedOverlayListenerEvent["OffsetForTrackedBarcode"] = "BarcodeTrackingAdvancedOverlayListener.offsetForTrackedBarcode";
    BarcodeTrackingAdvancedOverlayListenerEvent["DidTapViewForTrackedBarcode"] = "BarcodeTrackingAdvancedOverlayListener.didTapViewForTrackedBarcode";
})(BarcodeTrackingAdvancedOverlayListenerEvent || (BarcodeTrackingAdvancedOverlayListenerEvent = {}));
class NativeBarcodeTrackingAdvancedOverlayProxy extends scanditDatacaptureFrameworksCore.BaseNativeProxy {
    static get cordovaExec() {
        return Cordova.exec;
    }
    setBrushForTrackedBarcode(_brushJson, _sessionFrameSequenceID, _trackedBarcodeIdentifier) {
        return Promise.resolve();
    }
    setViewForTrackedBarcode(viewJson, trackedBarcodeIdentifier, sessionFrameSequenceID) {
        return new Promise((resolve, reject) => {
            NativeBarcodeTrackingAdvancedOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.SetViewForTrackedBarcode, [{
                    view: viewJson,
                    sessionFrameSequenceID: sessionFrameSequenceID === null || sessionFrameSequenceID === void 0 ? void 0 : sessionFrameSequenceID.toString(),
                    trackedBarcodeID: trackedBarcodeIdentifier.toString(),
                }]);
        });
    }
    setAnchorForTrackedBarcode(anchor, trackedBarcodeIdentifier, sessionFrameSequenceID) {
        return new Promise((resolve, reject) => {
            NativeBarcodeTrackingAdvancedOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.SetAnchorForTrackedBarcode, [{
                    anchor,
                    sessionFrameSequenceID: sessionFrameSequenceID === null || sessionFrameSequenceID === void 0 ? void 0 : sessionFrameSequenceID.toString(),
                    trackedBarcodeID: trackedBarcodeIdentifier.toString(),
                }]);
        });
    }
    setOffsetForTrackedBarcode(offsetJson, trackedBarcodeIdentifier, sessionFrameSequenceID) {
        return new Promise((resolve, reject) => {
            NativeBarcodeTrackingAdvancedOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.SetOffsetForTrackedBarcode, [{
                    offset: offsetJson,
                    sessionFrameSequenceID: sessionFrameSequenceID === null || sessionFrameSequenceID === void 0 ? void 0 : sessionFrameSequenceID.toString(),
                    trackedBarcodeID: trackedBarcodeIdentifier.toString(),
                }]);
        });
    }
    clearTrackedBarcodeViews() {
        return new Promise((resolve, reject) => {
            NativeBarcodeTrackingAdvancedOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.ClearTrackedBarcodeViews, null);
        });
    }
    registerListenerForAdvancedOverlayEvents() {
        NativeBarcodeTrackingAdvancedOverlayProxy.cordovaExec(this.notifyListeners.bind(this), null, CordovaFunction.SubscribeBarcodeTrackingAdvancedOverlayListener, null);
    }
    updateBarcodeTrackingAdvancedOverlay(overlayJson) {
        return new Promise((resolve, reject) => {
            NativeBarcodeTrackingAdvancedOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.UpdateBarcodeTrackingAdvancedOverlay, [overlayJson]);
        });
    }
    unregisterListenerForAdvancedOverlayEvents() {
        return new Promise((resolve, reject) => {
            NativeBarcodeTrackingAdvancedOverlayProxy.cordovaExec(resolve, reject, CordovaFunction.UnregisterBarcodeTrackingAdvancedOverlayListener, null);
        });
    }
    getJSONStringForView(view) {
        return view ? view.toJSON() : null;
    }
    notifyListeners(event) {
        if (!event) {
            // The event could be undefined/null in case the plugin result did not pass a "message",
            // which could happen e.g. in case of "ok" results, which could signal e.g. successful
            // listener subscriptions.
            return null;
        }
        switch (event.name) {
            case BarcodeTrackingAdvancedOverlayListenerEvent.ViewForTrackedBarcode:
                this.eventEmitter.emit(barcode.BarcodeTrackingAdvancedOverlayListenerEvents.viewForTrackedBarcode, JSON.stringify(event.argument));
                break;
            case BarcodeTrackingAdvancedOverlayListenerEvent.AnchorForTrackedBarcode:
                this.eventEmitter.emit(barcode.BarcodeTrackingAdvancedOverlayListenerEvents.anchorForTrackedBarcode, JSON.stringify(event.argument));
                break;
            case BarcodeTrackingAdvancedOverlayListenerEvent.OffsetForTrackedBarcode:
                this.eventEmitter.emit(barcode.BarcodeTrackingAdvancedOverlayListenerEvents.offsetForTrackedBarcode, JSON.stringify(event.argument));
                break;
            case BarcodeTrackingAdvancedOverlayListenerEvent.DidTapViewForTrackedBarcode:
                this.eventEmitter.emit(barcode.BarcodeTrackingAdvancedOverlayListenerEvents.didTapViewForTrackedBarcode, JSON.stringify(event.argument));
                break;
        }
        return null;
    }
}

function initBarcodeProxies() {
    scanditDatacaptureFrameworksCore.FactoryMaker.bindLazyInstance('BarcodeCaptureListenerProxy', () => new NativeBarcodeCaptureListenerProxy());
    scanditDatacaptureFrameworksCore.FactoryMaker.bindLazyInstance('BarcodeSelectionListenerProxy', () => new NativeBarcodeSelectionListenerProxy());
    scanditDatacaptureFrameworksCore.FactoryMaker.bindLazyInstance('BarcodeSelectionProxy', () => new NativeBarcodeSelectionProxy());
    scanditDatacaptureFrameworksCore.FactoryMaker.bindLazyInstance('BarcodeSelectionOverlayProxy', () => new NativeBarcodeSelectionOverlayProxy());
    scanditDatacaptureFrameworksCore.FactoryMaker.bindLazyInstance('BarcodeTrackingListenerProxy', () => new NativeBarcodeTrackingListenerProxy());
    scanditDatacaptureFrameworksCore.FactoryMaker.bindLazyInstance('BarcodeTrackingBasicOverlayProxy', () => new NativeBarcodeTrackingBasicOverlayProxy());
    scanditDatacaptureFrameworksCore.FactoryMaker.bindLazyInstance('BarcodeTrackingAdvancedOverlayProxy', () => new NativeBarcodeTrackingAdvancedOverlayProxy());
}

// tslint:disable-next-line:variable-name
const Cordova = {
    pluginName: 'ScanditBarcodeCapture',
    defaults: {},
    exec: (success, error, functionName, args) => scanditCordovaDatacaptureCore.cordovaExec(success, error, Cordova.pluginName, functionName, args),
};
function getDefaults() {
    return new Promise((resolve, reject) => {
        Cordova.exec((defaultsJSON) => {
            barcode.loadBarcodeDefaults(defaultsJSON);
            barcode.loadBarcodeCaptureDefaults(defaultsJSON.BarcodeCapture);
            barcode.loadBarcodeSelectionDefaults(defaultsJSON.BarcodeSelection);
            barcode.loadBarcodeTrackingDefaults(defaultsJSON.BarcodeTracking);
            initBarcodeProxies();
            resolve();
        }, reject, 'getDefaults', null);
    });
}
function initializeBarcodeCordova() {
    scanditCordovaDatacaptureCore.initializePlugin(Cordova.pluginName, getDefaults);
}
var CordovaFunction;
(function (CordovaFunction) {
    CordovaFunction["SubscribeBarcodeCaptureListener"] = "subscribeBarcodeCaptureListener";
    CordovaFunction["UnsubscribeBarcodeCaptureListener"] = "unsubscribeBarcodeCaptureListener";
    CordovaFunction["FinishBarcodeCaptureDidScan"] = "finishBarcodeCaptureDidScan";
    CordovaFunction["FinishBarcodeCaptureDidUpdateSession"] = "finishBarcodeCaptureDidUpdateSession";
    CordovaFunction["SetBarcodeCaptureModeEnabledState"] = "setBarcodeCaptureModeEnabledState";
    CordovaFunction["SetBarcodeSelectionModeEnabledState"] = "setBarcodeSelectionModeEnabledState";
    CordovaFunction["SubscribeBarcodeSelectionListener"] = "subscribeBarcodeSelectionListener";
    CordovaFunction["UnsubscribeBarcodeSelectionListener"] = "unsubscribeBarcodeSelectionListener";
    CordovaFunction["GetCountForBarcodeInBarcodeSelectionSession"] = "getCountForBarcodeInBarcodeSelectionSession";
    CordovaFunction["ResetBarcodeCaptureSession"] = "resetBarcodeCaptureSession";
    CordovaFunction["ResetBarcodeSelectionSession"] = "resetBarcodeSelectionSession";
    CordovaFunction["ResetBarcodeSelection"] = "resetBarcodeSelection";
    CordovaFunction["UnfreezeCameraInBarcodeSelection"] = "unfreezeCameraInBarcodeSelection";
    CordovaFunction["SelectAimedBarcode"] = "selectAimedBarcode";
    CordovaFunction["IncreaseCountForBarcodes"] = "increaseCountForBarcodes";
    CordovaFunction["UnselectBarcodes"] = "unselectBarcodes";
    CordovaFunction["SetSelectBarcodeEnabled"] = "setSelectBarcodeEnabled";
    CordovaFunction["FinishBarcodeSelectionDidUpdateSelection"] = "finishBarcodeSelectionDidUpdateSelection";
    CordovaFunction["FinishBarcodeSelectionDidUpdateSession"] = "finishBarcodeSelectionDidUpdateSession";
    CordovaFunction["UpdateBarcodeSelectionBasicOverlay"] = "updateBarcodeSelectionBasicOverlay";
    CordovaFunction["UpdateBarcodeSelectionMode"] = "updateBarcodeSelectionMode";
    CordovaFunction["ApplyBarcodeSelectionModeSettings"] = "applyBarcodeSelectionModeSettings";
    CordovaFunction["UpdateBarcodeCaptureOverlay"] = "updateBarcodeCaptureOverlay";
    CordovaFunction["UpdateBarcodeCaptureMode"] = "updateBarcodeCaptureMode";
    CordovaFunction["ApplyBarcodeCaptureModeSettings"] = "applyBarcodeCaptureModeSettings";
    CordovaFunction["SetTextForAimToSelectAutoHint"] = "setTextForAimToSelectAutoHint";
    CordovaFunction["RemoveAimedBarcodeBrushProvider"] = "removeAimedBarcodeBrushProvider";
    CordovaFunction["SetAimedBarcodeBrushProvider"] = "setAimedBarcodeBrushProvider";
    CordovaFunction["FinishBrushForAimedBarcodeCallback"] = "finishBrushForAimedBarcodeCallback";
    CordovaFunction["RemoveTrackedBarcodeBrushProvider"] = "removeTrackedBarcodeBrushProvider";
    CordovaFunction["SetTrackedBarcodeBrushProvider"] = "setTrackedBarcodeBrushProvider";
    CordovaFunction["FinishBrushForTrackedBarcodeCallback"] = "finishBrushForTrackedBarcodeCallback";
    CordovaFunction["SetViewForTrackedBarcode"] = "setViewForTrackedBarcode";
    CordovaFunction["SetAnchorForTrackedBarcode"] = "setAnchorForTrackedBarcode";
    CordovaFunction["SetOffsetForTrackedBarcode"] = "setOffsetForTrackedBarcode";
    CordovaFunction["ClearTrackedBarcodeViews"] = "clearTrackedBarcodeViews";
    CordovaFunction["SetBrushForTrackedBarcode"] = "setBrushForTrackedBarcode";
    CordovaFunction["ClearTrackedBarcodeBrushes"] = "clearTrackedBarcodeBrushes";
    CordovaFunction["SetBarcodeTrackingModeEnabledState"] = "setBarcodeTrackingModeEnabledState";
    CordovaFunction["SubscribeBarcodeTrackingListener"] = "subscribeBarcodeTrackingListener";
    CordovaFunction["UnregisterBarcodeTrackingListener"] = "unregisterBarcodeTrackingListener";
    CordovaFunction["SubscribeBarcodeTrackingAdvancedOverlayListener"] = "subscribeBarcodeTrackingAdvancedOverlayListener";
    CordovaFunction["SubscribeBarcodeTrackingBasicOverlayListener"] = "subscribeBarcodeTrackingBasicOverlayListener";
    CordovaFunction["ResetBarcodeTrackingSession"] = "resetBarcodeTrackingSession";
    CordovaFunction["UpdateBarcodeTrackingAdvancedOverlay"] = "updateBarcodeTrackingAdvancedOverlay";
    CordovaFunction["UpdateBarcodeTrackingBasicOverlay"] = "updateBarcodeTrackingBasicOverlay";
    CordovaFunction["UpdateBarcodeTrackingMode"] = "updateBarcodeTrackingMode";
    CordovaFunction["ApplyBarcodeTrackingModeSettings"] = "applyBarcodeTrackingModeSettings";
    CordovaFunction["FinishBarcodeTrackingDidUpdateSession"] = "finishBarcodeTrackingDidUpdateSession";
    CordovaFunction["FinishBarcodeTrackingBrushForTrackedBarcode"] = "finishBarcodeTrackingBrushForTrackedBarcode";
    CordovaFunction["UnregisterBarcodeTrackingAdvancedOverlayListener"] = "unregisterBarcodeTrackingAdvancedOverlayListener";
    CordovaFunction["UnregisterBarcodeTrackingBasicOverlayListener"] = "unregisterBarcodeTrackingBasicOverlayListener";
})(CordovaFunction || (CordovaFunction = {}));

class TrackedBarcodeView extends scanditDatacaptureFrameworksCore.DefaultSerializeable {
    static withHTMLElement(element, options) {
        return this.getEncodedImageData(element).then(data => new TrackedBarcodeView(data, options));
    }
    static withBase64EncodedData(data, options) {
        return Promise.resolve(new TrackedBarcodeView(data, options));
    }
    static getEncodedImageData(element) {
        return this.getBase64DataForSVG(this.getSVGDataForElement(element));
    }
    static getSize(element) {
        const isInDOM = document.body.contains(element);
        if (!isInDOM) {
            document.body.appendChild(element);
        }
        const size = element.getBoundingClientRect();
        if (!isInDOM) {
            document.body.removeChild(element);
        }
        return new scanditDatacaptureFrameworksCore.Size(size.width, size.height);
    }
    static getSVGDataForElement(element) {
        const size = this.getSize(element);
        const data = encodeURIComponent(`<svg xmlns="http://www.w3.org/2000/svg" width="${size.width}px" height="${size.height}px">
        <foreignObject width="100%" height="100%">
          <div xmlns="http://www.w3.org/1999/xhtml">
            ${element.outerHTML}
          </div>
        </foreignObject>
      </svg>`);
        return { data, size };
    }
    static getCanvasWithSize(size) {
        const canvas = document.createElement('canvas');
        canvas.width = size.width;
        canvas.height = size.height;
        return canvas;
    }
    static getBase64DataForSVG(svgData) {
        return new Promise((resolve, reject) => {
            const image = new Image();
            image.onload = () => {
                const canvas = this.getCanvasWithSize(svgData.size);
                canvas.getContext('2d').drawImage(image, 0, 0);
                resolve(canvas.toDataURL('image/png', 1));
            };
            image.onerror = reject;
            image.src = 'data:image/svg+xml,' + svgData.data;
        });
    }
    constructor(encodedData, options) {
        super();
        if (options == null) {
            options = { scale: 1 };
        }
        this.data = encodedData;
        this.options = options;
    }
}

class BarcodeTrackingAdvancedOverlay {
    get type() {
        return this.baseBarcodeTrackingOverlay.type;
    }
    get shouldShowScanAreaGuides() {
        return this.baseBarcodeTrackingOverlay.shouldShowScanAreaGuides;
    }
    set shouldShowScanAreaGuides(shouldShow) {
        this.baseBarcodeTrackingOverlay.shouldShowScanAreaGuides = shouldShow;
    }
    get listener() {
        return this.baseBarcodeTrackingOverlay.listener;
    }
    set listener(listener) {
        this.baseBarcodeTrackingOverlay.listener = listener;
    }
    static withBarcodeTrackingForView(barcodeTracking, view) {
        const overlay = new BarcodeTrackingAdvancedOverlay();
        overlay.baseBarcodeTrackingOverlay.initialize(barcodeTracking, view);
        return overlay;
    }
    constructor() {
        this.baseBarcodeTrackingOverlay = new barcode.BaseBarcodeTrackingAdvancedOverlay();
    }
    setViewForTrackedBarcode(view, trackedBarcode) {
        return this.baseBarcodeTrackingOverlay.setViewForTrackedBarcode(view, trackedBarcode);
    }
    setAnchorForTrackedBarcode(anchor, trackedBarcode) {
        return this.baseBarcodeTrackingOverlay.setAnchorForTrackedBarcode(anchor, trackedBarcode);
    }
    setOffsetForTrackedBarcode(offset, trackedBarcode) {
        return this.baseBarcodeTrackingOverlay.setOffsetForTrackedBarcode(offset, trackedBarcode);
    }
    clearTrackedBarcodeViews() {
        return this.baseBarcodeTrackingOverlay.clearTrackedBarcodeViews();
    }
    toJSON() {
        return this.baseBarcodeTrackingOverlay.toJSON();
    }
}

initializeBarcodeCordova();

exports.ArucoDictionary = barcode.ArucoDictionary;
Object.defineProperty(exports, 'ArucoDictionaryPreset', {
    enumerable: true,
    get: function () { return barcode.ArucoDictionaryPreset; }
});
exports.ArucoMarker = barcode.ArucoMarker;
exports.Barcode = barcode.Barcode;
exports.BarcodeCapture = barcode.BarcodeCapture;
exports.BarcodeCaptureFeedback = barcode.BarcodeCaptureFeedback;
exports.BarcodeCaptureOverlay = barcode.BarcodeCaptureOverlay;
Object.defineProperty(exports, 'BarcodeCaptureOverlayStyle', {
    enumerable: true,
    get: function () { return barcode.BarcodeCaptureOverlayStyle; }
});
exports.BarcodeCaptureSession = barcode.BarcodeCaptureSession;
exports.BarcodeCaptureSettings = barcode.BarcodeCaptureSettings;
exports.BarcodeSelection = barcode.BarcodeSelection;
exports.BarcodeSelectionAimerSelection = barcode.BarcodeSelectionAimerSelection;
exports.BarcodeSelectionAutoSelectionStrategy = barcode.BarcodeSelectionAutoSelectionStrategy;
exports.BarcodeSelectionBasicOverlay = barcode.BarcodeSelectionBasicOverlay;
Object.defineProperty(exports, 'BarcodeSelectionBasicOverlayStyle', {
    enumerable: true,
    get: function () { return barcode.BarcodeSelectionBasicOverlayStyle; }
});
exports.BarcodeSelectionFeedback = barcode.BarcodeSelectionFeedback;
Object.defineProperty(exports, 'BarcodeSelectionFreezeBehavior', {
    enumerable: true,
    get: function () { return barcode.BarcodeSelectionFreezeBehavior; }
});
exports.BarcodeSelectionManualSelectionStrategy = barcode.BarcodeSelectionManualSelectionStrategy;
exports.BarcodeSelectionSession = barcode.BarcodeSelectionSession;
exports.BarcodeSelectionSettings = barcode.BarcodeSelectionSettings;
Object.defineProperty(exports, 'BarcodeSelectionTapBehavior', {
    enumerable: true,
    get: function () { return barcode.BarcodeSelectionTapBehavior; }
});
exports.BarcodeSelectionTapSelection = barcode.BarcodeSelectionTapSelection;
exports.BarcodeTracking = barcode.BarcodeTracking;
exports.BarcodeTrackingBasicOverlay = barcode.BarcodeTrackingBasicOverlay;
Object.defineProperty(exports, 'BarcodeTrackingBasicOverlayStyle', {
    enumerable: true,
    get: function () { return barcode.BarcodeTrackingBasicOverlayStyle; }
});
Object.defineProperty(exports, 'BarcodeTrackingScenario', {
    enumerable: true,
    get: function () { return barcode.BarcodeTrackingScenario; }
});
exports.BarcodeTrackingSession = barcode.BarcodeTrackingSession;
exports.BarcodeTrackingSettings = barcode.BarcodeTrackingSettings;
Object.defineProperty(exports, 'Checksum', {
    enumerable: true,
    get: function () { return barcode.Checksum; }
});
Object.defineProperty(exports, 'CompositeFlag', {
    enumerable: true,
    get: function () { return barcode.CompositeFlag; }
});
Object.defineProperty(exports, 'CompositeType', {
    enumerable: true,
    get: function () { return barcode.CompositeType; }
});
exports.Ean13UpcaClassification = barcode.Ean13UpcaClassification;
exports.EncodingRange = barcode.EncodingRange;
exports.LocalizedOnlyBarcode = barcode.LocalizedOnlyBarcode;
exports.Range = barcode.Range;
exports.StructuredAppendData = barcode.StructuredAppendData;
Object.defineProperty(exports, 'Symbology', {
    enumerable: true,
    get: function () { return barcode.Symbology; }
});
exports.SymbologyDescription = barcode.SymbologyDescription;
exports.SymbologySettings = barcode.SymbologySettings;
exports.TrackedBarcode = barcode.TrackedBarcode;
exports.BarcodeTrackingAdvancedOverlay = BarcodeTrackingAdvancedOverlay;
exports.TrackedBarcodeView = TrackedBarcodeView;
