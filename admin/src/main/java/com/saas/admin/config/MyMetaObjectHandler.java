package com.saas.admin.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 通用持久层对象字段自动填充
 * 主要填充 createtime 和 updatetime
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    // 插入数据时自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("### 开始插入填充 createtime, updatetime, deflag ###");

        // strict严格模式填充createTime字段，像严格的门卫：必须确保字段存在且类型匹配才会盖章
        // 如果字段不存在会报错，避免填错字段，一下都是
        this.strictInsertFill(metaObject, "createTime", Date::new, Date.class);
        this.strictInsertFill(metaObject, "updateTime", Date::new, Date.class);
        this.strictInsertFill(metaObject, "delFlag", () -> 0, Integer.class);
    }

    // 更新数据时自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        strictInsertFill(metaObject, "updateTime", Date::new, Date.class);
    }
}
