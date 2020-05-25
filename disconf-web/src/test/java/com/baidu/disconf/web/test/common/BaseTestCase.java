package com.baidu.disconf.web.test.common;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;

/**
 * 所有测试类的基类
 * 
 * @author liaoqiqi
 * @version 2013-12-13
 */
@TestExecutionListeners(BaseTestBeforeClass.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@ActiveProfiles({ "db-test" })
public class BaseTestCase extends AbstractTestCase {

}
