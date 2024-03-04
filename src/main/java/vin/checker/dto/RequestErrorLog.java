package vin.checker.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public final class RequestErrorLog {
	private Integer errorTypeId;
	private Integer vehicleInfoId;
	private String requestedVin;
	private String requestedMake;
	private String requestedModel;
	private String requestedModelYear;
	private String errorCode;
	private String errorText;
}
