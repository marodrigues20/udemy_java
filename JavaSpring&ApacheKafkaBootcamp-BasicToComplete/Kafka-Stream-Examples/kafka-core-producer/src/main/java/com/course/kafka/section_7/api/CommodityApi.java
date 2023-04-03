package com.course.kafka.section_7.api;

import com.course.kafka.section_7.entity.Commodity;
import com.course.kafka.section_7.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 34. Consuming with Consumer Groups - Create Producer
 */
@RestController
@RequestMapping("/api/commodity/v1")
public class CommodityApi {

    @Autowired
    private CommodityService commodityService;
    @GetMapping(value = "/all")
    public List<Commodity> generateAllCommodities(){
        return commodityService.createDummyCommodities();
    }
}
