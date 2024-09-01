import { BaseNativeProxy } from 'scandit-datacapture-frameworks-core';
import { BarcodeSelectionProxy } from 'scandit-datacapture-frameworks-barcode';
export declare class NativeBarcodeSelectionProxy extends BaseNativeProxy implements BarcodeSelectionProxy {
    private static get cordovaExec();
    unfreezeCamera(): Promise<void>;
    resetMode(): Promise<void>;
    selectAimedBarcode(): Promise<void>;
    unselectBarcodes(barcodesStr: string): Promise<void>;
    setSelectBarcodeEnabled(barcodeStr: string, enabled: boolean): Promise<void>;
    increaseCountForBarcodes(barcodeStr: string): Promise<void>;
    setModeEnabledState(enabled: boolean): void;
    updateBarcodeSelectionMode(modeJson: string): Promise<void>;
    applyBarcodeSelectionModeSettings(newSettingsJson: string): Promise<void>;
}
