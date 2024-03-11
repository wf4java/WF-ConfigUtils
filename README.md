# WF-ConfigUtils:
## Maven:
`Java(min): 1.8`
```xml
  <dependency>
    <groupId>io.github.wf4java</groupId>
    <artifactId>WF-ConfigUtils</artifactId>
    <version>2.0</version>
  </dependency>
```

## Example
```java
Config config = new Config("config.yml");

config.set("int", 5);
System.out.println(config.get("int"));

config.save();
```
