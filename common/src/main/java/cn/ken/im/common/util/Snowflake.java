package cn.ken.im.common.util;

import cn.hutool.core.lang.generator.SnowflakeGenerator;

/**
 * <pre>
 *
 * </pre>
 *
 * @author <a href="https://github.com/Ken-Chy129">Ken-Chy129</a>
 * @since 2023/11/5 15:22
 */
public class Snowflake {

    private static final SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();

    public static Long nextId() {
        return snowflakeGenerator.next();
    }
}
