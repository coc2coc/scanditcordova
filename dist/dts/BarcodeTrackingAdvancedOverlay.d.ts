import { BarcodeTracking, TrackedBarcode } from 'scandit-datacapture-frameworks-barcode';
import { Anchor, DataCaptureOverlay, DataCaptureView, PointWithUnit } from 'scandit-datacapture-frameworks-core';
import { BarcodeTrackingAdvancedOverlayListener } from './BarcodeTrackingAdvancedOverlayListener';
import { TrackedBarcodeView } from './TrackedBarcodeView';
export declare class BarcodeTrackingAdvancedOverlay implements DataCaptureOverlay {
    private baseBarcodeTrackingOverlay;
    private get type();
    get shouldShowScanAreaGuides(): boolean;
    set shouldShowScanAreaGuides(shouldShow: boolean);
    get listener(): BarcodeTrackingAdvancedOverlayListener | null;
    set listener(listener: BarcodeTrackingAdvancedOverlayListener | null);
    static withBarcodeTrackingForView(barcodeTracking: BarcodeTracking, view: DataCaptureView | null): BarcodeTrackingAdvancedOverlay;
    private constructor();
    setViewForTrackedBarcode(view: Promise<TrackedBarcodeView | null>, trackedBarcode: TrackedBarcode): Promise<void>;
    setAnchorForTrackedBarcode(anchor: Anchor, trackedBarcode: TrackedBarcode): Promise<void>;
    setOffsetForTrackedBarcode(offset: PointWithUnit, trackedBarcode: TrackedBarcode): Promise<void>;
    clearTrackedBarcodeViews(): Promise<void>;
    toJSON(): object;
}
