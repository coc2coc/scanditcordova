import { BarcodeCaptureListenerProxy } from 'scandit-datacapture-frameworks-barcode';
import { BaseNativeProxy } from 'scandit-datacapture-frameworks-core';
export declare class NativeBarcodeCaptureListenerProxy extends BaseNativeProxy implements BarcodeCaptureListenerProxy {
    private static get cordovaExec();
    isModeEnabled: () => boolean;
    resetSession(): Promise<void>;
    registerListenerForEvents(): void;
    setModeEnabledState(enabled: boolean): void;
    unregisterListenerForEvents(): void;
    finishDidUpdateSessionCallback(isFinished: boolean): void;
    finishDidScanCallback(isFinished: boolean): void;
    updateBarcodeCaptureMode(modeJson: string): Promise<void>;
    applyBarcodeCaptureModeSettings(newSettingsJson: string): Promise<void>;
    updateBarcodeCaptureOverlay(overlayJson: string): Promise<void>;
    private emitInCallback;
    private notifyListeners;
}
