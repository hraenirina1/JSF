package Controller.MiniController;

import Controller.util.PaginationHelper;
import Entity.Avion;
import Entity.Place;
import Facade.AvionFacade;
import Facade.PlaceFacade;
import java.util.List;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

public class AvionPlace {
    private Avion current;
    
    private Facade.PlaceFacade ejbPlaceFacade;
    private PlaceFacade getPlaceFacade() {
        return ejbPlaceFacade;
    }
    public void setEjbPlaceFacade(PlaceFacade ejbPlaceFacade) {
        this.ejbPlaceFacade = ejbPlaceFacade;
    }
    
    private Facade.AvionFacade ejbAvionFacade;
    private AvionFacade getAvionFacade() {
        return ejbAvionFacade;
    }
    public void setEjbAvionFacade(AvionFacade ejbAvionFacade) {
        this.ejbAvionFacade = ejbAvionFacade;
    }
    
    // reponsable de la pagination
    private PaginationHelper pagination;
    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                
                // nombre d'element
                @Override
                public int getItemsCount() {
                    return getPlaceFacade().count();
                }
                
                // datatable
                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(
                            getPlaceFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize() - 1}));
                }

                @Override
                public DataModel createPageDataModel(Avion avion) {
                    List<Place> place = getPlaceFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize() - 1},avion);
                    
                    return new ListDataModel(
                            getPlaceFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize() - 1},avion));}
            };
        }
        return pagination;
    }
    
    // datatable des elements
    private DataModel listePlace = null;
    public DataModel getListePlace() {
        return listePlace;
    }
    public void createPlace(Avion avion) {
            current = avion;
           listePlace = getPagination().createPageDataModel(avion);           
    }
    
    public AvionPlace() {
    }
    
    // pagination suivant precedant
    public void next() {
        getPagination().nextPage();
        listePlace = getPagination().createPageDataModel(current);   
    }
    public void previous() {
        getPagination().previousPage();
        listePlace = getPagination().createPageDataModel(current);   
    }

    @Override
    public String toString() {
        return "AvionPlace{" + '}';
    }
    
}
