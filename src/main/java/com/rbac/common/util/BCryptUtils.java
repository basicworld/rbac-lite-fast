package com.rbac.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * BCrypt 非对称加密工具类<br>
 * 提供静态方法：加密字符串，判断明文、密文是否一致<br>
 *
 *
 * 简介：bcrypt是一种跨平台的文件加密工具。<br>
 * Bcrypt就是一款加密工具，可以比较方便地实现数据的加密工作。你也可以简单理解为它内部自己实现了随机加盐处理<br>
 * 例如，我们使用MD5加密，每次加密后的密文其实都是一样的，这样就方便了MD5通过大数据的方式进行破解。<br>
 * Bcrypt生成的密文是60位的。而MD5的是32位的。 使用BCrypt 主要是能实现每次加密的值都是不一样的。<br>
 *
 * @author wlfei
 *
 */
public class BCryptUtils {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * 使用BCrypt算法加密字符串（密码）
     *
     * @param rawPassword
     * @return
     */
    public static String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    /**
     * 判断两个密码是否相同
     *
     * @param rawPassword     未经加密的密码
     * @param encodedPassword 经过加密的密码
     * @return
     */
    public static Boolean isSamePassword(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
