package com.javakc.pms.dispord.controller;
import com.javakc.commonutil.api.APICODE;
import com.javakc.pms.dispord.entity.DispOrd;
import com.javakc.pms.dispord.service.DispOrdService;
import com.javakc.pms.dispord.vo.DispOrdQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "调度指令库管理")
@RestController
@RequestMapping("/pms/dispord")
@CrossOrigin
public class DispOrdController {


    @Autowired
    private DispOrdService dispOrdService;


    /**
     * 查询所有调度指令库
     * @return 全部数据
     */
    @ApiOperation(value = "查询所有指令库")
    @GetMapping
    public List<DispOrd> findAll() {
        return dispOrdService.findAll();
    }


    @ApiOperation(value = "统一数据格式返回测试")
    @GetMapping("test")
    public APICODE test() {
        List<DispOrd> dispOrdList = dispOrdService.findAll();
        return APICODE.OK().data("items", dispOrdList);
    }


    @ApiOperation(value = "根据条件进行分页查询")
    @PostMapping("{pageNum}/{pageSize}")
    public APICODE findPageDispOrd(@RequestBody(required = false) DispOrdQuery dispOrdQuery,@PathVariable int pageNum, @PathVariable int pageSize) {    //传入查询条件    当前页  每页总条数
        Page<DispOrd> page = dispOrdService.findPageDispOrd(dispOrdQuery, pageNum, pageSize);
        long totalElements = page.getTotalElements();     //返回总数量
        List<DispOrd> dispOrdList = page.getContent();    //得到当前页结果集
        return APICODE.OK().data("total", totalElements).data("items", dispOrdList);     //统一返回具体数据
    }


    @ApiOperation(value = "新增调度指令库")
    @PostMapping("saveDispOrd")
    public APICODE saveDispOrd(@RequestBody DispOrd dispOrd) {     //传入Json数据格式的DispOrd对象进行新增
        dispOrdService.saveOrUpdate(dispOrd);
        return APICODE.OK();
    }


    @ApiOperation(value = "根据ID获取调度指令库")
    @GetMapping("{dispOrdId}")
    public APICODE getDispOrdById(@PathVariable int dispOrdId) {
        DispOrd dispOrd = dispOrdService.getById(dispOrdId);
        return APICODE.OK().data("dispOrd", dispOrd);
    }

    @ApiOperation(value = "修改调度指令库")
    @PutMapping("updateDispOrd")
    public APICODE updateDispOrd(@RequestBody DispOrd dispOrd) {     //传入Json数据格式的DispOrd对象替换原有数据
        dispOrdService.saveOrUpdate(dispOrd);
        return APICODE.OK();
    }

    @ApiOperation(value = "根据ID删除调度指令库")
    @DeleteMapping("{dispOrdId}")
    public APICODE deleteDispOrdById(@PathVariable int dispOrdId) {
        dispOrdService.removeById(dispOrdId);
        return APICODE.OK();
    }


}