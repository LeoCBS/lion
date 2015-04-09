package com.custom.utils;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.custom.service.TestEntity;
import com.custom.service.TestService;

public class ServiceTest {

	private static EntityManagerFactory emFactory;

	private static EntityManager em;

	private static Connection connection;

	@BeforeClass
	public static void initTestFixture() throws Exception {
		try {
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection(
					"jdbc:hsqldb:mem:unit-testing-jpa", "sa", "");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			emFactory = Persistence.createEntityManagerFactory("testPU");
			em = emFactory.createEntityManager();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Test
	public void testFindByFilters() throws Exception{
		TestService testService = new TestService();
		testService.setEntityManager(em);
		testService.create(new TestEntity());
	}

	/**
	 * Cleans up the session.
	 */
	@AfterClass
	public static void closeTestFixture() {
		if (em != null) {
			em.close();
		}
		if (emFactory != null) {
			emFactory.close();
		}
		try {
			connection.createStatement().execute("SHUTDOWN");
		} catch (Exception ex) {
		}
	}

}
