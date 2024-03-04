package vin.checker.constants;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

public class VinCheckerApiConstants {

	// API end points
	public static final String API_GET_HEARTBEAT = "/api/heartbeat";
	public static final String API_POST_SEARCH_VIN = "post.search-vin"; 

    public static final Set<String> API_END_POINTS = ImmutableSet.of(
    		API_POST_SEARCH_VIN
    );

    
	/**
	 * empty private constructor
	 */
	private VinCheckerApiConstants() {
		
	}
}
