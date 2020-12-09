package com.javakc.pms.dispord.controller;
import com.javakc.commonutil.api.APICODE;
import com.javakc.pms.dispord.client.MesClient;
import com.javakc.pms.dispord.entity.DispOrdRls;
import com.javakc.pms.dispord.service.DispOrdRlsService;
import com.javakc.pms.dispord.vo.DispOrdRlsQuery;
import com.javakc.servicebase.handler.HctfException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(tags = "调度指令管理")
@RequestMapping("/pms/dispordrls")
@RestController
//@CrossOrigin
public class DispOrdRlsController {


    @Autowired
    DispOrdRlsService dispOrdRlsService;

    @Autowired
    MesClient mesClient;

    @ApiOperation("带条件的分页查询 - 调度指令管理")
    @PostMapping("{PageNum}/{PageSize}")
    public APICODE findPageDispOrdRls(@RequestBody(required = false) DispOrdRlsQuery dispOrdRlsQuery,@PathVariable int PageNum,@PathVariable int PageSize){
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

    @ApiOperation("下达集团调度指令")
    @GetMapping("updateRelease/{dispOrdRlsId}")
    public APICODE updateRelease(@PathVariable int dispOrdRlsId){
        DispOrdRls dispOrdRls = dispOrdRlsService.getById(dispOrdRlsId);
        dispOrdRls.setIsRelease(1);      //修改数据库为1，即为下达。
        dispOrdRls.setReleaseTime(new Date());

        //调用Mes服务接口，保存下达的任务
        APICODE apicode = mesClient.savePmsDispOrdRls(dispOrdRls);
        if (apicode.getCode() == 20001){
           throw  new HctfException(20001,apicode.getMessage());
        }
        dispOrdRlsService.saveOrUpdate(dispOrdRls);   //保存好修改后的结果
        return APICODE.OK();
    }


}
