package com.javakc.mes.dispord.service;
import com.javakc.commonutil.jpa.base.service.BaseService;
import com.javakc.commonutil.jpa.dynamic.SimpleSpecificationBuilder;
import com.javakc.mes.dispord.dao.DispOrdRlsDao;
import com.javakc.mes.dispord.entity.DispOrdRls;
import com.javakc.mes.dispord.vo.DispOrdRlsQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class DispOrdRlsService extends BaseService<DispOrdRlsDao, DispOrdRls> {

    @Autowired
    DispOrdRlsDao dispOrdRlsDao;



    public Page<DispOrdRls> findPageDispOrdRls(DispOrdRlsQuery dispOrdRlsQuery, int PageNum, int PageSize){

        //新建一个查询封装类
        SimpleSpecificationBuilder<DispOrdRls> simpleSpecificationBuilder=new SimpleSpecificationBuilder();

        if (!StringUtils.isEmpty(dispOrdRlsQuery.getOrderNum())) {    //判断传入的参数是否为空，不为空则进行下一步。
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
            simpleSpecificationBuilder.and("orderNum", "=", dispOrdRlsQuery.getOrderNum());   //查询字段   ：模糊查询    查询参数
        }



        //传入查询条件 当前页数  当前页最大条数   会返回一个当前页数据返回即可
        Page page= dispOrdRlsDao.findAll(simpleSpecificationBuilder.getSpecification(), PageRequest.of(PageNum-1,PageSize));

        return page;
    }

}
