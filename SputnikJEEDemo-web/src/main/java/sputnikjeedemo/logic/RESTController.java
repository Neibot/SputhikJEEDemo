/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sputnikjeedemo.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import sputnikjeedemo.logic.book.Book;


/**
 * REST Web Service
 *
 * @author Neibot
 */
@Path("/books")
@RequestScoped
public class RESTController 
{
    @EJB
    private LibraryManagerSessionBean libraryManager;
    
    String htmlStart = "";
    String htmlEnd = "";

    public RESTController() 
    {
        htmlStart = htmlStart.concat("<h3>");
        htmlEnd = htmlEnd.concat("</h3>");
        htmlEnd = htmlEnd.concat("<br><a href='/SputnikJEEDemo-web'>Вернуться назад</a>");        
    }    
        
    @GET
    @Path("/{catalog}/{id}")    
    public String getBook(@PathParam("catalog") String catalog, @PathParam("id") String id) 
    {
        String [] result = libraryManager.searchBook(id, catalog);
        Book book = new Book();
        book.setId(id);
        book.setName(result[0]);
        book.setAuthor(result[1]);
        book.setPublication_date(result[2]);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return htmlStart+gson.toJson(book)+htmlEnd;
    }
    
    @DELETE
    @Path("/{catalog}/{id}")    
    public String removeBook(@PathParam("catalog") String catalog, @PathParam("id") String id) 
    {
        libraryManager.removeBook(id, catalog);                
        return htmlStart+"Книга удалена"+htmlEnd;
    }
    
    @POST
    @Path("/{catalog}")    
    public String addBook(@PathParam("catalog") String catalog, String content) 
    {        
        Book book = fromJson(content);
        String id = libraryManager.addBook(book.getName(), book.getAuthor(), book.getPublication_date(), catalog);        
        return htmlStart+"Книга добавлена в каталог "+catalog+" с присвоением ID "+id+htmlEnd;
    }
    
    @PUT
    @Path("/{catalog}/{id}")    
    public String editBook(@PathParam("catalog") String catalog, String content) 
    {        
        Book book = fromJson(content);
        libraryManager.editBook(book.getId(), book.getName(), book.getAuthor(), book.getPublication_date(), catalog);        
        return htmlStart+"Книга отредактирована"+htmlEnd;
    }   
        
    @PUT
    @Path("/{catalog}/{id}/changecat")    
    public String changeCatalogOfBook(@PathParam("catalog") String newCatalog, @PathParam("id") String id) 
    {
        id = libraryManager.changeCatalogOfBook(id, newCatalog);                
        return htmlStart+"Книга перемещена в каталог "+newCatalog+" c присвоением ID "+id+htmlEnd;
    }
    
    private Book fromJson(String content)
    {
        JsonParser parser = new JsonParser();
        JsonObject jObj = parser.parse(content).getAsJsonObject();
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        Book book = gson.fromJson(jObj, Book.class);
        return book;
    }
}
