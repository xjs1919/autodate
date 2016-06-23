package com.chrhc.mybatis.autodate.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

	private static final long serialVersionUID = 9030785649977959711L;

	private Integer id;
	private String name;
	private String password;
	private Long version;
	private Date createDate1;
	private Date updateDate1;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateDate1() {
		return createDate1;
	}

	public void setCreateDate1(Date createDate1) {
		this.createDate1 = createDate1;
	}

	public Date getUpdateDate1() {
		return updateDate1;
	}

	public void setUpdateDate1(Date updateDate1) {
		this.updateDate1 = updateDate1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
