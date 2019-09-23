package com.aspire.commons.log;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Marker;

import java.util.function.Supplier;


/**
 * log公共类
 */
@Slf4j
public class LogCommons {

    /**
     * @param key      参数KEY
     * @param supplier 参数
     */
    public static void info(String key, Supplier<?> supplier) {
        log.info(key + ": {}", supplier.get());
    }


    /**
     * @param msg 参数msg
     */
    public static void info(String msg) {
        log.info(msg);
    }

    /**
     * Log a message at the INFO level according to the specified format
     * and argument.
     * <p/>
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the INFO level. </p>
     *
     * @param format the format string
     * @param arg    the argument
     */
    public static void info(String format, Object arg){
        log.info(format,arg);
    };

    /**
     * Log a message at the INFO level according to the specified format
     * and arguments.
     * <p/>
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the INFO level. </p>
     *
     * @param format the format string
     * @param arg1   the first argument
     */
    public static void info(String format, String[] arg1){
        log.info(format,arg1);
    }

    /**
     * Log a message at the INFO level according to the specified format
     * and arguments.
     * <p/>
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the INFO level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for INFO. The variants taking
     * {@link #info(String, Object) one} and {@link # info(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.</p>
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public static void info(String format, Object... arguments){
        log.info(format,arguments);
    }

    /**
     * Log an exception (throwable) at the INFO level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    public static void info(String msg, Throwable t){
        log.info(msg,t);
    }



    /**
     * @param key      参数KEY
     * @param supplier 参数
     */
    public static void debug(String key, Supplier<?> supplier) {
        if (log.isDebugEnabled()) {
            log.debug(key + ": {}", supplier.get());
        }
    }


    /**
     * @param msg 参数msg
     */
    public static void debug(String msg) {
        if (log.isDebugEnabled()) {
            log.debug(msg);
        }
    }


    /**
     * Log a message at the DEBUG level according to the specified format
     * and argument.
     * <p/>
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the DEBUG level. </p>
     *
     * @param format the format string
     * @param arg    the argument
     */
    public static void debug(String format, Object arg){
        if (log.isDebugEnabled()) {
            log.debug(format,arg);
        }
    }

    /**
     * Log a message at the DEBUG level according to the specified format
     * and arguments.
     * <p/>
     * <p>This form avoids superfluous object creation when the logger
     * is disabled for the DEBUG level. </p>
     *
     * @param format the format string
     * @param arg1   the first argument
     * @param arg2   the second argument
     */
    public static void debug(String format, Object arg1, Object arg2){
        if (log.isDebugEnabled()) {
            log.debug(format,arg1,arg2);
        }
    }

    /**
     * Log a message at the DEBUG level according to the specified format
     * and arguments.
     * <p/>
     * <p>This form avoids superfluous string concatenation when the logger
     * is disabled for the DEBUG level. However, this variant incurs the hidden
     * (and relatively small) cost of creating an <code>Object[]</code> before invoking the method,
     * even if this logger is disabled for DEBUG. The variants taking
     * {@link #debug(String, Object) one} and {@link #debug(String, Object, Object) two}
     * arguments exist solely in order to avoid this hidden cost.</p>
     *
     * @param format    the format string
     * @param arguments a list of 3 or more arguments
     */
    public static void debug(String format, Object... arguments){
        if (log.isDebugEnabled()) {
            log.debug(format,arguments);
        }
    }

    /**
     * Log an exception (throwable) at the DEBUG level with an
     * accompanying message.
     *
     * @param msg the message accompanying the exception
     * @param t   the exception (throwable) to log
     */
    public static void debug(String msg, Throwable t){
        if (log.isDebugEnabled()) {
            log.debug(msg,t);
        }
    }


    /**
     * @param key      参数KEY
     * @param supplier 参数
     */
    public static void error(String key, Object... supplier) {
        log.error(key,supplier);
    }

    /**
     * @param key      参数KEY
     * @param supplier 参数
     */
    public static void error(String key, Object supplier) {
        log.error(key,supplier);
    }

    public static void error(String format, Object arg1, Object arg2){
        log.error(format,arg1,arg2);
    }


    /**
     * @param msg 参数msg
     */
    public static void error(String msg) {
        log.error(msg);
    }

}
