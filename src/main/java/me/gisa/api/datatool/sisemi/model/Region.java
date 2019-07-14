package me.gisa.api.datatool.sisemi.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Region {
	private String type;
	private String code;
	private String name;
	private String fullName;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Region region = (Region) o;
		return new EqualsBuilder().append(type, region.type).append(code, region.code).append(name, region.name)
				.append(fullName, region.fullName).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(type).append(code).append(name).append(fullName).toHashCode();
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("type", type).append("code", code).append("name", name)
				.append("fullName", fullName).toString();
	}
}