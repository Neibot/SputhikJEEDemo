function DataAlert (string)
{
    alert("Полученные данные: " + string);
}

function ReplaceBody (newBodyCode)
{    
    $('body').html(newBodyCode);
    document.title = "Успех!";
}

function AddToCatalog(catalog)
{    
    var tempObject =
    {            
        "name":document.getElementById('name_add').value,
        "author":document.getElementById('author_add').value,
        "publication_date":document.getElementById('publication_date_add').value
    };
    var json_string = JSON.stringify(tempObject);    
    $.post("/SputnikJEEDemo-web/rest/books/"+catalog, json_string, function( data ) 
    { 
        ReplaceBody(data); 
    });    
}

function EditInCatalog(catalog)
{
    var tempObject =
    {   
        "id":document.getElementById('id_edit').value,
        "name":document.getElementById('name_edit').value,
        "author":document.getElementById('author_edit').value,
        "publication_date":document.getElementById('publication_date_edit').value
    };
    var json_string = JSON.stringify(tempObject);
    $.put("/SputnikJEEDemo-web/rest/books/"+catalog+"/"+document.getElementById('id_edit').value, json_string, function( data ) 
    { 
        ReplaceBody(data); 
    });    
}

function SearchInCatalog(catalog)
{    
    var id = document.getElementById('id_search').value;
    $.get("/SputnikJEEDemo-web/rest/books/"+catalog+"/"+id, function( data ) 
    { 
        ReplaceBody(data); 
    });    
}

function RemoveFromCatalog(catalog)
{    
    var id = document.getElementById('id_remove').value;
    $.delete("/SputnikJEEDemo-web/rest/books/"+catalog+"/"+id, function( data ) 
    { 
        ReplaceBody(data); 
    });
}

function ChangeCatalog(newcatalog)
{    
    var id = document.getElementById('id_change').value;
    $.put("/SputnikJEEDemo-web/rest/books/"+newcatalog+"/"+id+"/changecat", function( data ) 
    { 
        ReplaceBody(data); 
    });
}

var myCalendar;
function doOnLoad() 
{
    myCalendar = new dhtmlXCalendarObject(["publication_date_add","publication_date_edit"]);
}

jQuery.each( [ "put", "delete" ], function( i, method ) {
  jQuery[ method ] = function( url, data, callback, type ) {
    if ( jQuery.isFunction( data ) ) {
      type = type || callback;
      callback = data;
      data = undefined;
    }
 
    return jQuery.ajax({
      url: url,
      type: method,
      dataType: type,
      data: data,
      success: callback
    });
  };
});
