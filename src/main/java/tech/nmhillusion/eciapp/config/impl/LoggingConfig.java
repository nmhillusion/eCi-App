package tech.nmhillusion.eciapp.config.impl;

import tech.nmhillusion.eciapp.config.Configurable;
import tech.nmhillusion.n2mix.helper.YamlReader;
import tech.nmhillusion.n2mix.helper.log.LogHelper;
import tech.nmhillusion.pi_logger.constant.LogLevel;
import tech.nmhillusion.pi_logger.model.LogConfigModel;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
public class LoggingConfig implements Configurable {

    private <T> T getConfig(String configKey, Class<T> clazz2Cast) throws IOException {
        try (final InputStream configStream = getClass().getClassLoader().getResourceAsStream("app-config/logging.yml")) {
            return new YamlReader(configStream).getProperty(configKey, clazz2Cast);
        }
    }

    @Override
    public void configure() throws IOException {
        LogHelper.getLogger(this).info("Configuring Logging");

        final LogLevel logLevel = LogLevel.valueOf(
                getConfig("level", String.class)
        );
        final boolean isColor = getConfig("isColor", Boolean.class);

        LogHelper.getLogger(this).info(
                MessageFormat.format("Configured Logging: logLevel={0}, isColor={1}", logLevel, isColor)
        );

        final LogConfigModel piLoggerConfig = LogHelper.getDefaultPiLoggerConfig();
        piLoggerConfig
                .setColoring(isColor)
                .setLogLevel(logLevel);

        LogHelper.setDefaultPiLoggerConfig(piLoggerConfig);
    }
}
