package com.zhongzhou.Excavator.dataIndex.model.item.wheel;

import org.mongodb.morphia.annotations.Entity;

/**
 * Created by lixiaohao on 2016/11/18
 *
 * @Description
 * @Create 2016-11-18 9:27
 * @Company
 */
@Entity( noClassnameStored=true )
public class TradeShow {
    /**xx*/
    private String tradeShowName;
    /**xx*/
    private String dateAttended;
    /**xx*/
    private String  hostCountryOrRegion;

    public TradeShow() {
    }

    public String getTradeShowName() {
        return tradeShowName;
    }

    public void setTradeShowName(String tradeShowName) {
        this.tradeShowName = tradeShowName;
    }

    public String getDateAttended() {
        return dateAttended;
    }

    public void setDateAttended(String dateAttended) {
        this.dateAttended = dateAttended;
    }

    public String getHostCountryOrRegion() {
        return hostCountryOrRegion;
    }

    public void setHostCountryOrRegion(String hostCountryOrRegion) {
        this.hostCountryOrRegion = hostCountryOrRegion;
    }

    @Override
    public String toString() {
        return "TradeShow{" +
                "tradeShowName='" + tradeShowName + '\'' +
                ", dateAttended='" + dateAttended + '\'' +
                ", hostCountryOrRegion='" + hostCountryOrRegion + '\'' +
                '}';
    }
}
