package com.zhongzhou.Excavator.dataIndex.model.item.wheel;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Entity;

@Entity( noClassnameStored=true )
public class Corporation {
	
	private static final String dataType = "corporation"; 
	
	/**唯标识*/
	private String id;
	private String originalUrl;
	private int currentPage;
	private int  index;
	private String parentId;
	/**数据原始网站*/
	private String dataSource;
	/**图片id*/
	private String imageId;
	/**网页资源id*/
	private String pageId;

	private String modelClassName;
	private Class modelClass;
	private long insertTime;
	/** 公司名称 **/
	private String name;
	/**图片**/
	private byte[] img;
	/**公司地址* */
	private String address;
	/**工厂地址**/
	private String factoryAddress;
	/** 联系人 **/
	private String contact;
	/** 传真 **/
	private String fax;
	/** 联系电话 **/
	private String telePhone;
	/**主营产品*/
	private String mainProducts;
	/**公司性质*/
//	private String companyNature;
	/**产品资源所在url**/
	private String resourceUrl;
	/**产品证书**/
//	private List<Certification> certifications;
	/**贸易类型**/
	private String businessType;
	/****/
	private String businessRange;
	/****/
	private String registeredCapital;
	/****/
	private String managementSystemCertification;
	/****/
	private String termsOfPayment;
	/**OEM/ODM能力**/
	private String oemOrOdmAvailability;
	/**公司简介**/
	private String introduction;
	/****/
	private String internationalCommercialTerms;
	/****/
	private String averageLeadTime;
	/****/
	private String numberofForeignTradingStaff;
	/****/
	private  String exportYear;
	/****/
	private String exportPercentage;
	/**主要市场**/
	private String mainMarkets;
	/****/
	private String nearestPort;
	/****/
	private String importExportMode;
	/****/
//	private byte[] licensePhoto;
	/****/
	private String annualExportRevenue;
	/****/
	private String rAndDCapacity;
	/****/
	private String noOfRAndDStaff;
	/****/
	private String noOfProductionLines;
	/****/
	private String annualOutputValue;
	/**其他描述**/
	private String otherDesciption;
	/***/
	private String goldMember;
	/***/
	private List<TradeShow> tradeShows;
	/**厂房面积*/
	private String factorySize;
	/**成立时间*/
	private String yearEstablished;
	/**员工人数*/
	private String totalEmployees;
	//认证图片
	private List<Certification> certifications;

