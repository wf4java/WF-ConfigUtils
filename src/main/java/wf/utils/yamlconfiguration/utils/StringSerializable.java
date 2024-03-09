package wf.utils.yamlconfiguration.utils;

public interface StringSerializable <T extends StringSerializable> {


    public T getSerializableObject(String s);
    public String getSerializableString();


}
