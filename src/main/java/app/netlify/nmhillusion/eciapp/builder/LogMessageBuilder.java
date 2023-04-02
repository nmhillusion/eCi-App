package app.netlify.nmhillusion.eciapp.builder;

import app.netlify.nmhillusion.n2mix.type.ChainMap;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import org.slf4j.event.Level;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * date: 2023-04-02
 * <p>
 * created-by: nmhillusion
 */

public class LogMessageBuilder {
    private static final Map<Level, Paint> LOG_LEVEL__COLOR = new ChainMap<Level, Paint>()
            .chainPut(Level.INFO, Paint.valueOf("#3366ff"))
            .chainPut(Level.WARN, Paint.valueOf("#ed9600"))
            .chainPut(Level.ERROR, Paint.valueOf("#ff2222"));
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String message;
    private org.slf4j.event.Level logLevel;
    private Class<?> contextClazz;

    public String getMessage() {
        return message;
    }

    public LogMessageBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public LogMessageBuilder setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public Class<?> getContextClazz() {
        return contextClazz;
    }

    public LogMessageBuilder setContextClazz(Class<?> contextClazz) {
        this.contextClazz = contextClazz;
        return this;
    }

    public Label build() {
        final String logMessage = dateFormatter.format(new Date(Instant.now().toEpochMilli())) +
                " - [" +
                logLevel.toString() +
                "] : " +
                message +
                (null != contextClazz ? (" (" + contextClazz.getName() + ") ") : "");
        final Label logItem = new Label(logMessage);

        logItem.setTextFill(LOG_LEVEL__COLOR.get(logLevel));
        logItem.setStyle("-fx-pref-width: Infinity;");

        return logItem;
    }
}
