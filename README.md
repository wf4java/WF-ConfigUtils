# WF-ConfigUtils:
## Maven:
`Java(min): 1.8`
```xml
  <dependency>
    <groupId>io.github.wf4java</groupId>
    <artifactId>WF-ConfigUtils</artifactId>
    <version>3.1.0</version>
  </dependency>
```

## Example:
```java
Config<Pojo> config = Config.builder(Pojo.class)
        .setPath("settings.yml")
        .setAutoSave(true)
        .setAutoSaveSeconds(300)
        .setConfigType(ConfigType.YAML)
        .build();

Pojo pojo = config.load(Pojo.class);

System.out.println(pojo.getPoint());
System.out.println(pojo.getMessage());


pojo.setMessage("New message")
config.save();




@Getter
@Setter
public static class Pojo {

    private String message = "Test message";

    private Point point = new Point(20, 50);

}

```
