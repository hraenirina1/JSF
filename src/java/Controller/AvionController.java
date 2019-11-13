package Controller;

import Controller.MiniController.AvionPlace;
import Entity.Avion;
import Controller.util.JsfUtil;
import Controller.util.PaginationHelper;
import Entity.Place;
import Entity.PlaceId;
import Facade.AvionFacade;
import Facade.PlaceFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;

/* 
 *`classe de controlleur de l'avion    
 *
 */ 
@Named("avionController")
@SessionScoped
public class AvionController implements Serializable {
    
    // facade de l'avion
    // Necessaire pour la persistence de donnee
    @EJB
    private Facade.AvionFacade ejbFacade;
    private AvionFacade getFacade() {
        return ejbFacade;
    }
    
    @EJB
    private Facade.PlaceFacade ejbPlaceFacade;
    private PlaceFacade getPlaceFacade() {
        return ejbPlaceFacade;
    }
    
    // variable pour l'avion en cours
    private Avion current;
    public Avion getSelected() {
        if (current == null) {
            current = new Avion();
            selectedItemIndex = -1;
        }
        return current;
    }
    
    // datatable des elements
    private DataModel items = null;
    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }
    
    // element selectionnee du databable
    private int selectedItemIndex;
    
    // reponsable de la pagination
    private PaginationHelper pagination;
    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {
                
                // nombre d'element
                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }
                
                // datatable
                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(
                            getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize() - 1}));
                }
                
                // datatable
                @Override
                public DataModel createPageDataModel(Avion avion) {
                    return new ListDataModel(
                            getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize() - 1}));
                }
            };
        }
        return pagination;
    }
    
    // details des places
    private AvionPlace avionplace;
    public AvionPlace getAvionplace() {
        if(avionplace==null)
        {
            avionplace = new AvionPlace();
            avionplace.setEjbAvionFacade(ejbFacade);
            avionplace.setEjbPlaceFacade(ejbPlaceFacade);
        }
        return avionplace;
    }
        
    // constructeur
    public AvionController() {
    }

    // affichage des listes
    public String prepareList() {
        items = null;
        return "List";
    }
    public String prepareView() {
        current = (Avion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        
        getAvionplace().createPlace(current);
        return "View";
    }
    public String prepareCreate() {
        current = new Avion();
        selectedItemIndex = -1;
        return "/avion/Create";
    }
    public String prepareEdit() {
        current = (Avion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }
    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    // vrai action post
    public String create() {
        try {
            int i = current.getNbPlace();
            getFacade().create(current);
            
            List<Place> listPlace = new ArrayList<>();
            
            for (int j = 0; j < i; j++) {
                Place place = new Place();
                place.setId(new PlaceId("", j));
                place.setAvion(current);
                listPlace.add(place);
            }           
            
            getPlaceFacade().create(listPlace);
            recreateModel();
            
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AvionCreated"));
            return prepareCreate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AvionUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public String destroy() {
        current = (Avion) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        try {
            
            getFacade().remove(current);
            
            
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AvionDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }
    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AvionDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }
    
    private void recreateModel() {
        items = null;
    }
    private void recreatePagination() {
        pagination = null;
    }

    // pagination suivant precedant
    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }
    public String goPage(int page) {        
        getPagination().setPage(page-1);
        recreateModel();
        return "List";
    }
    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Avion getAvion(java.lang.String id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Avion.class)
    public static class AvionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AvionController controller = (AvionController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "avionController");
            return controller.getAvion(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Avion) {
                Avion o = (Avion) object;
                return getStringKey(o.getNAvion());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Avion.class.getName());
            }
        }

    }

}
