import { BarcodeSelectionListenerProxy } from 'scandit-datacapture-frameworks-barcode';
import { BaseNativeProxy } from 'scandit-datacapture-frameworks-core';
export declare class NativeBarcodeSelectionListenerProxy extends BaseNativeProxy implements BarcodeSelectionListenerProxy {
    private static get cordovaExec();
    isModeEnabled: () => boolean;
    getCount(selectionIdentifier: string): Promise<number>;
    resetSession(): Promise<void>;
    registerListenerForEvents(): void;
    finishDidUpdateSelectionCallback(isEnabled: boolean): void;
    finishDidUpdateSessionCallback(isEnabled: boolean): void;
    unregisterListenerForEvents(): void;
    private notifyListeners;
}
