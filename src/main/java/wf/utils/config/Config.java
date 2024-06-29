package wf.utils.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import wf.utils.config.utils.ThreadMultipleLoopTask;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;


public class Config<T> {
    private static HashMap<Integer, ThreadMultipleLoopTask> autoSaveLoopTaskMap;


    private final File file;
    private final ObjectMapper objectMapper;

    private ThreadMultipleLoopTask autoSaveLoopTask;
    private T o;



    public Config(String path) {
        this(path, ConfigType.YAML, 300, false);
    }

    public Config(String path, ConfigType configType, int second, boolean unique) {
        this.file = new File(path);
        this.objectMapper = createObjectMapper(configType);

        if(second > 0)
            autoSaveInit(second, unique);
    }



    public T load(Class<T> clazz) {
        if(file.exists()) {
            try {
                o = objectMapper.readValue(file, clazz);
                return o;
            }
            catch (IOException e) { throw new RuntimeException(e); }
        } else {
            try {
                //noinspection ResultOfMethodCallIgnored
                file.getParentFile().mkdirs();
                if(!file.createNewFile())
                    throw new RuntimeException("Error on create file: " + file.getName());
            }
            catch (IOException e) { throw new RuntimeException(e); }

            try {
                Constructor<T> constructor = clazz.getConstructor();
                o = constructor.newInstance();
                save();
                return o;
            }
            catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) { throw new RuntimeException(e); }
        }
    }

    public void save() {
        if(o == null) return;

        try { objectMapper.writeValue(file, o); }
        catch (IOException e) { throw new RuntimeException(e); }
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
        } else {
            autoSaveLoopTask = new ThreadMultipleLoopTask(seconds * 1000L, seconds * 1000L);
            autoSaveLoopTask.addRunnable(file.getAbsolutePath(), this::save);
            autoSaveLoopTask.start();
        }
    }


    private ObjectMapper createObjectMapper(ConfigType configType) {
        ObjectMapper objectMapper = new ObjectMapper(getMapperFactory(configType));

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);

        return objectMapper;
    }

    private JsonFactory getMapperFactory(ConfigType configType) {
        switch (configType) {
            case YAML: return new YAMLFactory();
            case JSON: return new JsonFactory();
            default: return new MappingJsonFactory();
        }
    }

    public static <C> ConfigBuilder<C> builder(Class<C> clazz) {
        return new ConfigBuilder<C>();
    }

    public void stopAutoSave(){
        if(autoSaveLoopTask != null) autoSaveLoopTask.stop();
    }

    public void startAutoSave(){
        if(autoSaveLoopTask != null) autoSaveLoopTask.start();
    }


    public static HashMap<Integer, ThreadMultipleLoopTask> getAutoSaveLoopTaskMap() {
        return autoSaveLoopTaskMap;
    }


    public File getFile() {
        return file;
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public ThreadMultipleLoopTask getAutoSaveLoopTask() {
        return autoSaveLoopTask;
    }

    public void setAutoSaveLoopTask(ThreadMultipleLoopTask autoSaveLoopTask) {
        this.autoSaveLoopTask = autoSaveLoopTask;
    }
}