	//支付货币
	private String acceptedPaymentCurrency;
	//生产能力
	private String highestEverAnnualOutput;
	/**上市公司代码*/
	private String listedCompanyCode;
	public Corporation() {
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

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
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

	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getImg() {
		return img;
	}

	public void setImg(byte[] img) {
		this.img = img;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFactoryAddress() {
		return factoryAddress;
	}

	public void setFactoryAddress(String factoryAddress) {
		this.factoryAddress = factoryAddress;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTelePhone() {
		return telePhone;
	}

	public void setTelePhone(String telePhone) {
		this.telePhone = telePhone;
	}

	public String getMainProducts() {
		return mainProducts;
	}

	public void setMainProducts(String mainProducts) {
		this.mainProducts = mainProducts;
	}

	public String getResourceUrl() {
		return resourceUrl;
	}

	public void setResourceUrl(String resourceUrl) {
		this.resourceUrl = resourceUrl;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessRange() {
		return businessRange;
	}

	public void setBusinessRange(String businessRange) {
		this.businessRange = businessRange;
	}

	public String getRegisteredCapital() {
		return registeredCapital;
	}

	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}

	public String getManagementSystemCertification() {
		return managementSystemCertification;
	}

	public void setManagementSystemCertification(String managementSystemCertification) {
		this.managementSystemCertification = managementSystemCertification;
	}

	public String getTermsOfPayment() {
		return termsOfPayment;
	}

	public void setTermsOfPayment(String termsOfPayment) {
		this.termsOfPayment = termsOfPayment;
	}

	public String getOemOrOdmAvailability() {
		return oemOrOdmAvailability;
	}

	public void setOemOrOdmAvailability(String oemOrOdmAvailability) {
		this.oemOrOdmAvailability = oemOrOdmAvailability;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getInternationalCommercialTerms() {
		return internationalCommercialTerms;
	}

	public void setInternationalCommercialTerms(String internationalCommercialTerms) {
		this.internationalCommercialTerms = internationalCommercialTerms;
	}

	public String getAverageLeadTime() {
		return averageLeadTime;
	}

	public void setAverageLeadTime(String averageLeadTime) {
		this.averageLeadTime = averageLeadTime;
	}

	public String getNumberofForeignTradingStaff() {
		return numberofForeignTradingStaff;
	}

	public void setNumberofForeignTradingStaff(String numberofForeignTradingStaff) {
		this.numberofForeignTradingStaff = numberofForeignTradingStaff;
	}

	public String getExportYear() {
		return exportYear;
	}

	public void setExportYear(String exportYear) {
		this.exportYear = exportYear;
	}

	public String getExportPercentage() {
		return exportPercentage;
	}

	public void setExportPercentage(String exportPercentage) {
		this.exportPercentage = exportPercentage;
	}

	public String getMainMarkets() {
		return mainMarkets;
	}

	public void setMainMarkets(String mainMarkets) {
		this.mainMarkets = mainMarkets;
	}

	public String getNearestPort() {
		return nearestPort;
	}

	public void setNearestPort(String nearestPort) {
		this.nearestPort = nearestPort;
	}

	public String getImportExportMode() {
		return importExportMode;
	}

	public void setImportExportMode(String importExportMode) {
		this.importExportMode = importExportMode;
	}

	public String getAnnualExportRevenue() {
		return annualExportRevenue;
	}

	public void setAnnualExportRevenue(String annualExportRevenue) {
		this.annualExportRevenue = annualExportRevenue;
	}

	public String getrAndDCapacity() {
		return rAndDCapacity;
	}

	public void setrAndDCapacity(String rAndDCapacity) {
		this.rAndDCapacity = rAndDCapacity;
	}

	public String getNoOfRAndDStaff() {
		return noOfRAndDStaff;
	}

	public void setNoOfRAndDStaff(String noOfRAndDStaff) {
		this.noOfRAndDStaff = noOfRAndDStaff;
	}

	public String getNoOfProductionLines() {
		return noOfProductionLines;
	}

	public void setNoOfProductionLines(String noOfProductionLines) {
		this.noOfProductionLines = noOfProductionLines;
	}

	public String getAnnualOutputValue() {
		return annualOutputValue;
	}

	public void setAnnualOutputValue(String annualOutputValue) {
		this.annualOutputValue = annualOutputValue;
	}

	public String getOtherDesciption() {
		return otherDesciption;
	}

	public void setOtherDesciption(String otherDesciption) {
		this.otherDesciption = otherDesciption;
	}

	public String getGoldMember() {
		return goldMember;
	}

	public void setGoldMember(String goldMember) {
		this.goldMember = goldMember;
	}

	public List<TradeShow> getTradeShows() {
		return tradeShows;
	}

	public void setTradeShows(List<TradeShow> tradeShows) {
		this.tradeShows = tradeShows;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public String getFactorySize() {
		return factorySize;
	}

	public void setFactorySize(String factorySize) {
		this.factorySize = factorySize;
	}

	public String getYearEstablished() {
		return yearEstablished;
	}

	public void setYearEstablished(String yearEstablished) {
		this.yearEstablished = yearEstablished;
	}

	public String getTotalEmployees() {
		return totalEmployees;
	}

	public void setTotalEmployees(String totalEmployees) {
		this.totalEmployees = totalEmployees;
	}

	public List<Certification> getCertifications() {
		return certifications;
	}

	public void setCertifications(List<Certification> certifications) {
		this.certifications = certifications;
	}

	public String getAcceptedPaymentCurrency() {
		return acceptedPaymentCurrency;
	}

	public void setAcceptedPaymentCurrency(String acceptedPaymentCurrency) {
		this.acceptedPaymentCurrency = acceptedPaymentCurrency;
	}

	public String getHighestEverAnnualOutput() {
		return highestEverAnnualOutput;
	}

	public void setHighestEverAnnualOutput(String highestEverAnnualOutput) {
		this.highestEverAnnualOutput = highestEverAnnualOutput;
	}

	public String getListedCompanyCode() {
		return listedCompanyCode;
	}

	public void setListedCompanyCode(String listedCompanyCode) {
		this.listedCompanyCode = listedCompanyCode;
	}

	@Override
	public String toString() {
		return "Corporation{" +
				"id='" + id + '\'' +
				", parentId='" + parentId + '\'' +
				", imageId='" + imageId + '\'' +
				", pageId='" + pageId + '\'' +
				", modelClassName='" + modelClassName + '\'' +
				", modelClazz=" + modelClass +
				", name='" + name + '\'' +
				", img=" + Arrays.toString(img) +
				", address='" + address + '\'' +
				", factoryAddress='" + factoryAddress + '\'' +
				", contact='" + contact + '\'' +
				", fax='" + fax + '\'' +
				", telePhone='" + telePhone + '\'' +
				", mainProducts='" + mainProducts + '\'' +
				", resourceUrl='" + resourceUrl + '\'' +
				", businessType='" + businessType + '\'' +
				", businessRange='" + businessRange + '\'' +
				", registeredCapital='" + registeredCapital + '\'' +
				", managementSystemCertification='" + managementSystemCertification + '\'' +
				", termsOfPayment='" + termsOfPayment + '\'' +
				", oemOrOdmAvailability='" + oemOrOdmAvailability + '\'' +
				", introduction='" + introduction + '\'' +
				", internationalCommercialTerms='" + internationalCommercialTerms + '\'' +
				", averageLeadTime='" + averageLeadTime + '\'' +
				", numberofForeignTradingStaff='" + numberofForeignTradingStaff + '\'' +
				", exportYear='" + exportYear + '\'' +
				", exportPercentage='" + exportPercentage + '\'' +
				", mainMarkets='" + mainMarkets + '\'' +
				", nearestPort='" + nearestPort + '\'' +
				", importExportMode='" + importExportMode + '\'' +
				", annualExportRevenue='" + annualExportRevenue + '\'' +
				", rAndDCapacity='" + rAndDCapacity + '\'' +
				", noOfRAndDStaff='" + noOfRAndDStaff + '\'' +
				", noOfProductionLines='" + noOfProductionLines + '\'' +
				", annualOutputValue='" + annualOutputValue + '\'' +
				", otherDesciption='" + otherDesciption + '\'' +
				", goldMember='" + goldMember + '\'' +
				", tradeShows=" + tradeShows +
				", dataSource=" + dataSource +
				", factorySize='" + factorySize + '\'' +
				", yearEstablished='" + yearEstablished + '\'' +
				", totalEmployees='" + totalEmployees + '\'' +
				", certifications=" + certifications +
				", acceptedPaymentCurrency='" + acceptedPaymentCurrency + '\'' +
				", highestEverAnnualOutput='" + highestEverAnnualOutput + '\'' +
				'}';
	}
}
