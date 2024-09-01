import { BarcodePickView, BarcodePickViewProxy } from 'scandit-datacapture-frameworks-barcode';
import { BaseNativeProxy } from 'scandit-datacapture-frameworks-core';
export declare class NativeBarcodePickViewProxy extends BaseNativeProxy implements BarcodePickViewProxy {
    private static get cordovaExec();
    initialize(_view?: BarcodePickView | undefined): Promise<void>;
    findNodeHandle(_view?: BarcodePickView | undefined): number | null;
    createView(_: number | null, json: string): Promise<void>;
    viewStart(): Promise<void>;
    viewPause(): Promise<void>;
    updateView(json: string): Promise<void>;
    setPositionAndSize(top: number, left: number, width: number, height: number, shouldBeUnderWebView: boolean): Promise<void>;
    subscribeDidPickItemListener(): Promise<void>;
    subscribeDidUnpickItemListener(): Promise<void>;
    addActionListener(): Promise<void>;
    finishPickAction(code: string, result: boolean): Promise<void>;
    unsubscribeListeners(): Promise<void>;
    private didPickItemListenerHandler;
    private didUnpickItemListenerHandler;
}
