/*
 * This file is part of the Scandit Data Capture SDK
 *
 * Copyright (C) 2023- Scandit AG. All rights reserved.
 */

import ScanditBarcodeCapture
import WebKit

class BarcodeFindViewHandler {
    let webView: WKWebView

    var barcodeFindView: BarcodeFindView? {
        willSet {
            barcodeFindView?.removeFromSuperview()
        }
        didSet {
            guard let barcodeFindView = barcodeFindView else { return }
            barcodeFindView.translatesAutoresizingMaskIntoConstraints = false
            webView.addSubview(barcodeFindView)
            resetConstraints()
            update()
        }
    }

    private var top: NSLayoutConstraint?
    private var left: NSLayoutConstraint?
    private var width: NSLayoutConstraint?
    private var height: NSLayoutConstraint?

    private var position = CGPoint.zero
    private var size = CGSize.zero
    private var shouldBeUnderWebView = false

    private var constraints: [NSLayoutConstraint] {
        return [top, left, width, height].compactMap({ $0 })
    }

    init(relativeTo webView: WKWebView) {
        self.webView = webView
    }

    /// Update the constraints that set the position and size of the capture view,
    /// based on a JSON passed in as the argument to a Cordova command.
    ///
    /// If the view does not exist yet, the position and size are stored and will be applied to the view
    /// when it's created (and the constraints object is updated with the new view).
    ///
    /// - Parameter positionAndSizeJSON: The JSON passed to the Cordova command
    func updatePositionAndSize(fromJSON positionAndSizeJSON: ViewPositionAndSizeJSON) {
        position = positionAndSizeJSON.position
        size = positionAndSizeJSON.size
        shouldBeUnderWebView = positionAndSizeJSON.shouldBeUnderWebView
        update()
    }

    private func update() {
        updateConstraints()
        updatePosition()
    }

    private func activate() {
        NSLayoutConstraint.activate(constraints)
    }

    private func resetConstraints() {
        top = nil
        left = nil
        width = nil
        height = nil
    }

    private func updateConstraints() {
        guard let barcodeFindView = barcodeFindView else {
            return
        }

        let topConstant = position.y + webView.adjustedContentInset.top
        let leftConstant = position.x + webView.adjustedContentInset.left

        if let top = top {
            top.constant = topConstant
        } else {
            top = barcodeFindView.topAnchor.constraint(equalTo: webView.topAnchor, constant: topConstant)
            top?.isActive = true
        }

        if let left = left {
            left.constant = leftConstant
        } else {
            left = barcodeFindView.leadingAnchor.constraint(equalTo: webView.leadingAnchor, constant: leftConstant)
            left?.isActive = true
        }

        if let width = width {
            width.constant = size.width
        } else {
            width = barcodeFindView.widthAnchor.constraint(equalToConstant: size.width)
            width?.isActive = true
        }

        if let height = height {
            height.constant = size.height
        } else {
            height = barcodeFindView.heightAnchor.constraint(equalToConstant: size.height)
            height?.isActive = true
        }

        barcodeFindView.superview?.layoutIfNeeded()
    }

    private func updatePosition() {
        guard let barcodeFindView = barcodeFindView else {
            return
        }

        if shouldBeUnderWebView {
            barcodeFindView.superview?.sendSubviewToBack(barcodeFindView)
        } else {
            barcodeFindView.superview?.bringSubviewToFront(barcodeFindView)
        }
    }
}

