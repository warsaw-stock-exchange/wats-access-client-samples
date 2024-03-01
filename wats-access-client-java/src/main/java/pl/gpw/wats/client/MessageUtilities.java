package pl.gpw.wats.client;

public class MessageUtilities {

    private static final String HEXES = "0123456789ABCDEF";

    /**
     * Convert array of bytes to a human-readable format.
     *
     * @param bytes
     * @return human-readable string
     */
    public static String bytesToHexString(byte[] bytes) {
        String msg = "[";
        for (int i = 0; i < bytes.length; i++) {
            msg += HEXES.charAt((bytes[i] & 0xF0) >> 4);
            msg += HEXES.charAt((bytes[i] & 0x0F));
            if (i < bytes.length - 1) {
                msg += ",";
            }
        }
        msg += "]";
        return msg;
    }

}
