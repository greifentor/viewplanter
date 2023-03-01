package de.ollie.viewplanter.jdbc.model;

import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class JDBCConnectionData {

	private String driverClassName;
	private String password;
	private String schemeName;
	private String url;
	private String userName;

}
