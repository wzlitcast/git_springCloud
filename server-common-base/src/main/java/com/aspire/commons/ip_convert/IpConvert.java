package com.aspire.commons.ip_convert;

public class IpConvert {

//    public templates void main(String[] args) {
//
//        String s = "255.255.255.205";
//        System.out.println("iptoLong  : " + IpConvert.ipToLong(s));
//        System.out.println("iptoLong2 : " + IpConvert.ipToLong2(s));
//
//        System.out.println("longToIp  : " + IpConvert.longToIp(3689901706L));
//        System.out.println("longToIp2 : " + IpConvert.longToIp2(3689901706L));
//
//    }

    // example : 192.168.1.2
    public static long ipToLong(String ipAddress) {

        // ipAddressInArray[0] = 192
        String[] ipAddressInArray = ipAddress.split("\\.");

        long result = 0;
        for (int i = 0; i < ipAddressInArray.length; i++) {

            int power = 3 - i;
            int ip = Integer.parseInt(ipAddressInArray[i]);

            // 1. 192 * 256^3
            // 2. 168 * 256^2
            // 3. 1 * 256^1
            // 4. 2 * 256^0
            result += ip * Math.pow(256, power);

        }

        return result;

    }
//
//    public templates long ipToLong2(String ipAddress) {
//
//        long result = 0;
//
//        String[] ipAddressInArray = ipAddress.split("\\.");
//
//        for (int i = 3; i >= 0; i--) {
//
//            long ip = Long.parseLong(ipAddressInArray[3 - i]);
//
//            // left shifting 24,16,8,0 and bitwise OR
//
//            // 1. 192 << 24
//            // 1. 168 << 16
//            // 1. 1 << 8
//            // 1. 2 << 0
//            result |= ip << (i * 8);
//
//        }
//
//        return result;
//    }

    public static String longToIp(long i) {

        return ((i >> 24) & 0xFF) +
                "." + ((i >> 16) & 0xFF) +
                "." + ((i >> 8) & 0xFF) +
                "." + (i & 0xFF);

    }
//
//    public templates String longToIp2(long ip) {
//        StringBuilder sb = new StringBuilder(15);
//
//        for (int i = 0; i < 4; i++) {
//
//            // 1. 2
//            // 2. 1
//            // 3. 168
//            // 4. 192
//            sb.insert(0, Long.toString(ip & 0xff));
//
//            if (i < 3) {
//                sb.insert(0, '.');
//            }
//
//            // 1. 192.168.1.2
//            // 2. 192.168.1
//            // 3. 192.168
//            // 4. 192
//            ip = ip >> 8;
//
//        }
//
//        return sb.toString();
//    }

}
