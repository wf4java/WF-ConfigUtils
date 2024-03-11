package wf.utils.yamlconfiguration.utils;





import wf.utils.yamlconfiguration.configuration.Config;

import java.util.Collection;

public class ConfigBuilder {

    private String path;
    private String resourcePath;
    private Collection<ConfigDefaultValue> defaultValues;
    private boolean autoSave = true;
    private int autoSaveSeconds = 300;
    private boolean autoSaveUnique = false;


    public ConfigBuilder setPath(String path) {
        this.path = path;
        if(this.resourcePath == null) this.resourcePath = path;
        return this;
    }

    public ConfigBuilder setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
        return this;
    }

    public ConfigBuilder setDefaultValues(Collection<ConfigDefaultValue> defaultValues) {
        this.defaultValues = defaultValues;
        return this;
    }

    public ConfigBuilder setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
        return this;
    }

    public ConfigBuilder setAutoSaveSeconds(int autoSaveSeconds) {
        this.autoSaveSeconds = autoSaveSeconds;
        return this;
    }

    public ConfigBuilder setAutoSaveUnique(boolean autoSaveUnique) {
        this.autoSaveUnique = autoSaveUnique;
        return this;
    }

    public Config build() {
        if(!autoSave) return new Config(path, resourcePath, defaultValues);
        return new Config(path, resourcePath, defaultValues, autoSaveSeconds, autoSaveUnique);
    }

}
