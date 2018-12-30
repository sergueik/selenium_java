package com.neovisionaries.ws.client;

public class ZeroMasker implements Masker {

    public byte[] getMaskingKey() {
        return new byte[] {0x0,0x0,0x0,0x0};
    }

    public void mask(byte[] payload) {
        return;
    }
}
