package com.vsarzhynskyi.shop.items.demo.functionaltests;

import com.vsarzhynskyi.shop.items.demo.Application;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {
                Application.class, FunctionalTestConfiguration.class
        })
@ActiveProfiles({"test"})
public abstract class BaseFunctionalTest {

    @LocalServerPort
    protected int serverPort;
    @LocalManagementPort
    protected int managementPort;

}
