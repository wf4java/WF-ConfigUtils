package wf.utils.yamlconfiguration.utils;


import wf.utils.yamlconfiguration.configuration.Config;

public interface ConfigSerializable<T> {

    public default T getSerializableObject(String path, Config config){
        return null;
    };
    public default void setSerializableObject(String path, Config config){

    };

}
