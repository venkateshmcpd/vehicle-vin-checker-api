package vin.checker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class AttributeData {

	private String valueId;
	private String value;
	
	public AttributeData(String valueId, String value) {
		this.valueId = valueId;
		this.value = value;
	}
}
