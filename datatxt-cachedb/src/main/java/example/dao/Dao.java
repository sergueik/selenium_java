package example.dao;

import java.util.List;

import example.entity.Host;
import example.entity.Result;
import example.projection.ServerInstanceApplication;

public interface Dao {

	public int addHost(Host student);
	public List<?> findAllHost();
	public int updateHost(Host student);
	public int delHostById(long id);
	// NOTE: strongly typed
	public Host findHostById(long id);
	
	public Host findHostByHostname(String hostname);
	public List<?> findAllServerInstanceApplication();
}
			