package com.baidu.disconf.web.service.zookeeper.service.impl;

import com.baidu.disconf.core.common.constants.DisConfigTypeEnum;
import com.baidu.disconf.core.common.path.ZooPathMgr;
import com.baidu.disconf.core.common.zookeeper.ZookeeperMgr;
import com.baidu.disconf.web.innerapi.zookeeper.ZooKeeperDriver;
import com.baidu.disconf.web.service.config.bo.Config;
import com.baidu.disconf.web.service.config.service.ConfigMgr;
import com.baidu.disconf.web.service.zookeeper.config.ZooConfig;
import com.baidu.disconf.web.service.zookeeper.dto.ZkDisconfData;
import com.baidu.disconf.web.service.zookeeper.service.ZkDeployMgr;
import com.baidu.disconf.web.web.config.dto.ConfigFullModel;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author liaoqiqi
 * @version 2014-9-11
 */
@Service
public class ZkDeployMgrImpl implements ZkDeployMgr {
    protected static final Logger LOG = LoggerFactory.getLogger(ZkDeployMgrImpl.class);
    @Autowired
    private ZooKeeperDriver zooKeeperDriver;

    @Autowired
    private ZooConfig zooConfig;

    @Autowired
    private ConfigMgr configMgr;


    /**
     * 获取ZK的详细部署信息
     */
    @Override
    public String getDeployInfo(String app, String env, String version) {
        // 路径获取
        String url = ZooPathMgr.getZooBaseUrl(zooConfig.getZookeeperUrlPrefix(), app, env, version);

        List<String> hostInfoList = zooKeeperDriver.getConf(url);

        return StringUtils.join(hostInfoList, '\n');
    }

    /**
     * 获取每个配置级别的Map数据, Key是配置, Value是ZK配置数据
     *
     * @return
     */
    @Override
    public Map<String, ZkDisconfData> getZkDisconfDataMap(String app, String env, String version) {

        return zooKeeperDriver.getDisconfData(app, env, version);
    }


    @Override
    public ZkDisconfData getZkDisconfData(String app, String env, String version, DisConfigTypeEnum disConfigTypeEnum,
                                          String keyName) {

        return zooKeeperDriver.getDisconfData(app, env, version, disConfigTypeEnum, keyName);
    }


    /**
     * 同步配置至zk
     * @param configFullModel
     * @return
     */
    @Override
    public boolean SyncToZk(ConfigFullModel configFullModel){
        ZookeeperMgr instance = ZookeeperMgr.getInstance();
        String path = "/disconf/" + configFullModel.getApp().getName() + "_"
                + configFullModel.getVersion() + "_" + configFullModel.getEnv().getName() + "/item";
        LOG.info("Sync Path:{}",path);
        try {
            if(!instance.exists(path)){
                instance.writePersistentUrl(path.substring(0,path.lastIndexOf("/")),configFullModel.getApp().getName());
                instance.writePersistentUrl(path,configFullModel.getApp().getName()+"_"+configFullModel.getEnv());
            }
            List<Config> configList = configMgr.getConfigByCondition(configFullModel.getApp().getId(), configFullModel.getEnv().getId(),
                    configFullModel.getVersion());
            if(configList.isEmpty()){
                return false;
            }
            List<String> children = instance.getZk().getChildren(path, false);
            if(!children.isEmpty()){
                for(String a :children){
                    instance.deleteNode(path+"/"+a);
                }
            }
            for(Config config:configList){
                instance.writePersistentUrl(path+"/"+config.getName(),config.getValue());
            }
            return true;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
