package Controller.util;

import Entity.Avion;
import java.util.ArrayList;
import java.util.List;
import javax.faces.model.DataModel;

public abstract class PaginationHelper {

    private final int pageSize;
    public int getPageSize() {
        return pageSize;
    }

    
    private int page;
    
    // constrcuteur
    public PaginationHelper(int pageSize) {
        this.pageSize = pageSize;
    }

    // nombres d'occurrence
    public abstract int getItemsCount();

    // Datamodel
    public abstract DataModel createPageDataModel();
    public abstract DataModel createPageDataModel(Avion avion) ;
    
    // recuperer le numero de la premiere element
    public int getPageFirstItem() {
        return page * pageSize;
    }

    // recuperer le numero de la derniere
    public int getPageLastItem() {
        
        // -1 pour avoir le numero d'indice
        int i = getPageFirstItem() + pageSize - 1;
        int count = getItemsCount() - 1;
        
        // si le nombre depasse la limite ramener a la limite
        if (i > count) {
            i = count;
        }
        
        // si le nombre descend en dessous de 0 remene a 0
        if (i < 0) {
            i = 0;
        }
        
        return i;
    }

    // teste suivant ou non
    public boolean isHasNextPage() {
        return (page + 1) * pageSize + 1 <= getItemsCount();
    }
    public boolean isHasPreviousPage() {
            return page > 0;
    }    
    public String NextExiste()
    {
        if(!isHasNextPage()) return "disabled";
        else return "";
    }
    public String PreviousExiste()
    {
        if(!isHasPreviousPage()) return "disabled";
        else return "";
    }

    // suivant
    public void nextPage() {
        if (isHasNextPage()) {
            page++;
        }
    }
    public void previousPage() {
        if (isHasPreviousPage()) {
            page--;
        }
    }
    public void setPage(int text)
    {
        this.page = text;
    }
    public List<Integer> list_page()
    {
        int nb = getItemsCount() / pageSize + 1;
        if(getItemsCount()%pageSize == 0) nb--;
        
        List<Integer> list_page = new ArrayList<>();
        
        for (int i = 1; i <= nb; i++) {
            if(i==nb || i==1 || (i>= page-1 && i<=page+3))            
            list_page.add(i);
        }
        
        return list_page;
    }
    public int getPage() {
        return page;
    }
}
