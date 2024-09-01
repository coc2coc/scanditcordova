import { BarcodeTrackingListenerProxy } from 'scandit-datacapture-frameworks-barcode';
import { BaseNativeProxy } from 'scandit-datacapture-frameworks-core';
export declare class NativeBarcodeTrackingListenerProxy extends BaseNativeProxy implements BarcodeTrackingListenerProxy {
    private static get cordovaExec();
    isModeEnabled: () => boolean;
    resetSession(): Promise<void>;
    registerListenerForEvents(): void;
    unregisterListenerForEvents(): void;
    setModeEnabledState(enabled: boolean): void;
    updateBarcodeTrackingMode(modeJson: string): Promise<void>;
    applyBarcodeTrackingModeSettings(newSettingsJson: string): Promise<void>;
    finishDidUpdateSessionCallback(enabled: boolean): void;
    private notifyListeners;
}
