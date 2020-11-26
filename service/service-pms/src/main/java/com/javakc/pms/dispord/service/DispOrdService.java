package com.javakc.pms.dispord.service;
import com.javakc.commonutil.jpa.base.service.BaseService;
import com.javakc.commonutil.jpa.dynamic.SimpleSpecificationBuilder;
import com.javakc.pms.dispord.dao.DispOrdDao;
import com.javakc.pms.dispord.entity.DispOrd;
import com.javakc.pms.dispord.vo.DispOrdQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class DispOrdService extends BaseService<DispOrdDao, DispOrd > {

    @Autowired
    DispOrdDao dispOrdDao;

    //查询所有调度指令库
    public List<DispOrd> findAll() {
        return dispOrdDao.findAll();
    }



    /**
     * 根据条件进行分页查询
     * @param dispOrdQuery
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<DispOrd> findPageDispOrd(DispOrdQuery dispOrdQuery, int pageNum, int pageSize) { //查询条件    当前页    每页总条数

        SimpleSpecificationBuilder<DispOrd> simpleSpecificationBuilder = new SimpleSpecificationBuilder();      //封装JpaSpecificationExecutor 查询接口

        if (!StringUtils.isEmpty(dispOrdQuery.getOrderName())) {    //判断传入的参数是否为空，不为空则进行下一步。
            /**
             * 可用操作符
             * = 等值、!= 不等值 (字符串、数字)
             * >=、<=、>、< (数字)
             * ge，le，gt，lt(字符串)
             * :表示like %v%
             * l:表示 v%
             * :l表示 %v
             * null表示 is null
             * !null表示 is not null
             */
            simpleSpecificationBuilder.and("orderName", ":", dispOrdQuery.getOrderName());   //查询字段   ：模糊查询    查询参数
        }

        if(!StringUtils.isEmpty(dispOrdQuery.getBeginDate())){
            simpleSpecificationBuilder.and("gmtCreate","ge",dispOrdQuery.getBeginDate());
        }

        if(!StringUtils.isEmpty(dispOrdQuery.getEndDate())){
            simpleSpecificationBuilder.and("gmtCreate","lt",dispOrdQuery.getEndDate());
        }

        return dao.findAll(simpleSpecificationBuilder.getSpecification(), PageRequest.of(pageNum - 1, pageSize));   //返回查询数据   当前页   总页数
    }



}