package test.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.star.cms.model.Area;

/**
 * 
 * @author Administrator
 *
 */
@Path(value = "taobao")
public class SpiderController {
	Log log = LogFactory.getLog(SpiderController.class);
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Area> getAreas() {
		return null;
	}
	
}
