package wf.utils.yamlconfiguration.configuration;




import wf.utils.yamlconfiguration.file.FileConfiguration;
import wf.utils.yamlconfiguration.file.YamlConfiguration;
import wf.utils.yamlconfiguration.utils.*;
import wf.utils.yamlconfiguration.utils.thread.ThreadMultipleLoopTask;
import wf.utils.yamlconfiguration.utils.types.IntegerInRange;
import wf.utils.yamlconfiguration.utils.types.IntegerRandom;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;


public class Config {

    public File file;
    public FileConfiguration config;
    private ThreadMultipleLoopTask autoSaveLoopTask;

    private static HashMap<Integer, ThreadMultipleLoopTask> autoSaveLoopTaskMap;


    public Config(String path, String resourcePath){
        try {
            file = new File(path);
            if (!file.exists()) {
                InputStream link = (Config.class.getResourceAsStream("/" + resourcePath));
                file.getAbsoluteFile().getParentFile().mkdirs();

                if(link == null) file.getAbsoluteFile().createNewFile();
                else Files.copy(link, file.getAbsoluteFile().toPath());
            }
            config = YamlConfiguration.loadConfiguration(file);
        }catch (IOException e) { e.printStackTrace(); }
    }

    public Config(String path, String resourcePath, Collection<ConfigDefaultValue> defaultValues){
        this(path, resourcePath);
        if(defaultValues != null && !defaultValues.isEmpty()){
            setDefaultValues(defaultValues);
            save();
        }
    }

    public Config(String path){
        try {
            file = new File(path);
            if (!file.exists()) {
                InputStream link = (Config.class.getResourceAsStream(path));
                file.getAbsoluteFile().getParentFile().mkdirs();

                if(link == null) file.getAbsoluteFile().createNewFile();
                else Files.copy(link, file.getAbsoluteFile().toPath());
            }
            config = YamlConfiguration.loadConfiguration(file);
        }catch (IOException e) { e.printStackTrace(); }
    }

    public Config(String path, String resourcePath, Collection<ConfigDefaultValue> defaultValues, int autoSaveSeconds, boolean autoSaveUnique){
        try {
            file = new File(path);
            if (!file.exists()) {
                InputStream link = (Config.class.getResourceAsStream("/" + resourcePath));
                file.getAbsoluteFile().getParentFile().mkdirs();

                if(link == null) file.getAbsoluteFile().createNewFile();
                else Files.copy(link, file.getAbsoluteFile().toPath());
            }
            config = YamlConfiguration.loadConfiguration(file);
        }catch (IOException e) { e.printStackTrace(); }
        if(defaultValues != null && !defaultValues.isEmpty()){
            setDefaultValues(defaultValues);
            save();
        }
        autoSaveInit(autoSaveSeconds, autoSaveUnique);
    }

    public Config(String path, Collection<ConfigDefaultValue> defaultValues){
        try {
            file = new File(path);
            if (!file.exists()) {
                InputStream link = (Config.class.getResourceAsStream(path));
                file.getAbsoluteFile().getParentFile().mkdirs();

                if(link == null) file.createNewFile();
                else Files.copy(link, file.getAbsoluteFile().toPath());
            }
            config = YamlConfiguration.loadConfiguration(file);
        }catch (IOException e) { e.printStackTrace(); }
        if(defaultValues != null && !defaultValues.isEmpty()){
            setDefaultValues(defaultValues);
            save();
        }
    }


    private void autoSaveInit(int seconds, boolean unique) {
        if(!unique) {
            if(autoSaveLoopTaskMap == null) autoSaveLoopTaskMap = new HashMap<>();

            ThreadMultipleLoopTask task = autoSaveLoopTaskMap.get( seconds);
            if(task == null){
                task = new ThreadMultipleLoopTask(seconds * 1000L, seconds * 1000L);
                autoSaveLoopTaskMap.put(seconds, task);
            }
            autoSaveLoopTask = task;
            autoSaveLoopTask.addRunnable(file.getAbsolutePath(), this::save);
            autoSaveLoopTask.start();
        }else {
            autoSaveLoopTask = new ThreadMultipleLoopTask(seconds * 1000L, seconds * 1000L);
            autoSaveLoopTask.addRunnable(file.getAbsolutePath(), this::save);
            autoSaveLoopTask.start();
        }
    }

