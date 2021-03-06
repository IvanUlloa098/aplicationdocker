package ec.edu.ups.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;


@FacesConfig(version = FacesConfig.Version.JSF_2_3)
@Named
@SessionScoped
public class PersonaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String nombre;
	private String telefono;
	
	private ArrayList<Object> listdata;
	
	public PersonaBean() {
		
	}
	
	@PostConstruct
    public void init(){
        
		System.out.println("***PersonaBean INICIALIZADO***");
		
    }

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public ArrayList<Object> getListdata() {
		return listdata;
	}

	public void setListdata(ArrayList<Object> listdata) {
		this.listdata = listdata;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void createContact() {
		
		System.out.println(">>>>> "+nombre);
		System.out.println(">>>>> "+telefono);
		
		HttpClient httpclient = new DefaultHttpClient();
		
		try {
			
			HttpPost httpPost = new HttpPost("http://srvwildfly:8080/apidocker/rs/hola/register");
			System.out.println("----------------------------------------");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("nombre",nombre));
            nameValuePairs.add(new BasicNameValuePair("telefono",telefono));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
            System.out.println("----------------------------------------");
            System.out.println("executing request " + httpPost.getRequestLine());
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            System.out.println("----------------------------------------");
           
            System.out.println(response.getStatusLine());
            if (resEntity != null) {
                System.out.println("Response content length: " + resEntity.getContentLength());
                System.out.println("Chunked?: " + resEntity.isChunked());
                String responseBody = EntityUtils.toString(resEntity);
                System.out.println("Data: " + responseBody);
            }
            
            
            EntityUtils.consume(resEntity);
            System.out.println("*****CREATED*****");
	        
	        
			
		} catch (Exception e) {
			System.out.println("*****AN ERROR HAS OCURRED*****\n"+e);
		} finally {
            
            httpclient.getConnectionManager().shutdown();
        }
		
		
	}
	
	public void modifyContact() {
		
		HttpClient httpclient = new DefaultHttpClient();
		
		try {
			
			HttpPost httpPost = new HttpPost("http://srvwildfly:8080/apidocker/rs/hola/modify");
			System.out.println("----------------------------------------");
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
            nameValuePairs.add(new BasicNameValuePair("id",id));
            nameValuePairs.add(new BasicNameValuePair("nombre",nombre));
            nameValuePairs.add(new BasicNameValuePair("telefono",telefono));
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
            System.out.println("----------------------------------------");
            System.out.println("executing request " + httpPost.getRequestLine());
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity resEntity = response.getEntity();
            System.out.println("----------------------------------------");
           
            System.out.println(response.getStatusLine());
            if (resEntity != null) {
                System.out.println("Response content length: " + resEntity.getContentLength());
                System.out.println("Chunked?: " + resEntity.isChunked());
                String responseBody = EntityUtils.toString(resEntity);
                System.out.println("Data: " + responseBody);
            }
            
            
            EntityUtils.consume(resEntity);
            System.out.println("*****MODIFIED*****");
	        
	        
			
		} catch (Exception e) {
			System.out.println("*****AN ERROR HAS OCURRED*****\n"+e);
		} finally {
            
            httpclient.getConnectionManager().shutdown();
        }
	}
	
	public void redirectPersona() {
		
		HttpClient client = new DefaultHttpClient();
	    HttpGet request = new HttpGet("http://srvwildfly:8080/apidocker/rs/hola/list");
	    HttpResponse response;
	    String result = null;
	    try {
	        response = client.execute(request);         
	        HttpEntity entity = response.getEntity();

	        if (entity != null) {

	            // A Simple JSON Response Read
	            InputStream instream = entity.getContent();
	            result = convertStreamToString(instream);
	            // now you have the string representation of the HTML request
	            System.out.println("RESPONSE: " + result);
	            instream.close();
	            if (response.getStatusLine().getStatusCode() == 200) {
	                System.out.println("**DONE");
	            }

	        }
	        // Headers
	        org.apache.http.Header[] headers = response.getAllHeaders();
	        for (int i = 0; i < headers.length; i++) {
	            System.out.println(headers[i]);
	        }
	    } catch (ClientProtocolException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    } catch (IOException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    }
	    
	    result = "{ \"data\" :"+result+"}";
	    
	    System.out.println("THIS IS THE RESULT>>>> "+result);
	    JSONObject myObject = new JSONObject(result);
	    
	    
	    listdata = new ArrayList<Object>();  
	    JSONArray jArray = myObject.getJSONArray("data");
	    
	    if (jArray != null) {   
            
            //Iterating JSON array  
            for (int i=0;i<jArray.length();i++){   
                  
                //Adding each element of JSON array into ArrayList  
                listdata.add(jArray.get(i));  
            }   
        }  
	    
	    System.out.println("JSON>>>> "+ listdata);
		
		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "list.xhtml");
	}
	
	
	
	
	public void deleteContact() {
		HttpClient client = new DefaultHttpClient();
	    HttpGet request = new HttpGet("http://srvwildfly:8080/apidocker/rs/hola/delete/"+id);
	    HttpResponse response;
	    String result = null;
	    try {
	        response = client.execute(request);         
	        HttpEntity entity = response.getEntity();

	        if (entity != null) {

	            // A Simple JSON Response Read
	            InputStream instream = entity.getContent();
	            result = convertStreamToString(instream);
	            // now you have the string representation of the HTML request
	            System.out.println("RESPONSE: " + result);
	            instream.close();
	            if (response.getStatusLine().getStatusCode() == 200) {
	                System.out.println("**DONE");
	            }

	        }
	        // Headers
	        org.apache.http.Header[] headers = response.getAllHeaders();
	        for (int i = 0; i < headers.length; i++) {
	            System.out.println(headers[i]);
	        }
	    } catch (ClientProtocolException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    } catch (IOException e1) {
	        // TODO Auto-generated catch block
	        e1.printStackTrace();
	    }
	}
	
	private static String convertStreamToString(InputStream is) {

	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}
	
	
}
