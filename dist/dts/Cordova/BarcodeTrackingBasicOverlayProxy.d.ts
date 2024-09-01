import { BarcodeTrackingBasicOverlayProxy } from 'scandit-datacapture-frameworks-barcode';
import { BaseNativeProxy } from 'scandit-datacapture-frameworks-core';
export declare class NativeBarcodeTrackingBasicOverlayProxy extends BaseNativeProxy implements BarcodeTrackingBasicOverlayProxy {
    private static get cordovaExec();
    setBrushForTrackedBarcode(brushJson: string | null, trackedBarcodeIdentifier: number, sessionFrameSequenceID: number): Promise<void>;
    clearTrackedBarcodeBrushes(): Promise<void>;
    registerListenerForBasicOverlayEvents(): void;
    updateBarcodeTrackingBasicOverlay(overlayJson: string): Promise<void>;
    unregisterListenerForBasicOverlayEvents(): Promise<void>;
    private notifyListeners;
}
