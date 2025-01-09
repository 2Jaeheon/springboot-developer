package org.jaeheon.springbootdeveloper.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Base64;
import org.springframework.util.SerializationUtils;

public class CookieUtil {

    // Add cookie to HTTP response based on request (name, value, expiration period)
    public static void addCookie(HttpServletResponse response,
        String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    // Enter the cookie name and delete the cookie.
    // The cookie expires by setting it to an empty value and the expiration time to 0.
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response,
        String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            return;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }

    // Serialize an object and convert it to a cookie value
    public static String serialize(Object obj) {
        return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(obj));
    }

    // Deserialize a cookie value and convert it to an object
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
            // Deserialize the cookie value by decoding it from Base64
            SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue()))
        );
    }

}
