import { TrackedBarcode } from 'scandit-datacapture-frameworks-barcode';
import { BarcodeTrackingAdvancedOverlay } from './BarcodeTrackingAdvancedOverlay';
import { TrackedBarcodeView } from './TrackedBarcodeView';
import { Anchor, PointWithUnit } from 'scandit-datacapture-frameworks-core';
export interface BarcodeTrackingAdvancedOverlayListener {
    didTapViewForTrackedBarcode?(overlay: BarcodeTrackingAdvancedOverlay, trackedBarcode: TrackedBarcode): void;
    viewForTrackedBarcode?(overlay: BarcodeTrackingAdvancedOverlay, trackedBarcode: TrackedBarcode): Promise<TrackedBarcodeView | null>;
    anchorForTrackedBarcode?(overlay: BarcodeTrackingAdvancedOverlay, trackedBarcode: TrackedBarcode): Anchor;
    offsetForTrackedBarcode?(overlay: BarcodeTrackingAdvancedOverlay, trackedBarcode: TrackedBarcode): PointWithUnit;
}
