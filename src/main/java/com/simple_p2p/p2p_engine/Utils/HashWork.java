package com.simple_p2p.p2p_engine.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashWork {

    private HashWork() {
    }

    public static String giveMyHash() {

        byte[] part1 = NetworkEnvironment.getLocalAddress().getAddress();
        byte[] part2 = NetworkEnvironment.getLocalMacAddress();
        byte[] part3 = System.getProperty("user.name").getBytes();
        byte[] resultBytes = new byte[part1.length + part2.length + part3.length];
        System.arraycopy(part1, 0, resultBytes, 0, part1.length);
        System.arraycopy(part2, 0, resultBytes, part1.length - 1, part2.length);
        System.arraycopy(part3, 0, resultBytes, part1.length + part2.length - 1, part3.length);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        StringBuffer result = new StringBuffer();

        for (int i = 0; i < resultBytes.length; i++) {
            String hex = Integer.toHexString(0xff & resultBytes[i]);
            if (hex.length() == 1) result.append('0');
            result.append(hex);
        }

        return result.toString();
    }
}
