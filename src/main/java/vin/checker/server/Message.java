package vin.checker.server;

import static vin.checker.constants.VinCheckerConstants.MESSAGES_BUNDLE;
import static vin.checker.constants.VinCheckerConstants.UNKNOWN_ERROR_CODE;

import java.util.Locale;
import java.util.ResourceBundle;

public class Message {

	private ResourceBundle messages;    
    public Message(String locale) {
    	
        super();        
        this.messages = ResourceBundle.getBundle(MESSAGES_BUNDLE, new Locale(locale));
    }
    
    /**
     * get message based on msg key
     * 
     * @param msgKey
     * @return
     */
    public String getMessage(String msgKey) {
    	
        try {
            return messages.getString(msgKey);
        } catch (Exception e) {
            return UNKNOWN_ERROR_CODE;
        }
    }
    
    /**
     * get message based on msg key
     * 
     * @param msgKey
     * @return
     */
    public String getMessage(Integer msgKey) {
    	
        try {
            return messages.getString(msgKey.toString());
        } catch (Exception e) {
            return UNKNOWN_ERROR_CODE;
        }
    }    
}