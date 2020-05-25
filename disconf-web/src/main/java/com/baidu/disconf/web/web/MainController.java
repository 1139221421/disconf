package com.baidu.disconf.web.web;

import com.baidu.dsp.common.annotation.NoAuth;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转得
 */
@Controller
public class MainController {

    @RequestMapping("/")
    @NoAuth
    public String index() {
        return "index";
    }
}
