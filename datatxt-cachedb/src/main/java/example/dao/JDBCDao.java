package example.dao;
/**
 * Copyright 2022 Serguei Kouzmine
 */

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;

import example.entity.Host;
import example.projection.ServerInstanceApplication;
import example.utils.JDBCUtils;
import example.utils.Utils;

import org.apache.logging.log4j.Logger;

public class JDBCDao implements Dao {

	final static Map<String, String> env = System.getenv();
	private static final Logger logger = LogManager
			.getLogger(JDBCDao.class.getName());

	private static final Connection conn = JDBCUtils.getConnection();

	@Override
	public int addHost(Host host) {
		return 0;
	}

	@Override
	public List<?> findAllHost() {
		return null;
	}

	@Override
	public List<?> findAllServerInstanceApplication() {
		List<?> results = null;
		String sql = "select server1_.sname as serverName, applicatio2_.aname as applicationName, instance3_.iname as instanceName"
				+ " from axixs axixs0_ inner join server server1_ on (axixs0_.sid=server1_.sid) inner join application applicatio2_ on (axixs0_.aid=applicatio2_.aid) inner join instance instance3_ on (axixs0_.iid=instance3_.iid)";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			ResultSet resultSet = preparedStatement.executeQuery();
			results = JDBCUtils.TranverseToList(resultSet,
					ServerInstanceApplication.class);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return results;
	}

	@Override
	public int updateHost(Host host) {
		return 0;
	}

	@Override
	public int delHostById(long id) {
		return 0;
	}

	@Override
	public Host findHostById(long id) {
		return null;
	}

	@Override
	public Host findHostByHostname(String hostname) {
		return null;
	}
}
