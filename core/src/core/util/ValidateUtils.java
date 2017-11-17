package core.util;

public class ValidateUtils implements IValidator {
	
	
	public boolean isNullOrEmpty(String value){
		if(value != null && value.length() > 0)
			return false;
		else
			return true;
	}
	
	
	public boolean isNullOrEmpty(Object obj) {
		if(obj == null)
			return true;
		if(obj instanceof Integer) {
			Integer value = (Integer) obj;
			return value <= 0;
		}
		if(obj instanceof String) {
			String value = (String) obj;
			return value.length() <= 0;
		}
		if(obj instanceof Double) {
			Double value = (Double) obj;
			return value <= 0.0;
		}
		return false;
	}
}
