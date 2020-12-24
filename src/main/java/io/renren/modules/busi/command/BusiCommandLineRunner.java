package io.renren.modules.busi.command;

import io.renren.modules.sys.service.SetupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BusiCommandLineRunner implements CommandLineRunner {

    @Autowired
    private SetupService setupService;

    @Override
    public void run(String... args) throws Exception {
        log.debug("初始化系统参数");

        log.debug("crm服务启动成功");
    }
}
