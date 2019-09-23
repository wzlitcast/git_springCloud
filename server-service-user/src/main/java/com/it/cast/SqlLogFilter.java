package com.it.cast;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 拦截SQL日志过滤器
 */
@Data
@Component
public class SqlLogFilter extends Filter<ILoggingEvent> {
    private Level level;

    protected FilterReply onMatch = FilterReply.NEUTRAL;
    protected FilterReply onMismatch = FilterReply.NEUTRAL;

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (event.getLevel() == Level.DEBUG) {
            if(event.getMessage().contains("==>")||event.getMessage().contains("<==")){
                return FilterReply.ACCEPT;
            }
        }
        return FilterReply.DENY;
    }
}


