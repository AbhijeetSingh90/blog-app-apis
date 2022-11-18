package blogappapis.exception;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException{
	
	 String resourceName;
	 String feildName;
	 Integer feildValue;
	
	public ResourceNotFoundException(String resourceName, String feildName, Integer userId) {
		super(String.format("%s Not Found With %s :%s ",resourceName,feildName,userId));
		this.resourceName = resourceName;
		this.feildName = feildName;
		this.feildValue = userId;
	}
	
	

}
