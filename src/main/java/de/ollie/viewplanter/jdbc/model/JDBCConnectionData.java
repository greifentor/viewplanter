package de.ollie.viewplanter.jdbc.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
public class JDBCConnectionData {

	private String driverClassName;
	private String password;
	private String schemeName;
	private String url;
	private String userName;

}
