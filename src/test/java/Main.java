import wf.utils.config.Config;
import wf.utils.config.ConfigBuilder;
import wf.utils.config.ConfigType;

import java.awt.*;

public class Main {


    public static void main(String[] args) {
        Config<Pojo> config = Config.builder(Pojo.class)
                .setPath("settings.yml")
                .setAutoSave(true)
                .setAutoSaveSeconds(300)
                .setAutoSaveUnique(true)
                .setConfigType(ConfigType.YAML)
                .build();

        Pojo pojo = config.load(Pojo.class);


        System.out.println(pojo);
    }



    public static class Pojo {

        private String message = "test_message";

        private Point point = new Point(20, 50);

    }


}
