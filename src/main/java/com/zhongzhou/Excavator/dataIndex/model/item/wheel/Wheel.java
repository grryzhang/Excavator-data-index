package com.zhongzhou.Excavator.dataIndex.model.item.wheel;

import org.mongodb.morphia.annotations.Entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Wheel {
	/**唯一标识*/
	private String id;
	/**该产品所在page的id*/
	private String parentId;
	private String originalUrl;
	/**页码数*/
	private int currentPage;
	private int  index;
	private String modelClassName;
	private Class modelClass;
	private long insertTime;
	/**图片id*/
	private List<String> imageId;
	/**网页资源id*/
	private String pageId;
	/**产品名称*/
	private String name;
	/**产品区间价格*/
	private List<Price> price;
	/**最小订单量*/
	private String minOrderQuantity;
	/**隔岸价格*/
	private String fobPrice;
	/**产品图片*/
	private byte[] img;
	/** Material */
	private String material;
	/** wheelHubDiameter Diameter */
	private String wheelHubDiameter;
	/**  xxx */
	private String certification;
	/**港口*/
	private String port;
	/**轮毂孔距*/
	private String pcd;
	/**尺寸*/
	private String size;
	/**装运港*/
	private String loadingPort;
	/**海关编码*/
	private String hsCode;
	/**  xxx */
	private String specification;
	/**  ET-Offset-偏距 */
	private String et;
	/**  xxx */
	private String cbd;
	/**  xxx */
	private String origin;
	/**  xxx */
	private  String width;
	/**  xxx */
	private String hole;
	/**  xxx */
	private String paymentTerms;
	/**  xxx */
	private String payment;
	/**  xxx */
	private String transportPackage;
	/**  xxx */
	private String packing;
	/**  xxx */
	private String productionCapacity;
	/**  xxx */
	private String certificate;
	/**  xxx */
	private String standard;
	/**  xxx */
	private String finishing;
	/**  xxx */
	private String type;
	/**  xxx */
	private String driveWheel;
	/**  xxx */
	private String wheelAccessories;
	/**  颜色 */
	private String color;
	/**  xxx */
	private String front;
	/**  xxx */
	private String rear;
	/**  xxx */
	private String wheelHubBrand;
	/**  xxx */
	private String trademark;
	/**  xxx */
	private String warranty;
	/**  xxx */
	private String painting;
	/**产品描述*/
	private String productDescription;
	/**公司主页地址*/
	private String corporationResourceUrl;
	private String resourcsUrl;
	//后续添加字段
	private String weight;
	/**process*/
	private String process;
	/**数据原始网站*/
	private String dataSource;
	public Wheel() {
		insertTime = new Date().getTime();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getOriginalUrl() {
		return originalUrl;
	}

	public void setOriginalUrl(String originalUrl) {
		this.originalUrl = originalUrl;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<String> getImageId() {
		return imageId;
	}

	public void setImageId(List<String> imageId) {
		this.imageId = imageId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getModelClassName() {
		return modelClassName;
	}

	public void setModelClassName(String modelClassName) {
		this.modelClassName = modelClassName;
	}

	public Class getModelClass() {
		return modelClass;
	}

	public void setModelClass(Class modelClass) {
		this.modelClass = modelClass;
	}

	public long getInsertTime() {
		return insertTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Price> getPrice() {
		return price;
	}

	public void setPrice(List<Price> price) {
		this.price = price;
	}

	public String getMinOrderQuantity() {
		return minOrderQuantity;
	}

	public void setMinOrderQuantity(String minOrderQuantity) {
		this.minOrderQuantity = minOrderQuantity;
	}

	public String getFobPrice() {
		return fobPrice;
	}

	public void setFobPrice(String fobPrice) {
		this.fobPrice = fobPrice;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getWheelHubDiameter() {
		return wheelHubDiameter;
	}

	public void setWheelHubDiameter(String wheelHubDiameter) {
		this.wheelHubDiameter = wheelHubDiameter;
	}


	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPcd() {
		return pcd;
	}

	public void setPcd(String pcd) {
		this.pcd = pcd;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getLoadingPort() {
		return loadingPort;
	}

	public void setLoadingPort(String loadingPort) {
		this.loadingPort = loadingPort;
	}

	public String getHsCode() {
		return hsCode;
	}

	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getEt() {
		return et;
	}

	public void setEt(String et) {
		this.et = et;
	}

	public String getCbd() {
		return cbd;
	}

	public void setCbd(String cbd) {
		this.cbd = cbd;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHole() {
		return hole;
	}

	public void setHole(String hole) {
		this.hole = hole;
	}

	public String getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getTransportPackage() {
		return transportPackage;
	}

	public void setTransportPackage(String transportPackage) {
		this.transportPackage = transportPackage;
	}

	public String getPacking() {
		return packing;
	}

	public void setPacking(String packing) {
		this.packing = packing;
	}

	public String getProductionCapacity() {
		return productionCapacity;
	}

	public void setProductionCapacity(String productionCapacity) {
		this.productionCapacity = productionCapacity;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getFinishing() {
		return finishing;
	}

	public void setFinishing(String finishing) {
		this.finishing = finishing;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDriveWheel() {
		return driveWheel;
	}

	public void setDriveWheel(String driveWheel) {
		this.driveWheel = driveWheel;
	}

	public String getWheelAccessories() {
		return wheelAccessories;
	}

	public void setWheelAccessories(String wheelAccessories) {
		this.wheelAccessories = wheelAccessories;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getFront() {
		return front;
	}

	public void setFront(String front) {
		this.front = front;
	}

	public String getRear() {
		return rear;
	}

	public void setRear(String rear) {
		this.rear = rear;
	}

	public String getWheelHubBrand() {
		return wheelHubBrand;
	}

	public void setWheelHubBrand(String wheelHubBrand) {
		this.wheelHubBrand = wheelHubBrand;
	}

	public String getTrademark() {
		return trademark;
	}

	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}

	public String getWarranty() {
		return warranty;
	}

	public void setWarranty(String warranty) {
		this.warranty = warranty;
	}

	public String getPainting() {
		return painting;
	}

	public void setPainting(String painting) {
		this.painting = painting;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getCorporationResourceUrl() {
		return corporationResourceUrl;
	}

	public void setCorporationResourceUrl(String corporationResourceUrl) {
		this.corporationResourceUrl = corporationResourceUrl;
	}

	public String getResourcsUrl() {
		return resourcsUrl;
	}

	public void setResourcsUrl(String resourcsUrl) {
		this.resourcsUrl = resourcsUrl;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}
}
