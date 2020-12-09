package com.javakc.pms.dispord.controller;
import com.alibaba.excel.EasyExcel;
import com.javakc.commonutil.api.APICODE;
import com.javakc.pms.dispord.entity.DispOrd;
import com.javakc.pms.dispord.listener.ExcelListener;
import com.javakc.pms.dispord.service.DispOrdService;
import com.javakc.pms.dispord.vo.DispOrdData;
import com.javakc.pms.dispord.vo.DispOrdQuery;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "调度指令库管理")
@RestController
@RequestMapping("/pms/dispord")
//@CrossOrigin
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



    @ApiOperation(value = "列表导出", notes = "使用阿里EasyExcel导出Excel格式的列表数据")
    @GetMapping("exportEasyExcel")
    public void exportEasyExcel(HttpServletResponse response) {
        // ## 查询调度指令库  获取全部信息
        List<DispOrd> dispOrdList = dispOrdService.findAll();
        // ## 定义导出列表集合
        List<DispOrdData> dispOrdDataList = new ArrayList<>();

        for (DispOrd dispOrd : dispOrdList) {   //循环原对象信息  再添加到新对象中。
            DispOrdData dispOrdData = new DispOrdData();
            BeanUtils.copyProperties(dispOrd, dispOrdData);     //工具类：直接复制信息互传。注意！必须得全部一致。
            dispOrdDataList.add(dispOrdData);       //添加完最后  在dispOrdDataList中  就有了全部的记录信息
        }

        String fileName = "dispOrdList";   //设置文件名

        try {
            // ## 设置响应信息
            response.reset();
            response.setContentType("application/vnd.ms-excel; charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8") + ".xlsx");
            //  EasyExcel.输出（响应.获取输出流，表头信息）.表单名.输出（记录信息）
            EasyExcel.write(response.getOutputStream(), DispOrdData.class).sheet("指令列表").doWrite(dispOrdDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation(value = "列表导入", notes = "使用阿里EasyExcel导入Excel格式的列表数据")
    @PostMapping("importEasyExcel")
    public void importEasyExcel(MultipartFile file) {
        try {
            //  EasyExcel.读取（文件.获取输入流，表头信息，传入dispOrdService保存数据）.默认第一个表单.读取
            EasyExcel.read(file.getInputStream(), DispOrdData.class, new ExcelListener(dispOrdService)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}