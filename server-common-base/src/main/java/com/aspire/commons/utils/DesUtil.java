package com.aspire.commons.utils;


import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DesUtil {

    private static final String DES_ALGORITHM = "DES";
    /**
     * DES加密
     *
     * @param plainData
     * @return
     * @throws Exception
     */
    public static String encryption(String plainData) {

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, generateKey("value"));
            // 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must be multiple of 8 when decrypting with padded cipher异常，
            // 不能把加密后的字节数组直接转换成字符串
            byte[] buf = cipher.doFinal(plainData.getBytes());
            return Base64Utils.encode(buf);
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException={}",e);
        } catch (NoSuchPaddingException e) {
            log.error("NoSuchPaddingException={}",e);
        } catch (InvalidKeyException e) {
            log.error("InvalidKeyException={}",e);
        }catch (IllegalBlockSizeException e) {
            log.error("IllegalBlockSizeException={}",e);
        } catch (BadPaddingException e) {
            log.error("BadPaddingException={}",e);
        }
        return null;
    }

    /**
     * DES解密
     *
     * @param secretData
     * @return
     */
    public static String decryption(String secretData) {

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(DES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, generateKey("value"));
            byte[] buf = cipher.doFinal(Base64Utils.decode(secretData.toCharArray()));
            return new String(buf);
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException={}",e);
        } catch (NoSuchPaddingException e) {
            log.error("NoSuchPaddingException={}",e);
        } catch (InvalidKeyException e) {
            log.error("InvalidKeyException={}",e);
        } catch (IllegalBlockSizeException e) {
            log.error("IllegalBlockSizeException={}",e);
        } catch (BadPaddingException e) {
            log.error("BadPaddingException={}",e);
        }
        return null;
    }


    /**
     * 获得秘密密钥
     *
     * @param secretKey
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static SecretKey generateKey(String secretKey) throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        secureRandom.setSeed(secretKey.getBytes());
        // 为我们选择的DES算法生成一个KeyGenerator对象
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(DES_ALGORITHM);
            kg.init(secureRandom);
            // 生成密钥
            return kg.generateKey();
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException e={}",e);
        }
        return null;
    }


    private static class Base64Utils {

        private static  char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
        private static  byte[] codes = new byte[256];

        static {
            for (int i = 0; i < 256; i++){
                codes[i] = -1;
            }

            for (int i = 'A'; i <= 'Z'; i++){
                codes[i] = (byte) (i - 'A');
            }

            for (int i = 'a'; i <= 'z'; i++){
                codes[i] = (byte) (26 + i - 'a');
            }

            for (int i = '0'; i <= '9'; i++){
                codes[i] = (byte) (52 + i - '0');
            }
            codes['+'] = 62;
            codes['/'] = 63;
        }

        /**
         * 将原始数据编码为base64编码
         */
        static  String encode(byte[] data) {
            char[] out = new char[((data.length + 2) / 3) * 4];
            for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
                boolean quad = false;
                boolean trip = false;
                int val = (0xFF & (int) data[i]);
                val <<= 8;
                if ((i + 1) < data.length) {
                    val |= (0xFF & (int) data[i + 1]);
                    trip = true;
                }
                val <<= 8;
                if ((i + 2) < data.length) {
                    val |= (0xFF & (int) data[i + 2]);
                    quad = true;
                }
                out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 1] = alphabet[val & 0x3F];
                val >>= 6;
                out[index + 0] = alphabet[val & 0x3F];
            }

            return new String(out);
        }

        /**
         * 将base64编码的数据解码成原始数据
         */
        static  byte[] decode(char[] data) {
            int len = ((data.length + 3) / 4) * 3;
            if (data.length > 0 && data[data.length - 1] == '='){
                --len;
            }

            if (data.length > 1 && data[data.length - 2] == '='){
                --len;
            }
            byte[] out = new byte[len];
            int shift = 0;
            int accum = 0;
            int index = 0;
            for (int ix = 0; ix < data.length; ix++) {
                int value = codes[data[ix] & 0xFF];
                if (value >= 0) {
                    accum <<= 6;
                    shift += 6;
                    accum |= value;
                    if (shift >= 8) {
                        shift -= 8;
                        out[index++] = (byte) ((accum >> shift) & 0xff);
                    }
                }
            }
            if (index != out.length){
                log.error("miscalculated data length!");
                return new byte[1];
            }
            return out;
        }
    }


    public static void main(String[] a) throws Exception {
        try{
            String input = "中国";
            String result = encryption(input);
            log.info("result= {}",result);
            log.info("decryption(result)= {}",decryption(result));
        }catch (NullPointerException e){
            log.error("NullPointerException e={}",e);
        }
    }
}