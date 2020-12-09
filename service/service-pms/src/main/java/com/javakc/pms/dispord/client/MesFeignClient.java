package com.javakc.pms.dispord.client;

import com.javakc.commonutil.api.APICODE;
import com.javakc.pms.dispord.entity.DispOrdRls;
import org.springframework.stereotype.Component;

@Component
public class MesFeignClient implements MesClient{

    @Override
    public APICODE savePmsDispOrdRls(DispOrdRls dispOrdRls) {
        return APICODE.ERROR().message("Mes 服务调用失败 - 下达");
    }
}
