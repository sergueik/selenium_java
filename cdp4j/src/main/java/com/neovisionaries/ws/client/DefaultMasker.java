package com.neovisionaries.ws.client;

public class DefaultMasker implements Masker {

    byte[] maskingKey;

    public DefaultMasker() {
        this.maskingKey = Misc.nextBytes(4);
    }

    public byte[] getMaskingKey() {
        return this.maskingKey;
    }

    public void mask(byte[] payload) {
        if (payload == null)
        {
            return;
        }

        for (int i = 0; i < payload.length; ++i)
        {
            // Mask
            payload[i] = (byte) ((payload[i] ^ this.maskingKey[i % 4]) & 0xFF);
        }
    }
}
