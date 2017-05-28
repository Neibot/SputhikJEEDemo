/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sputnikjeedemo.logic;

import java.util.TreeMap;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;

/**
 *
 * @author martin
 */
@Singleton
@LocalBean
public class LibraryManagerSessionBean {

    private TreeMap <String, Book> publicCatalog = new TreeMap<>();
    private TreeMap <String, Book> privateCatalog = new TreeMap<>();

    @PostConstruct
    private void Debug1()
    {
        System.out.println("EJB создан");
    }
    
    @PreDestroy
    private void Debug2()
    {
        System.out.println("EJB уничтожен");
    }
    
    public String addBook(String name, String author, String publication_date, String catalog)
    {        
        String id = "Ошибка!";
        switch (catalog.toLowerCase())
        {
            case "public":                
                addToCatalog(name,author,publication_date,publicCatalog);
                id = publicCatalog.lastKey();
                break;
            case "private":
                addToCatalog(name,author,publication_date,privateCatalog);
                id = privateCatalog.lastKey();
                break;
            default: 
                System.out.println("Неизвестный каталог "+catalog);
        }
        return id;
    }
    
    private void addToCatalog (String name, String author, String publication_date, TreeMap <String,Book> catalog)
    {
        String id;
        if (!catalog.isEmpty())
            id = String.valueOf(Integer.parseInt(catalog.lastKey())+1);
        else
            id = "0";
        catalog.put(id,new Book(id,name,author,publication_date));        
    }
    
    public void removeBook(String id, String catalog)
    {
        switch (catalog.toLowerCase())
        {
            case "public":
                removeFromCatalog (id, publicCatalog);
                break;
            case "private":
                removeFromCatalog (id, privateCatalog);
                break;
            default: 
                System.out.println("Неизвестный каталог "+catalog);
        }
    }
    
    private void removeFromCatalog (String id, TreeMap <String,Book> catalog)
    {
        if (catalog.containsKey(id))
            catalog.remove(id);
    }
    
    public void editBook(String id, String name, String author, String publication_date, String catalog)
    {
        switch (catalog.toLowerCase())
        {
            case "public":
                editInCatalog(id, name, author, publication_date, publicCatalog);
                break;
            case "private":
                editInCatalog(id, name, author, publication_date, privateCatalog);
                break;
            default: 
                System.out.println("Неизвестный каталог "+catalog);
        }
    }
    
    private void editInCatalog(String id, String name, String author, String publication_date, TreeMap <String,Book> catalog)
    {
        if (catalog.containsKey(id))
            catalog.put(id,new Book(id,name,author,publication_date));
    }
    
    public String changeCatalogOfBook(String id, String newCatalog)
    {
        String new_id = "Ошибка!";
        switch (newCatalog.toLowerCase())
        {
            case "public":
                changeCatalog(id,privateCatalog,publicCatalog);
                new_id = publicCatalog.lastKey();
                break;
            case "private":
                changeCatalog(id,publicCatalog,privateCatalog);
                new_id = privateCatalog.lastKey();
                break;
            default: 
                System.out.println("Неизвестный каталог "+newCatalog);
        }
        return new_id;
    }
    
    private void changeCatalog(String id, TreeMap <String,Book> oldCatalog, TreeMap <String,Book> newCatalog)
    {
        if (oldCatalog.containsKey(id))
        {
            String name = oldCatalog.get(id).getName();
            String author = oldCatalog.get(id).getAuthor();
            String publication_date = oldCatalog.get(id).getPublication_date();
            addToCatalog(name,author,publication_date,newCatalog);
            removeFromCatalog(id,oldCatalog);
        }
    }
    
    public String[] searchBook(String id, String catalog)
    {
        String[] result = null;
        switch (catalog.toLowerCase())
        {
            case "public":
                result = searchInCatalog(id, publicCatalog);
                break;
            case "private":
                result = searchInCatalog(id, privateCatalog);
                break;
            default: 
                System.out.println("Неизвестный каталог "+catalog);
        }
        return result;
    }

    private String[] searchInCatalog(String id, TreeMap <String,Book> catalog)
    {
        String[] result = new String[3];
        if (catalog.containsKey(id))
        {
            result[0] = catalog.get(id).getName();
            result[1] = catalog.get(id).getAuthor();
            result[2] = catalog.get(id).getPublication_date();
        }
        else
            System.out.println("Книга не найдена!");
        return result;            
    }
    
    private class Book
    {
        private String id;        
        private String name;
        private String author;
        private String publication_date;
        
        public Book(String id, String name, String author, String publication_date)
        {
            this.id = id;
            this.name = name;
            this.author = author;
            this.publication_date = publication_date;
        } 
        
        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPublication_date() {
            return publication_date;
        }

        public void setPublication_date(String publication_date) {
            this.publication_date = publication_date;
        }
    }

}
