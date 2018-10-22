package com.winds.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * AREA实体
 * 
 * @author autoCreate
 * @date 2015-12-14 16:41:04
 * @version V1.0.0
 */
public class Area implements Serializable,Cloneable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8237149864657686653L;
	// 父级ID
	private Long parentId;
	// 地区名称
	private String areaName;
	//
	private Integer tLevel;
	// 所有父节点的路径
	private String idPath;
	// 排序
	private Integer position;
	// 显示状态
	private Integer isUsed;
	// 地区等级2：省份 3：城市 4：地区 5街道
	private String areaLevel;
	// 区域名称
	private String posName;
	// 坐标
	private String map;
	// 地区代码
	private String areaCode;
	// 是否显示
	private Integer isShow;
	// 首字母大写索引
	private String iKey;
	// 国家标准码
	private String norm;

	private String parentName;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Area() {
		super();
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Integer gettLevel() {
		return tLevel;
	}

	public void settLevel(Integer tLevel) {
		this.tLevel = tLevel;
	}

	public String getIdPath() {
		return idPath;
	}

	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getIsUsed() {
		return isUsed;
	}

	public void setIsUsed(Integer isUsed) {
		this.isUsed = isUsed;
	}

	public String getAreaLevel() {
		return areaLevel;
	}

	public void setAreaLevel(String areaLevel) {
		this.areaLevel = areaLevel;
	}

	public String getPosName() {
		return posName;
	}

	public void setPosName(String posName) {
		this.posName = posName;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getiKey() {
		return iKey;
	}

	public void setiKey(String iKey) {
		this.iKey = iKey;
	}

	public String getNorm() {
		return norm;
	}

	public void setNorm(String norm) {
		this.norm = norm;
	}

	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}

	@Override
	public String toString() {
		return "Area{" +
				", parentId=" + parentId +
				", areaName='" + areaName + '\'' +
				", tLevel=" + tLevel +
				", idPath='" + idPath + '\'' +
				", position=" + position +
				", isUsed=" + isUsed +
				", areaLevel='" + areaLevel + '\'' +
				", posName='" + posName + '\'' +
				", map='" + map + '\'' +
				", areaCode='" + areaCode + '\'' +
				", isShow=" + isShow +
				", iKey='" + iKey + '\'' +
				", parentName='" + parentName + '\'' +
				'}';
	}
}
