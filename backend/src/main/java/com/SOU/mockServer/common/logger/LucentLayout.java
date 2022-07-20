//package com.SOU.mockServer.common.logger;
//
//import ch.qos.logback.classic.pattern.ThrowableHandlingConverter;
//import ch.qos.logback.classic.pattern.ThrowableProxyConverter;
//import ch.qos.logback.classic.spi.ILoggingEvent;
//import ch.qos.logback.classic.spi.IThrowableProxy;
//import ch.qos.logback.contrib.json.JsonLayoutBase;
//
//import com.SOU.mockServer.common.message.Message;
//import com.SOU.mockServer.common.util.DateUtil;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class LucentLayout extends JsonLayoutBase<ILoggingEvent> {
//    public static final String TIMESTAMP_ATTR_NAME = "time";
//    public static final String SERVER_ATTR_NAME = "name";
//    public static final String LEVEL_ATTR_NAME = "level";
//    public static final String THREAD_ATTR_NAME = "thread";
//    public static final String MDC_ATTR_NAME = "mdc";
//    public static final String LOGGER_ATTR_NAME = "logger";
//    public static final String FORMATTED_MESSAGE_ATTR_NAME = "msg";
//    public static final String MESSAGE_ATTR_NAME = "raw-message";
//    public static final String EXCEPTION_ATTR_NAME = "exception";
//    public static final String CONTEXT_ATTR_NAME = "context";
//    public static final String LOGGED_SOURCE_ATTR_NAME = "src";
//    public static final String VPN_MESSAGE = "vpn-msg";
//
//    // static value
//    private static final String SERVER_NAME = "SOU-EXTERNAL-ACCOUNT-SERVER";
//
//    protected boolean includeLevel;
//    protected boolean includeServerName;
//    protected boolean includeThreadName;
//    protected boolean includeMDC;
//    protected boolean includeLoggerName;
//    protected boolean includeFormattedMessage;
//    protected boolean includeMessage;
//    protected boolean includeException;
//    protected boolean includeContextName;
//    protected boolean includeLoggedSourceFileInfo;
//
//    private ThrowableHandlingConverter throwableProxyConverter;
//
//    public LucentLayout() {
//        super();
//        this.includeLevel = true;
//        this.includeServerName = true;
//        this.includeLoggedSourceFileInfo = true;
//        this.includeThreadName = true;
//        this.includeMDC = true;
//        this.includeLoggerName = true;
//        this.includeFormattedMessage = true;
//        this.includeException = true;
//        this.includeContextName = true;
//        this.throwableProxyConverter = new ThrowableProxyConverter();
//    }
//
//    @Override
//    protected Map<String, Object> toJsonMap(ILoggingEvent event){
//        Map<String, Object> map = new LinkedHashMap<>();
//        String formattedTimestamp = DateUtil.convertTimestampToISODateString(event.getTimeStamp());
//        int errorLevel = event.getLevel().toInt();
//
//        add(SERVER_ATTR_NAME, this.includeServerName, SERVER_NAME, map);
//        add(TIMESTAMP_ATTR_NAME, this.includeTimestamp, formattedTimestamp, map);
//        add(LEVEL_ATTR_NAME, this.includeLevel, String.valueOf(errorLevel), map);
//        add(FORMATTED_MESSAGE_ATTR_NAME, this.includeFormattedMessage, event.getFormattedMessage(), map);
//        if(!event.getLoggerName().isEmpty()){
//            StackTraceElement errorMsgSrc = Thread.currentThread().getStackTrace()[14];
//            Map<String, Object> srcMap = new LinkedHashMap<>();
//            srcMap.put("file", errorMsgSrc.getClassName());
//            srcMap.put("line", errorMsgSrc.getLineNumber());
//            srcMap.put("func", errorMsgSrc.getMethodName());
//            addMap(LOGGED_SOURCE_ATTR_NAME, this.includeLoggedSourceFileInfo, srcMap, map);
//        }
//        add(THREAD_ATTR_NAME, this.includeThreadName, event.getThreadName(), map);
//        addMap(MDC_ATTR_NAME, this.includeMDC, event.getMDCPropertyMap(), map);
//        add(LOGGER_ATTR_NAME, this.includeLoggerName, event.getLoggerName(), map);
//        add(CONTEXT_ATTR_NAME, this.includeContextName, event.getLoggerContextVO().getName(), map);
//        add(MESSAGE_ATTR_NAME, this.includeMessage, event.getMessage(), map);
//        addThrowableInfo(EXCEPTION_ATTR_NAME, this.includeException, event, map);
//
//        Object[] args = event.getArgumentArray();
//        if (args != null) {
//            for (int i = 0; i < args.length; i++) {
//                Object arg = args[i];
//                if (arg instanceof Message) {
//                    Message message = (Message) arg;
//                    Map<String, Object> body = message.toMap();
//
//                    if (body != null) {
//                        String messageName = message.getClass().getSimpleName();
//                        Map<String, Object> totalMap = new LinkedHashMap<>();
//                        totalMap.put("name", messageName);
//                        totalMap.put("msg", body);
//
//                        addMap(VPN_MESSAGE, true, totalMap, map);
//                    }
//                }
//            }
//        }
//
//        return map;
//    }
//
//    protected void addThrowableInfo(String fieldName, boolean field, ILoggingEvent value, Map<String, Object> map) {
//        if (field && value != null) {
//            IThrowableProxy throwableProxy = value.getThrowableProxy();
//            if (throwableProxy != null) {
//                String ex = throwableProxyConverter.convert(value);
//                if (ex != null && !ex.equals("")) {
//                    map.put(fieldName, ex);
//                }
//            }
//        }
//    }
//
//    public boolean isIncludeLevel() {
//        return includeLevel;
//    }
//
//    public void setIncludeLevel(boolean includeLevel) {
//        this.includeLevel = includeLevel;
//    }
//
//    public boolean isIncludeLoggerName() {
//        return includeLoggerName;
//    }
//
//    public void setIncludeLoggerName(boolean includeLoggerName) {
//        this.includeLoggerName = includeLoggerName;
//    }
//
//    public boolean isIncludeFormattedMessage() {
//        return includeFormattedMessage;
//    }
//
//    public void setIncludeFormattedMessage(boolean includeFormattedMessage) {
//        this.includeFormattedMessage = includeFormattedMessage;
//    }
//
//    public boolean isIncludeMessage() {
//        return includeMessage;
//    }
//
//    public void setIncludeMessage(boolean includeMessage) {
//        this.includeMessage = includeMessage;
//    }
//
//    public boolean isIncludeMDC() {
//        return includeMDC;
//    }
//
//    public void setIncludeMDC(boolean includeMDC) {
//        this.includeMDC = includeMDC;
//    }
//
//    public boolean isIncludeThreadName() {
//        return includeThreadName;
//    }
//
//    public void setIncludeThreadName(boolean includeThreadName) {
//        this.includeThreadName = includeThreadName;
//    }
//
//    public boolean isIncludeException() {
//        return includeException;
//    }
//
//    public void setIncludeException(boolean includeException) {
//        this.includeException = includeException;
//    }
//
//    public boolean isIncludeContextName() {
//        return includeContextName;
//    }
//
//    public void setIncludeContextName(boolean includeContextName) {
//        this.includeContextName = includeContextName;
//    }
//
//    public void setLoggedSourceAttrName(boolean includeLoggedSourceFileInfo){
//        this.includeLoggedSourceFileInfo = includeLoggedSourceFileInfo;
//    }
//
//    public boolean isIncludeLoggedSourceFileInfo() {
//        return includeLoggedSourceFileInfo;
//    }
//
//    public boolean isIncludeServerName() {
//        return includeServerName;
//    }
//
//
//    public ThrowableHandlingConverter getThrowableProxyConverter() {
//        return throwableProxyConverter;
//    }
//
//    public void setThrowableProxyConverter(ThrowableHandlingConverter throwableProxyConverter) {
//        this.throwableProxyConverter = throwableProxyConverter;
//    }
//}