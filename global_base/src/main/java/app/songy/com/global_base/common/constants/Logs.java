package app.songy.com.global_base.common.constants;

import app.songy.com.global_base.common.helper.LogHelper;
import app.songy.com.global_base.common.log.LogScheduler;

/**
 * Description:
 * Created by song on 2018/6/7.
 * email：bjay20080613@qq.com
 */

public interface Logs {
    /**
     * 网络相关
     */
    LogScheduler network = LogHelper.network();

    /**
     * 异步任务相关
     */
    LogScheduler task = LogHelper.task();

    /**
     * 图片相关
     */
    LogScheduler img = LogHelper.img();

    /**
     * 基础库
     */
    LogScheduler base = LogHelper.instance("base");

    /**
     * component库
     */
    LogScheduler component = LogHelper.instance("component");
    LogScheduler file = LogHelper.instance("file");

    /**
     * 用户相关的
     */
    LogScheduler user = LogHelper.instance("user");

    /**
     * 推送相关
     */
    LogScheduler push = LogHelper.instance("UPush");

    /**
     * h5相关的日志
     */
    LogScheduler h5 = LogHelper.instance("web");

    /**
     * 默认
     */
    LogScheduler defaults = LogHelper.instance("defaults");



    /**
     * 系统
     * */
    LogScheduler sys = LogHelper.instance("Sys");



    /**
     * 定位服务相关
     */
    LogScheduler location = LogHelper.instance("Location ");

    LogScheduler track = LogHelper.instance("Track");

    LogScheduler crash = LogHelper.instance("Crash");

    /**
     * 服务器环境
     * */
    LogScheduler server = LogHelper.instance("Server");

    LogScheduler dynamic  = LogHelper.instance("Dynamic");
}
