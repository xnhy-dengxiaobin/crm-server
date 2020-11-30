package io.renren.modules.busi.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BusiCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.debug("crm服务启动成功");
    }
}
