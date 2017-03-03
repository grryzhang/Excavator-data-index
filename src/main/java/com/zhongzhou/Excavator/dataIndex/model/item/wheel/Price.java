package com.zhongzhou.Excavator.dataIndex.model.item.wheel;

/***
 * 记录购买数量区间和区间内价格价格
 */
public class Price {

	/**购买数量*/
	private String purchaseQuantity;
	/**单价*/
	private String unitPrice;

	public Price() {
	}

	public String getPurchaseQuantity() {
		return purchaseQuantity;
	}

	public void setPurchaseQuantity(String purchaseQuantity) {
		this.purchaseQuantity = purchaseQuantity;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Override
	public String toString() {
		return "Price{" +
				"purchaseQuantity='" + purchaseQuantity + '\'' +
				", unitPrice='" + unitPrice + '\'' +
				'}';
	}
}
