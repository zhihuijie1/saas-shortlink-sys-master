package com.saas.admin.controller;

import com.saas.admin.common.convention.result.Result;
import com.saas.admin.common.convention.result.Results;
import com.saas.admin.dto.req.ShortLinkGroupSaveReqDTO;
import com.saas.admin.dto.req.ShortLinkGroupSortReqDTO;
import com.saas.admin.dto.req.ShortLinkGroupUpdateReqDTO;
import com.saas.admin.dto.resp.ShortLinkGroupRespDTO;
import com.saas.admin.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 查询分组列表
     */
    @GetMapping("/api/short-link/v1/group")
    public Result<List<ShortLinkGroupRespDTO>> listGroup() {
        return Results.success(groupService.listGroup());
    }

    /**
     * 分组修改功能
     */
    @PutMapping("/api/short-link/v1/group")
    public Result<Void> updateGroup(@RequestBody ShortLinkGroupUpdateReqDTO requestParam) {
        groupService.updateGroup(requestParam);
        return Results.success();
    }

    /**
     * 删除短链接分组
     */
    @DeleteMapping("/api/short-link/v1/group")
    public Result<Void> updateGroup(@RequestParam String gid) {
        groupService.deleteGroup(gid);
        return Results.success();
    }

    /**
     * 分组排序 - 前端将派好序的分组(链表)传给后端，后端插入到数据库
     */
    @PostMapping("/api/short-link/v1/group/sort")
    public Result<Void> sortGroup(@RequestBody List<ShortLinkGroupSortReqDTO> requestParam) {
        groupService.sortGroup(requestParam);
        return Results.success();
    }
}

//8c748d1e-e97a-4c10-a9c6-701556f3ef67
