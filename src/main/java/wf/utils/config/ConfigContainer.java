package wf.utils.config;

public class ConfigContainer<T> {

    private final Config<T> config;

    public ConfigContainer(Config<T> config) {
        this.config = config;
    }


    public void save() {
        config.save();
    }


    public Config<T> getConfig() {
        return config;
    }
    
}
