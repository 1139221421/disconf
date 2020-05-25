package com.baidu.disconf.web.service.env.form;

import com.baidu.dsp.common.form.RequestFormBase;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author liaoqiqi
 * @version 2014-1-24
 */
@Data
public class EnvNewForm extends RequestFormBase {

    /**
     *
     */
    private static final long serialVersionUID = 4329463343279659715L;

    @NotNull(message = "env.empty")
    @NotEmpty(message = "env.empty")
    private String env;
    public final static String ENV = "env";

}
