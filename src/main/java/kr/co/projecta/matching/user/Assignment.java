package kr.co.projecta.matching.user;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Assignment 
	implements Serializable
{
	private static final long serialVersionUID = 7329525393587583621L;
	
	public static final int CANDIDATE = 0;
	public static final int CONFIRM = 1;
	
	String requirementId;
	String seekerId;
	int confirm;
	
	public Assignment() {
		
	}
	
	public int getConfirm() {
		return confirm;
	}

	public void setConfirm(int confirm) {
		this.confirm = confirm;
	}
	
	public String getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}
	
	public String getSeekerId() {
		return seekerId;
	}

	public void setSeekerId(String seekerId) {
		this.seekerId = seekerId;
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(
			this, ToStringStyle.NO_CLASS_NAME_STYLE);
	}
	
	public boolean equals(Object obj) {
		Assignment assignment = (Assignment) obj;
		return new EqualsBuilder()
				.append(requirementId, assignment.requirementId)
				.append(seekerId, assignment.seekerId)
				.isEquals();
	}
}
