package com.nearit.ui_bindings.utils.qrcode;

/**
 * @author Federico Boschini
 */
public class QRcodeGeneratorProvider {

    private int width, height;

    public QRcodeGeneratorProvider(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public QRcodeGenerator getGenerator() {
        return new QRcodeGenerator(width, height);
    }

}
