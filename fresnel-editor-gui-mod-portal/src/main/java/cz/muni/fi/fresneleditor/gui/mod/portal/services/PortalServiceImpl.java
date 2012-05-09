/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.fresneleditor.gui.mod.portal.services;

import cz.muni.fi.fresneleditor.gui.mod.portal.model.Service;
import cz.muni.fi.fresneleditor.gui.mod.portal.model.Transformation;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author nodrock
 */
public class PortalServiceImpl implements PortalService {
    
    String portalURL = null;

    public PortalServiceImpl(String portalURL) {
        this.portalURL = portalURL;
    }
    
    @Override
    public List<Service> getServices() {
        List<Service> services = new ArrayList<Service>();
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(portalURL + "/rest/services.json");
            httpGet.setHeader("Accept","application/json");
            
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpClient.execute(httpGet, responseHandler);
            JSONObject obj = new JSONObject(responseBody);
            JSONArray arr = obj.getJSONArray("services");
            for(int i = 0; i<arr.length(); i++){
                JSONObject serv = (JSONObject) arr.get(i);
                Service service = new Service(serv.getInt("id"), serv.getString("name"), serv.getString("url"));
                services.add(service);
                System.out.println(service.toString());
            }
            
            //HttpPost httppost = new HttpPost("http://localhost:8080/fresnelportal/rest/upload");
            //HttpPost httppost = new HttpPost("http://localhost:8080/fresnelportal/render.htm");
            
            //FileBody bin = new FileBody(new File("test.n3"));
//            StringBody selectedGroup = new StringBody("http://www.fi.muni.cz/fresnel-editor#airportVizGroup");
//            StringBody selectedService = new StringBody("1");
//            StringBody selectedTransformation = new StringBody("0");
//
//            MultipartEntity reqEntity = new MultipartEntity();
            //reqEntity.addPart("file", bin);
//            reqEntity.addPart("selectedGroup", selectedGroup);
//            reqEntity.addPart("selectedService", selectedService);
//            reqEntity.addPart("selectedTransformation", selectedTransformation);
//            reqEntity.addPart("projectId", new StringBody("11"));
            

//            httppost.setEntity(reqEntity);
//            httppost.setHeader("Accept","text/html");

//            System.out.println("executing request " + httppost.getRequestLine());
            //HttpResponse response = httpclient.execute(httppost);
            
//            ResponseHandler<String> responseHandler = new BasicResponseHandler();
//            String responseBody = httpclient.execute(httppost, responseHandler);
//            System.out.println("----------------------------------------");
//            System.out.println(responseBody);
//            System.out.println("----------------------------------------");
        } catch (JSONException ex){          
            return services;
        } catch (IOException ex){
            return services;
        } finally {
            try { httpClient.getConnectionManager().shutdown(); } catch (Exception ignore) {}
        }
        services.add(0, new Service(0, "Semantic Web Client", null));
        return services;
    }

    @Override
    public List<Transformation> getTransformations() {
        List<Transformation> transformations = new ArrayList<Transformation>();
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet httpGet = new HttpGet(portalURL + "/rest/transformations.json");
            httpGet.setHeader("Accept","application/json");
            
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpClient.execute(httpGet, responseHandler);
            JSONObject obj = new JSONObject(responseBody);
            JSONArray arr = obj.getJSONArray("transformations");
            for(int i = 0; i<arr.length(); i++){
                JSONObject t = (JSONObject) arr.get(i);
                Transformation transformation = new Transformation(t.getInt("id"), t.getString("name"), t.getString("filename"), t.getString("contentType"));
                transformations.add(transformation);
            }
        } catch (JSONException ex){          
            return transformations;
        } catch (IOException ex){
            return transformations;
        } finally {
            try { httpClient.getConnectionManager().shutdown(); } catch (Exception ignore) {}
        }
        transformations.add(0, new Transformation(0, "No transformation", null, "application/xml"));
        return transformations;
    }

    @Override
    public Integer uploadProject(InputStream project) {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/fresnelportal/rest/upload");
            InputStreamBody isb = new InputStreamBody(project, "application/n3", "project.n3");
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("file", isb);
            httpPost.setEntity(reqEntity);
            httpPost.setHeader("Accept","application/json");
            
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpClient.execute(httpPost, responseHandler);
            
            JSONObject obj = new JSONObject(responseBody);
            return obj.getInt("id");          
        } catch (JSONException ex){          
            return null;
        } catch (IOException ex){
            return null;
        } finally {
            try { httpClient.getConnectionManager().shutdown(); } catch (Exception ignore) {}
        }
    }

    @Override
    public boolean visualizeProject(Integer projectId, String group, Integer service, Integer transformation, OutputStream result) {
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost httpPost = new HttpPost("http://localhost:8080/fresnelportal/render.htm");
            
            MultipartEntity reqEntity = new MultipartEntity();
            reqEntity.addPart("selectedGroup", new StringBody(group));
            reqEntity.addPart("selectedService", new StringBody(service.toString()));
            reqEntity.addPart("selectedTransformation", new StringBody(transformation.toString()));
            reqEntity.addPart("projectId", new StringBody(projectId.toString()));
            
            httpPost.setEntity(reqEntity);
            //            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            //            String responseBody = httpClient.execute(httpPost, responseHandler);
            HttpResponse response = httpClient.execute(httpPost);
            response.getEntity().writeTo(result);
         
            return true;
        } catch (IOException ex){
            return false;
        } finally {
            try { httpClient.getConnectionManager().shutdown(); } catch (Exception ignore) {}
        }
    }

}
