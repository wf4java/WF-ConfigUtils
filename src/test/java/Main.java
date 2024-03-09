import wf.utils.yamlconfiguration.configuration.Config;

public class Main {


    public static void main(String[] args) {

        Config config = new Config("config.yml");

        config.set("int", 5);
        System.out.println(config.get("int"));

        config.save();

    }


}
