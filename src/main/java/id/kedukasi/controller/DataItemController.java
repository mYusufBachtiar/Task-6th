package id.kedukasi.controller;

import com.oracle.svm.core.annotate.Inject;
import id.kedukasi.model.DataItem;
import io.vertx.core.json.JsonObject;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.xml.validation.Validator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Path("/item")
public class DataItemController {

    @Inject
    Validator validator;
    @Inject
    EntityManager entityManager;

    @POST
    @Transactional
    public Response create(JsonObject request){
        DataItem item = new DataItem();

        item.name = request.getString("name");
        item.count = request.getInteger("count");
        item.price = request.getInteger("price");
        item.type = request.getString("type");
        item.description = request.getString("description");
        item.createdAt = LocalDate.parse(request.getString("createdAt"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        item.updatedAt = LocalDate.parse(request.getString("updatedAt"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        item.persist();

        return Response.ok().entity(new HashMap<>()).build();
    }
    @GET
    public Response list(){
        return Response.ok().entity(DataItem.listAll()).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") Integer id){
        DataItem.deleteById(id);
        return  Response.ok().entity(new HashMap<>()).build();
    }

    @DELETE
    @Path("all")
    @Transactional
    public Response deleteAllDataItem(){
        entityManager.createQuery("DELETE FROM DataItem").executeUpdate();
        return Response.ok().entity(DataItem.listAll()).build();

    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id, JsonObject request){
        DataItem item = DataItem.findById(id);
        item.name = request.getString("name");
        item.count = request.getInteger("count");
        item.price = request.getInteger("price");
        item.type = request.getString("type");
        item.description = request.getString("description");
        item.createdAt = LocalDate.parse(request.getString("createdAt"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        item.updatedAt = LocalDate.parse(request.getString("updatedAt"), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        //save
        item.persist();
        return Response.ok().entity(new HashMap<>()).build();



    }


}
