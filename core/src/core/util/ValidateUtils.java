package core.util;

public class ValidateUtils implements IValidator {
	
	
	public boolean isNullOrEmpty(String value){
		if(value != null && value.length() > 0)
			return false;
		else
			return true;
	}
}
