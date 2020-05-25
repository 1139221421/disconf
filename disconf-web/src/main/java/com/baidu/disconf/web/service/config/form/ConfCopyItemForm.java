package com.baidu.disconf.web.service.config.form;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 新建配置项表格
 * @author liaoqiqi
 * @version 2014-7-3
 */
public class ConfCopyItemForm extends ConfNewForm {

    @NotNull(message = "version.empty")
    @NotEmpty(message = "version.empty")
    private String copyVersion;
    public static final String COPYVERSION = "copyVersion";

    @NotNull(message = "env.empty")
    private Long copyEnvId;
    public static final String COPYENVID = "copyEnvId";

    @NotNull(message = "copyAppId.empty")
    private Long copyAppId;
    public static final String COPYAPPID = "copyAppId";

    public String getCopyVersion() {
        return copyVersion;
    }

    public void setCopyVersion(String copyVersion) {
        this.copyVersion = copyVersion;
    }

    public Long getCopyEnvId() {
        return copyEnvId;
    }

    public void setCopyEnvId(Long copyEnvId) {
        this.copyEnvId = copyEnvId;
    }

    public ConfCopyItemForm(ConfNewForm confNewForm) {
        super(confNewForm.getAppId(), confNewForm.getVersion(), confNewForm.getEnvId());
    }

    public ConfCopyItemForm() {
        super();
    }

    public Long getCopyAppId() {
        return copyAppId;
    }

    public void setCopyAppId(Long copyAppId) {
        this.copyAppId = copyAppId;
    }
}
