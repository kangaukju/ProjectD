package kr.co.projecta.matching.context;

import java.util.List;

public interface ContextSchemable {
	public List<Object> getContextSchemaData();
	public String getName();
	public String getOriginalName();
}
