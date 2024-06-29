package wf.utils.config;

public class ConfigBuilder<T> {

    private String path;
    private boolean autoSave = true;
    private int autoSaveSeconds = 300;
    private boolean autoSaveUnique = true;
    private ConfigType configType = ConfigType.YAML;


    public ConfigBuilder<T> setPath(String path) {
        this.path = path;
        return this;
    }

    public ConfigBuilder<T> setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
        return this;
    }

    public ConfigBuilder<T> setAutoSaveSeconds(int autoSaveSeconds) {
        this.autoSaveSeconds = autoSaveSeconds;
        return this;
    }

    public ConfigBuilder<T> setAutoSaveUnique(boolean autoSaveUnique) {
        this.autoSaveUnique = autoSaveUnique;
        return this;
    }

    public ConfigBuilder<T> setConfigType(ConfigType configType) {
        this.configType = configType;
        return this;
    }


    public Config<T> build() {
        return new Config<T>(path, configType, autoSave ? autoSaveSeconds : -1, autoSaveUnique);
    }

}
