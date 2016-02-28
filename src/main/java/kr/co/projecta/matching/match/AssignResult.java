package kr.co.projecta.matching.match;

import java.io.Serializable;
import java.util.List;

import kr.co.projecta.matching.user.Requirement;
import kr.co.projecta.matching.user.Seeker;

public class AssignResult 
	implements Serializable
{
	private static final long serialVersionUID = 882062912548265559L;
	
	Requirement requirement;
	List<Seeker> seekers;
	
	public AssignResult(Requirement requirement, List<Seeker> seekers) {
		super();
		this.requirement = requirement;
		this.seekers = seekers;
	}
	public Requirement getRequirement() {
		return requirement;
	}
	public List<Seeker> getSeekers() {
		return seekers;
	}
}
