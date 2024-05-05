package tech.nmhillusion.eciapp.config;

import tech.nmhillusion.eciapp.config.impl.LoggingConfig;

import java.util.List;

/**
 * created by: nmhillusion
 * <p>
 * created date: 2024-05-05
 */
public class ConfigRegistry {
    private static final ConfigRegistry INSTANCE = new ConfigRegistry();
    private final List<Configurable> configList;

    private ConfigRegistry() {
        configList = List.of(
                new LoggingConfig()
        );
    }

    public static ConfigRegistry getInstance() {
        return INSTANCE;
    }

    public void applyConfigsFromRegistry() throws Exception {
        for (Configurable configurable_ : configList) {
            configurable_.configure();
        }
    }
}
