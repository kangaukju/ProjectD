package kr.co.projecta.matching.history;

import java.util.Date;

public class SeekerNoticeHistory {
	private String requirementId;
	private String offererId;
	private String seekerId;
	private Date datetime;
	
	public SeekerNoticeHistory() {		
	}
	public SeekerNoticeHistory(String requirementId, String offererId, String seekerId) {
		super();
		this.requirementId = requirementId;
		this.offererId = offererId;
		this.seekerId = seekerId;
	}
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
	public String getSeekerId() {
		return seekerId;
	}
	public void setSeekerId(String seekerId) {
		this.seekerId = seekerId;
	}
}
