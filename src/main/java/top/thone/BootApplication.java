package top.thone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

/**
 * @Author thone
 * @Description //TODO
 * @Date 4:41 PM-2019/8/31
 **/
@SpringBootApplication
public class BootApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(BootApplication.class);
        springApplication.addListeners(new ApplicationPidFileWriter());     // register PID write to spring boot. It will write PID to file
        springApplication.run(args);
    }
}
