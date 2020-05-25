package com.baidu.disconf.web.service.env.service;

import com.baidu.disconf.web.service.env.bo.Env;
import com.baidu.disconf.web.service.env.form.EnvNewForm;
import com.baidu.disconf.web.service.env.vo.EnvListVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
public interface EnvMgr {
    /**
     * @param name
     */
    Env getByName(String name);

    /**
     * @return
     */
    List<Env> getList();

    /**
     * @return
     */
    List<EnvListVo> getVoList();

    /**
     * @param ids
     *
     * @return
     */
    Map<Long, Env> getByIds(Set<Long> ids);

    /**
     * @param id
     *
     * @return
     */
    Env getById(Long id);

    /**
     * 生成一个ENV
     *
     * @param envNew
     *
     * @return
     */
    Env create(EnvNewForm envNew);
}
