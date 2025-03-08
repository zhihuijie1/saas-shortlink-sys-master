package com.saas.admin.controller;

import com.saas.admin.common.convention.result.Result;
import com.saas.admin.common.convention.result.Results;
import com.saas.admin.dto.req.ShortLinkGroupSaveReqDTO;
import com.saas.admin.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController {

    @Autowired
    GroupService groupService;

    /**
     * 新增分组
     */
    @PostMapping("/api/short-link/v1/group")
    public Result<Void> save(@RequestBody ShortLinkGroupSaveReqDTO requestParam) {
        groupService.saveGroup(requestParam.getName());
        return Results.success();
    }
}