    public void stopAutoSave(){
        if(autoSaveLoopTask != null) autoSaveLoopTask.stop();
    }

    public void startAutoSave(){
        if(autoSaveLoopTask != null) autoSaveLoopTask.start();
    }


    public void setDefaultValues(boolean replace, Collection<ConfigDefaultValue> defaultValues){
        for(ConfigDefaultValue value : defaultValues){
            if(replace && config.contains(value.getPath())) continue;
            config.set(value.getPath(), value.getValue());
        }
    }

    public static ConfigBuilder builder() {
        return new ConfigBuilder();
    }

    public void set(String path, Object value){
        config.set(path, value);
    }
    public boolean contains(String path){
        return config.contains(path);
    }
    public void setDefaultValues(Collection<ConfigDefaultValue> defaultValues){
        setDefaultValues(false, defaultValues);
    }



    public void save(){
        try { config.save(file); } catch (IOException e) { throw new RuntimeException(e); }
    }

    public FileConfiguration getConfig(){ return config; }

    public String getString(String path){ return config.getString(path); }
    public int getInt(String path){ return config.getInt(path); }
    public long getLong(String path){ return config.getLong(path); }
    public double getDouble(String path){ return config.getDouble(path); }
    public boolean getBoolean(String path){ return config.getBoolean(path); }

    public List<String> getStringList(String path){ return config.getStringList(path); }
    public List<Long> getLongList(String path){ return config.getLongList(path); }
    public List<Double> getDoubleList(String path){ return config.getDoubleList(path); }
    public List<Boolean> getBooleanList(String path){ return config.getBooleanList(path); }

    public List<Byte> getByteList(String path){ return config.getByteList(path); }
    public List<Float> getFloatList(String path){ return config.getFloatList(path); }

    public Object get(String path){ return config.get(path); }
    public <T> T getObject(String path, Class<T> type){ return config.getObject(path, type); }

    public String getString(String path, String def){ return config.getString(path, def); }
    public int getInt(String path, int def){ return config.getInt(path, def); }
    public long getLong(String path, long def){ return config.getLong(path, def); }
    public double getDouble(String path, double def){ return config.getDouble(path, def); }
    public boolean getBoolean(String path, boolean def){ return config.getBoolean(path, def); }


    public Object get(String path, Object def){ return config.get(path, def); }
    public <T> T getObject(String path, Class<T> type, T def){ return config.getObject(path, type, def); }


    public ConfigurationSection getConfigurationSection(String path){ return config.getConfigurationSection(path); }

    public void set(String path, StringSerializable value){
        set(path, value.getSerializableString());
    }


    public <T extends StringSerializable> T get(String path, T value){
        return (T) value.getSerializableObject(getString(path));
    }



    public void forEach(String path, Consumer<String> consumer){
        forEach(path,false, consumer);
    }

    public void forEach(String path, boolean deap, Consumer<String> consumer){
        for(String s : getConfigurationSection(path).getKeys(deap)) consumer.accept(s);
    }




    public void set(String path, IntegerRandom value){
        set(path, value.getSerializableString());
    }
    public IntegerRandom getIntegerRandom(String path){
        return new IntegerRandom().getSerializableObject(config.getString(path));
    }

    public void set(String path, IntegerInRange value){
        set(path, value.getSerializableString());
    }
    public IntegerInRange getIntegerInRange(String path){
        return new IntegerInRange().getSerializableObject(config.getString(path));
    }


}
