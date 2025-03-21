package shop.ninescent.mall.image.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class FileNameUtil {
    public static String sanitizeFileName(String fileName) {
        if (fileName == null) return "unknown";
        try {
            String encoded = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
            return encoded.replaceAll("[^a-zA-Z0-9._-]", "_");
        } catch (Exception e) {
            return "unknown";
        }
    }
}
