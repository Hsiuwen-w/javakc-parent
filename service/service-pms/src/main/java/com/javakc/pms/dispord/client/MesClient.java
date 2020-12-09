package com.javakc.pms.dispord.client;

import com.javakc.commonutil.api.APICODE;
import com.javakc.pms.dispord.entity.DispOrdRls;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "service-mes",fallback = MesFeignClient.class)    //指定从哪个服务中调用功能 ，名称与被调用服务名保持一致
@Component    //设置为组件
public interface MesClient {

    //指定要调用的接口
    @PostMapping("/mes/dispordrls/savePmsDispOrdRls")
    public APICODE savePmsDispOrdRls(@RequestBody DispOrdRls dispOrdRls);
}
