package com.zhongzhou.Excavator.dataIndex.model.item.wheel;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;

@Entity( noClassnameStored=true )
public class MatchedCorporation extends Corporation{

	private List<Wheel> matchedWheel;

	public List<Wheel> getMatchedWheel() {
		return matchedWheel;
	}

	public void setMatchedWheel(List<Wheel> matchedWheel) {
		this.matchedWheel = matchedWheel;
	}

}
