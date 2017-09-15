package com.srikanth.springboot.db;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cassandra.core.keyspace.CreateTableSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.config.java.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import com.datastax.driver.core.DataType;
import com.datastax.driver.core.Session;


@SpringBootApplication
@EnableCassandraRepositories(basePackageClasses = LocalCassandra.class)
public class LocalCassandra extends AbstractCassandraConfiguration {
	
	private static final Log LOGGER = LogFactory.getLog(LocalCassandra.class);

	public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS mykeyspace WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

	public static final String KEYSPACE_ACTIVATE_QUERY = "USE mykeyspace;";

	public static void main(String[] args) throws Exception {
		SpringApplication.run(LocalCassandra.class, args);
	}

	@Override
	protected String getKeyspaceName() {
		return "mykeyspace";
	}

	@Bean
	public CommandLineRunner loadData(CassandraOperations cassandraTemplate, CassandraSessionFactoryBean session,
			Record repository) {
		return (args) -> {
			Session nativeSessionObject = session.getObject();
			
			nativeSessionObject.execute(KEYSPACE_CREATION_QUERY);
			nativeSessionObject.execute(KEYSPACE_ACTIVATE_QUERY);
			
			LOGGER.info("KeySpace created and activated.");
			
			try {
				nativeSessionObject.execute("DROP TABLE Record");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			// Could use manual cql query as well, but Spring can help a bit.
			// We could also could let Spring autogenerate the table
			cassandraTemplate.execute(
					CreateTableSpecification.createTable().name("Record").partitionKeyColumn("attribute_name", DataType.text())
							.column("attribute_displayName", DataType.text()).column("attribute_value", DataType.text()));


		};
	}
}
