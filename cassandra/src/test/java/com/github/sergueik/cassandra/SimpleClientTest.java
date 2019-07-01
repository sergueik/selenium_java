package com.github.sergueik.cassandra;

import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.Ordering;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import static java.lang.System.out;
import static java.lang.System.err;

public class SimpleClientTest {

	// TODO: launch the server in @Before, wait for
	// org.apache.cassandra.service.CassandraDaemon process
	// TCP ports TCP (7000/7001), CLQ(9042) and possibly JMX (7199) to be LISTEN
	// https://www.linode.com/docs/databases/cassandra/set-up-a-cassandra-node-cluster-on-ubuntu-and-centos/
	// and bin\nodetool.cmd status
	// TODO: DataStax_Cassandra_Community_Server, DataStax_DDC_Server checks

	final int portCQL = 9042;
	// in $CASSANDRA_HOME/conf/jvm.options and
	// $CASSANDRA_HOME/conf/cassandra-env.sh
	private static final String node = "localhost"; // "127.0.0.1";

	@Test
	public void testGetConnected() {
		CassandraConnector client = new CassandraConnector(node);
		client.connect(node);
		Session session = client.getSession();
		err.println(
				"connected hosts:" + session.getState().getConnectedHosts().size());
		err.println("cluster:" + session.getCluster().getClusterName());
		err.println("driver version: " + session.getCluster().getDriverVersion());
	}

	@Test
	public void testClient() {
		CassandraConnector client = new CassandraConnector();
		client.connect(node);
		err.println("Connecting to node " + node + ":" + portCQL);
		client.getSession();
		client.createSchema();
		client.loadData();
		client.querySchema();
		client.closeSession();
		client.close();
	}

	// https://www.javaworld.com/article/2158807/connecting-to-cassandra-from-java.html
	private static class CassandraConnector {
		private Cluster cluster;
		private Session session = null;
		private String node = "localhost";

		public CassandraConnector(final String node) {
			this.node = node;
		}

		public CassandraConnector() {
		}

		public Session getSession() {
			if (session == null) {
				session = cluster.connect();
			}
			return this.session;
		}

		public void connect(final String node, final int port) {
			cluster = Cluster.builder().addContactPoint(node).withPort(port).build();
			for (final Host host : cluster.getMetadata().getAllHosts()) {
				err.printf("Datacenter: %s; Host: %s; Rack: %s\n", host.getDatacenter(),
						host.getAddress(), host.getRack());
			}
		}

		public void connect(final String node) {
			cluster = Cluster.builder().addContactPoint(node).build();
			Metadata metadata = cluster.getMetadata();
			err.println("Connected to cluster:" + metadata.getClusterName());
			for (Host host : metadata.getAllHosts()) {
				err.println("Datatacenter: " + host.getDatacenter() + "; " + "Host: "
						+ host.getAddress() + "; " + "Rack: " + host.getRack());
			}
		}

		public void closeSession() {
			session.close();
		}

		public void close() {
			cluster.close();
		}

		public void createSchema() {
			session.execute("CREATE KEYSPACE IF NOT EXISTS simplex WITH replication "
					+ "= {'class':'SimpleStrategy', 'replication_factor':3};");
			session.execute("CREATE TABLE IF NOT EXISTS simplex.songs ("
					+ "id uuid PRIMARY KEY," + "title text," + "album text,"
					+ "artist text," + "tags set<text>," + "data blob" + ");");
			session.execute("CREATE TABLE IF NOT EXISTS simplex.playlists ("
					+ "id uuid," + "title text," + "album text, " + "artist text,"
					+ "song_id uuid," + "PRIMARY KEY (id, title, album, artist)" + ");");
		}

		public void loadData() {

			PreparedStatement statement = session.prepare("INSERT INTO simplex.songs "
					+ "(id, title, album, artist, tags) " + "VALUES (?, ?, ?, ?, ?);");

			BoundStatement boundStatement = new BoundStatement(statement);
			Set<String> tags = new HashSet<>();
			tags.add("metal");
			tags.add("1992");
			UUID idAlbum = UUID.randomUUID();
			ResultSet res = session.execute(boundStatement.bind(idAlbum, "DNR",
					"The gathering", "Testament", tags));

			err.println(res.toString());

			statement = session.prepare("INSERT INTO simplex.playlists "
					+ "(id, song_id, title, album, artist) " + "VALUES (?, ?, ?, ?, ?);");

			UUID idPlaylist = UUID.randomUUID();
			boundStatement = new BoundStatement(statement);
			session.execute(boundStatement.bind(idPlaylist, idAlbum, "DNR",
					"The gathering", "Testament"));
		}

		public void querySchema() {
			Statement statement = QueryBuilder.select().all().from("simplex",
					"songs");
			ResultSet results = session.execute(statement);
			err.println(String.format("%-50s\t%-30s\t%-20s\t%-20s\n%s", "id", "title",
					"album", "artist",
					"------------------------------------------------------+-------------------------------+------------------------+-----------"));
			for (Row row : results) {
				err.println(String.format("%-50s\t%-30s\t%-20s\t%-20s",
						row.getUUID("id"), row.getString("title"), row.getString("album"),
						row.getString("artist")));
			}
			err.println();
		}
	}

}
