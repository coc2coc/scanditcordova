import ScanditBarcodeCapture

struct BarcodeCaptureCallbackResult: BlockingListenerCallbackResult {
    struct ResultJSON: Decodable {
        enum CodingKeys: String, CodingKey {
            case enabled
            case brushJSONString = "brush"
            case viewJSON = "view"
            case anchorString = "anchor"
            case offsetString = "offset"
        }

        let enabled: Bool?
        let brushJSONString: String?
        let viewJSON: TrackedBarcodeView.JSON?
        let anchorString: String?
        let offsetString: String?
    }

    let finishCallbackID: ListenerEvent.Name
    let result: ResultJSON?

    var enabled: Bool? {
        guard let result = result else {
            return nil
        }

        return result.enabled
    }

    var view: TrackedBarcodeView? {
        guard let viewJSON = result?.viewJSON else {
            return nil
        }

        return TrackedBarcodeView(json: viewJSON)
    }

    var brush: Brush? {
        guard let result = result, let jsonString = result.brushJSONString else {
            return nil
        }

        return Brush(jsonString: jsonString)
    }

    var anchor: Anchor? {
        var anchor = Anchor.center
        guard let result = result, let anchorString = result.anchorString, SDCAnchorFromJSONString(anchorString, &anchor) else {
            return nil
        }
        return anchor
    }

    var offset: PointWithUnit? {
        var offset = PointWithUnit.zero
        guard let result = result, let offsetString = result.offsetString, SDCPointWithUnitFromJSONString(offsetString,
                                                                                                          &offset) else {
            return nil
        }
        return offset
    }
}
