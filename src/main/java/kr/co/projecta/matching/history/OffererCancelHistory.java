package kr.co.projecta.matching.history;

import java.util.Date;

public class OffererCancelHistory {
	private String requirementId;
	private String offererId;
	private Date datetime;
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public String getRequirementId() {
		return requirementId;
	}
	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}
	public String getOffererId() {
		return offererId;
	}
	public void setOffererId(String offererId) {
		this.offererId = offererId;
	}
}
