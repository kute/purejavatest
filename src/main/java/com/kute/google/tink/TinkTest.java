package com.kute.google.tink;

import com.google.crypto.tink.aead.AeadKeyTemplates;
import com.google.crypto.tink.config.TinkConfig;
import com.google.crypto.tink.proto.KeyTemplate;

import java.security.GeneralSecurityException;

/**
 * created by kute on 2018/09/25 09:45
 */
public class TinkTest {

    static {

        try {
            TinkConfig.register();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {

        KeyTemplate keyTemplate = AeadKeyTemplates.AES128_GCM;

    }

}
