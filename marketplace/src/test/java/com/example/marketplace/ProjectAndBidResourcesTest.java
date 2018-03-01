/**
 * 
 */
package com.example.marketplace;

import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.example.marketplace.domain.bids.Bid;
import com.example.marketplace.domain.projects.Project;
import com.example.marketplace.resources.bids.BidResource;
import com.example.marketplace.resources.projects.ProjectResource;

/**
 * @author srikanthgummula
 *
 * Test class for Project resource
 */
public class ProjectAndBidResourcesTest {
	
	private static JerseyTest jerseyTest;
	
	private static void initJerseyTest() {
		jerseyTest = new JerseyTest() {
			@Override
			protected Application configure() {
				return new ResourceConfig(ProjectResource.class,BidResource.class);
			}
		};
	}
	
	@BeforeClass
	public static void setUp() throws Exception {
		initJerseyTest();
		jerseyTest.setUp();
	}
	
	@AfterClass
	public static void tearDown() throws Exception {
		jerseyTest.tearDown();
		jerseyTest = null;
	}
	
	@Before
	public void setData() {
		
	}
	
	@Test
	public void testCreateProject() {
		Project project = createProject();
		Response response = jerseyTest.target("/v1/projects").request().post(Entity.entity(project, MediaType.APPLICATION_JSON));
		Assert.assertEquals(201, response.getStatus());
		String msg = response.readEntity(String.class);
		Assert.assertNotNull(msg);
	}
	
	@Test
	public void testCreateProjectWithInvalidRequest() {
		Project project = createProject();
		project.setCurrency(null);
		Response response = jerseyTest.target("/v1/projects").request().post(Entity.entity(project, MediaType.APPLICATION_JSON));
		Assert.assertEquals(400, response.getStatus());
		Assert.assertNotNull(response.getEntity());
		String msg = response.readEntity(String.class);
		Assert.assertNotNull(msg);
		JSONObject jsonObject = new JSONObject(msg);
		Assert.assertEquals(ErrorCode.INVALID_REQUEST.toString(), jsonObject.getString("code"));
		Assert.assertEquals(ProjectMessages.CURRENCY_INVALID, jsonObject.getString("message"));
	}
	
	@Test
	public void testUpdateProject() {
		Project project = createProject();
		Response response = jerseyTest.target("/v1/projects/12").request().put(Entity.entity(project, MediaType.APPLICATION_JSON));
		Assert.assertEquals(204, response.getStatus());
		String msg = response.readEntity(String.class);
		Assert.assertNotNull(msg);
		Assert.assertEquals("", msg);
	}
	
	@Test
	public void testUpdateProjectWithInvalidRequest() {
		Project project = createProject();
		project.setProjectId(11);
		Response response = jerseyTest.target("/v1/projects/12").request().put(Entity.entity(project, MediaType.APPLICATION_JSON));
		Assert.assertEquals(400, response.getStatus());
		String msg = response.readEntity(String.class);
		Assert.assertNotNull(msg);
		JSONObject jsonObject = new JSONObject(msg);
		Assert.assertEquals(ErrorCode.INVALID_REQUEST.toString(), jsonObject.getString("code"));
		Assert.assertEquals(ProjectMessages.PROJECT_ID_INVALID, jsonObject.getString("message"));
	}
	
	@Test
	public void testGetProjectById() {
		Response response = jerseyTest.target("/v1/projects/12").request().get();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertNotNull(response.getEntity());
	}
	
	@Test
	public void testGetProjectByInvalidId() {
		Response response = jerseyTest.target("/v1/projects/1").request().get();
		Assert.assertEquals(404, response.getStatus());
		Assert.assertNotNull(response.getEntity());
	}
	
	@Test
	public void testGetProjectByStatus() {
		Response response = jerseyTest.target("/v1/projects/all").queryParam("status", "Open").request().get();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertNotNull(response.getEntity());
	}
	
	@Test
	public void testGetProjectByInvalidStatus() {
		Response response = jerseyTest.target("/v1/projects/all").queryParam("status", "abc").request().get();
		Assert.assertEquals(204, response.getStatus());
		String msg = response.readEntity(String.class);
		Assert.assertNotNull(msg);
		Assert.assertEquals("", msg);
	}
	
