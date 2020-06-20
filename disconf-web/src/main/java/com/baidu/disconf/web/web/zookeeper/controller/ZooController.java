package com.baidu.disconf.web.web.zookeeper.controller;

import javax.validation.Valid;

import com.baidu.disconf.core.common.zookeeper.ZookeeperMgr;
import com.baidu.disconf.web.innerapi.zookeeper.ZooKeeperDriver;
import com.baidu.disconf.web.innerapi.zookeeper.impl.ZookeeperDriverImpl;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.disconf.core.common.constants.Constants;
import com.baidu.disconf.core.common.json.ValueVo;
import com.baidu.disconf.web.service.zookeeper.config.ZooConfig;
import com.baidu.disconf.web.service.zookeeper.form.ZkDeployForm;
import com.baidu.disconf.web.service.zookeeper.service.ZkDeployMgr;
import com.baidu.disconf.web.web.config.dto.ConfigFullModel;
import com.baidu.disconf.web.web.zookeeper.validator.ZkDeployValidator;
import com.baidu.dsp.common.annotation.NoAuth;
import com.baidu.dsp.common.constant.WebConstants;
import com.baidu.dsp.common.controller.BaseController;
import com.baidu.dsp.common.vo.JsonObjectBase;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Zoo API
 *
 * @author liaoqiqi
 * @version 2014-1-20
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/zoo")
public class ZooController extends BaseController {

    protected static final Logger LOG = LoggerFactory.getLogger(ZooController.class);

    @Autowired
    private ZooConfig zooConfig;

    @Autowired
    private ZkDeployValidator zkDeployValidator;

    @Autowired
    private ZkDeployMgr zkDeployMgr;

    /**
     * 获取Zookeeper地址
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/hosts", method = RequestMethod.GET)
    @ResponseBody
    public ValueVo getHosts() {

        ValueVo confItemVo = new ValueVo();
        confItemVo.setStatus(Constants.OK);
        confItemVo.setValue(zooConfig.getZooHosts());

        return confItemVo;
    }

    /**
     * 获取ZK prefix
     *
     * @return
     */
    @NoAuth
    @RequestMapping(value = "/prefix", method = RequestMethod.GET)
    @ResponseBody
    public ValueVo getPrefixUrl() {

        ValueVo confItemVo = new ValueVo();
        confItemVo.setStatus(Constants.OK);
        confItemVo.setValue(zooConfig.getZookeeperUrlPrefix());

        return confItemVo;
    }

    /**
     * 获取ZK 部署情况
     *
     * @param zkDeployForm
     *
     * @return
     */
    @RequestMapping(value = "/zkdeploy", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase getZkDeployInfo(@Valid ZkDeployForm zkDeployForm) {

        LOG.info(zkDeployForm.toString());

        ConfigFullModel configFullModel = zkDeployValidator.verify(zkDeployForm);

        String data = zkDeployMgr.getDeployInfo(configFullModel.getApp().getName(), configFullModel.getEnv().getName(),
                zkDeployForm.getVersion());

        return buildSuccess("hostInfo", data);
    }

    /**
     * 同步至ZK中
     * @param zkDeployForm
     * @return
     */
    @RequestMapping(value = "/syncToZK", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase syncToZK(@Valid ZkDeployForm zkDeployForm){
        LOG.info("同步配置到zk中请求参数:{}",zkDeployForm.toString());
        ConfigFullModel configFullModel = zkDeployValidator.verify(zkDeployForm);
        boolean b = zkDeployMgr.SyncToZk(configFullModel);
        return buildSuccess(b);
    }

    public static void main(String[] args)throws Exception {
        String url="localhost:2181";
        String prefix="/disconf/file_0.0.1_rd";

        ZookeeperMgr instance = ZookeeperMgr.getInstance();
        instance.init(url,prefix,true);
        List<String> children = instance.getZk()
                .getChildren(prefix+"/item", false);
    for(String a :children){
        instance.deleteNode(prefix+"/item/"+a);
        System.out.println(a);
    }
        TimeUnit.SECONDS.sleep(20);
    }
}
