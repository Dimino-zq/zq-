package edu.hfu.train.service.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6181373721596678438L;
	private final String userType;
	public CustomWebAuthenticationDetails(HttpServletRequest request) {
		super(request);
		userType=request.getParameter("userType");
	}
	
	public String getUserType() {
		return userType;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("; UserType: ").append(this.getUserType());
        return sb.toString();
    }
}
