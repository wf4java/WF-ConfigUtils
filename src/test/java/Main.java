import wf.utils.yamlconfiguration.configuration.Config;

public class Main {


    public static void main(String[] args) {

        Config config = new Config("config.yml");

        config.set("example_number", 5);
        System.out.println(config.getInt("example_number"));

        config.save();

    }


}
