package com.baidu.disconf.web.test.common;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;

@TestExecutionListeners(BaseTestBeforeClass.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@ActiveProfiles({"api-normal", "db-test"})
public class BaseNoMockTC extends AbstractTestCase {

}
