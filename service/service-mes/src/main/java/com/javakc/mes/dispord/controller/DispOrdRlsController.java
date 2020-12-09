package com.javakc.mes.dispord.controller;
import com.javakc.commonutil.api.APICODE;
import com.javakc.mes.dispord.entity.DispOrdRls;
import com.javakc.mes.dispord.service.DispOrdRlsService;
import com.javakc.mes.dispord.vo.DispOrdRlsQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "调度指令管理")
@RequestMapping("/mes/dispordrls")
@RestController
//@CrossOrigin
public class DispOrdRlsController {


    @Autowired
    DispOrdRlsService dispOrdRlsService;

    @ApiOperation("带条件的分页查询 - 调度指令管理")
    @PostMapping("{PageNum}/{PageSize}")
    public APICODE findPageDispOrdRls(@RequestBody(required = false) DispOrdRlsQuery dispOrdRlsQuery, @PathVariable int PageNum, @PathVariable int PageSize){
        System.out.println("dispOrdRlsQuery = " + dispOrdRlsQuery);
        System.out.println("PageNum = " + PageNum);
        System.out.println("PageSize = " + PageSize);
        Page<DispOrdRls> page= dispOrdRlsService.findPageDispOrdRls(dispOrdRlsQuery,PageNum,PageSize);
        long totalElements = page.getTotalElements();     //返回总数量
        List<DispOrdRls> list = page.getContent();    //得到当前页结果集
        return APICODE.OK().data("total",totalElements).data("items",list);
    }

    @ApiOperation("查询单条记录 - 调度指令管理")
    @GetMapping("{dispOrdRlsId}")
    public APICODE getById(@PathVariable int dispOrdRlsId){
        System.out.println("DispOrdRlsId = " + dispOrdRlsId);
       DispOrdRls dispOrdRls= dispOrdRlsService.getById(dispOrdRlsId);
        return APICODE.OK().data("dispOrdRls",dispOrdRls);
    }

    @ApiOperation("接收集团下达的指令信息")
    @PostMapping("savePmsDispOrdRls")
    public APICODE savePmsDispOrdRls(@RequestBody DispOrdRls dispOrdRls){
        dispOrdRlsService.saveOrUpdate(dispOrdRls);


        return APICODE.OK();
    }



}
