package com.baidu.disconf.web.web.env.validator;

import com.baidu.disconf.web.service.env.bo.Env;
import com.baidu.disconf.web.service.env.form.EnvNewForm;
import com.baidu.disconf.web.service.env.service.EnvMgr;
import com.baidu.dsp.common.exception.FieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 权限验证
 *
 * @author liaoqiqi
 * @version 2014-7-2
 */
@Component
public class EnvValidator {

    @Autowired
    private EnvMgr envMgr;

    /**
     * 验证创建
     */
    public void validateCreate(EnvNewForm envNewForm) {

        // trim
        if (envNewForm.getEnv() != null) {
            envNewForm.setEnv(envNewForm.getEnv().trim());
        }

        Env env = envMgr.getByName(envNewForm.getEnv());
        if (env != null) {
            throw new FieldException(EnvNewForm.ENV, "env.exist", null);
        }
    }

}
