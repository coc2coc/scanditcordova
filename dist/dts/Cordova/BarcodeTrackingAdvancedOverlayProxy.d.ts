import { BarcodeTrackingAdvancedOverlayProxy, BarcodeTrackingAdvancedOverlayView } from 'scandit-datacapture-frameworks-barcode';
import { BaseNativeProxy } from 'scandit-datacapture-frameworks-core';
import { Anchor } from 'scandit-datacapture-frameworks-core';
export declare class NativeBarcodeTrackingAdvancedOverlayProxy extends BaseNativeProxy implements BarcodeTrackingAdvancedOverlayProxy {
    private static get cordovaExec();
    setBrushForTrackedBarcode(_brushJson: string, _sessionFrameSequenceID: number | null, _trackedBarcodeIdentifier: number): Promise<void>;
    setViewForTrackedBarcode(viewJson: string | object | null, trackedBarcodeIdentifier: number, sessionFrameSequenceID: number | null): Promise<void>;
    setAnchorForTrackedBarcode(anchor: Anchor, trackedBarcodeIdentifier: number, sessionFrameSequenceID: number | null): Promise<void>;
    setOffsetForTrackedBarcode(offsetJson: string, trackedBarcodeIdentifier: number, sessionFrameSequenceID: number | null): Promise<void>;
    clearTrackedBarcodeViews(): Promise<void>;
    registerListenerForAdvancedOverlayEvents(): void;
    updateBarcodeTrackingAdvancedOverlay(overlayJson: string): Promise<void>;
    unregisterListenerForAdvancedOverlayEvents(): Promise<void>;
    getJSONStringForView(view: BarcodeTrackingAdvancedOverlayView | null): string | object | null;
    private notifyListeners;
}
