package singletons;


import controller.HTTPServer;
import org.slf4j.LoggerFactory;

public class Logger {
    private org.slf4j.Logger logger;
    private static Logger loggerInstance;
    public Logger(){
        logger = LoggerFactory.getLogger(HTTPServer.class);
    }

    public static enum Level {
        TRACE, DEBUG, INFO, WARN, ERROR
    }

    public static Level defaultLevel = Level.INFO;

    public static Logger  getInstance()
    {
        if (loggerInstance == null)
            loggerInstance = new Logger();
        return loggerInstance;
    }

    public void setLoggingLevel(Level level){
        defaultLevel = level;
    }

    public void log(String txt, Level level){
        if (level == null){
            switch (defaultLevel) {
                case TRACE:
                    logger.trace(txt);
                    break;
                case DEBUG:
                    logger.debug(txt);
                    break;
                case INFO:
                    logger.info(txt);
                    break;
                case WARN:
                    logger.warn(txt);
                    break;
                case ERROR:
                    logger.error(txt);
                    break;
            }
        }else {
            switch (level) {
                case TRACE:
                    logger.trace(txt);
                    break;
                case DEBUG:
                    logger.debug(txt);
                    break;
                case INFO:
                    logger.info(txt);
                    break;
                case WARN:
                    logger.warn(txt);
                    break;
                case ERROR:
                    logger.error(txt);
                    break;
            }
        }
    }

}
