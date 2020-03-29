package com.spitter.security.encoder;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Tyler Yin
 */
public class MD5Encoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Utils.encode((String) rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5Utils.encode((String) rawPassword));
    }
}