	@Test
	public void testGetProjectByType() {
		Response response = jerseyTest.target("/v1/projects/all").queryParam("type", "fixed").request().get();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertNotNull(response.getEntity());		
	}
	
	@Test
	public void testGetProjectByInvalidType() {
		Response response = jerseyTest.target("/v1/projects/all").queryParam("type", "abcd").request().get();
		Assert.assertEquals(204, response.getStatus());
		String msg = response.readEntity(String.class);
		Assert.assertNotNull(msg);
		Assert.assertEquals("", msg);		
	}
	
	@Test
	public void testCreateBid() {
		Response response = jerseyTest.target("/v1/projects/10/bids").request().post(Entity.entity(createBidRequest(), MediaType.APPLICATION_JSON));
		Assert.assertEquals(201, response.getStatus());
		String msg = response.readEntity(String.class);
		Assert.assertNotNull(msg);
	}
	
	@Test
	public void testCreateBidWithInvalidRequest() {
		Bid bid = createBidRequest();
		bid.setBuyer(null);
		Response response = jerseyTest.target("/v1/projects/11/bids").request().post(Entity.entity(bid, MediaType.APPLICATION_JSON));
		Assert.assertEquals(400, response.getStatus());
		String msg = response.readEntity(String.class);
		Assert.assertNotNull(msg);
		JSONObject jsonObject = new JSONObject(msg);
		Assert.assertEquals(ErrorCode.INVALID_REQUEST.toString(), jsonObject.getString("code"));
		Assert.assertEquals(BidMessages.BUYER_INVALID, jsonObject.getString("message"));
	}
	
	@Test
	public void testUpdateBid() {
		Bid bid = createBidRequest();
		bid.setProjectId(10);
		Response response = jerseyTest.target("/v1/projects/bids/11").request().put(Entity.entity(bid, MediaType.APPLICATION_JSON));
		Assert.assertEquals(204, response.getStatus());
		String msg = response.readEntity(String.class);
		Assert.assertNotNull(msg);
	}
	
	@Test
	public void testUpdateBidWithInvalidRequest() {
		Bid bid = createBidRequest();
		Response response = jerseyTest.target("/v1/projects/bids/11").request().put(Entity.entity(bid, MediaType.APPLICATION_JSON));
		Assert.assertEquals(400, response.getStatus());
		String msg = response.readEntity(String.class);
		Assert.assertNotNull(msg);
		JSONObject jsonObject = new JSONObject(msg);
		Assert.assertEquals(ErrorCode.INVALID_REQUEST.toString(), jsonObject.getString("code"));
		Assert.assertEquals(BidMessages.PROJECT_ID_INVALID, jsonObject.getString("message"));
	}
	
	@Test
	public void testGetBidById() {
		Response response = jerseyTest.target("/v1/projects/bids/11").request().get();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertNotNull(response.getEntity());
	}
	
	@Test
	public void testGetBidByInvalidId() {
		Response response = jerseyTest.target("/v1/projects/bids/1").request().get();
		Assert.assertEquals(404, response.getStatus());
		Assert.assertNotNull(response.getEntity());
	}
	
	@Test
	public void testGetBidsByProjectId() {
		Response response = jerseyTest.target("/v1/projects/10/bids").request().get();
		Assert.assertEquals(200, response.getStatus());
		Assert.assertNotNull(response.getEntity());
	}
	
	@Test
	public void testGetBidsByInvalidProjectId() {
		Response response = jerseyTest.target("/v1/projects/1/bids").request().get();
		Assert.assertEquals(404, response.getStatus());
		Assert.assertNotNull(response.getEntity());
	}

	
	private static Project createProject() {
		Project project = new Project();
		project.setTitle("test Title");
		project.setDescription("test description");
		project.setRequirements("test requirements");
		project.setCurrency("USD");
		project.setMaxBudget(122);
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(calendar.getTimeInMillis()+500000);
		project.setLastDate(date);
		
		return project;
	}
	
	static Bid createBidRequest() {
		Bid bid = new Bid();
		bid.setBidAmount(123);
		bid.setBuyer("Test Buyer");
		bid.setCurrency("USD");
		return bid;
	}
	

	
}
