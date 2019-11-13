package Controller;

import Entity.Place;
import Controller.util.JsfUtil;
import Controller.util.PaginationHelper;
import Entity.Avion;
import Facade.PlaceFacade;

import java.io.Serializable;
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

@Named("placeController")
@SessionScoped
public class PlaceController implements Serializable {
    
    // enregistre place en cours
    private Place current;
    public Place getSelected() {
        if (current == null) {
            current = new Place();
            current.setId(new Entity.PlaceId());
            selectedItemIndex = -1;
        }
        return current;
    }
    
    // liste des places
    private DataModel items = null;
    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        
        System.out.println("Controller.PlaceController.getItems()"+ items.getRowCount());
        return items;
    }

    // facade responsable des base de donnees
    @EJB
    private Facade.PlaceFacade ejbFacade;
    private PlaceFacade getFacade() {
        return ejbFacade;
    }
    
    // pagination   
    private PaginationHelper pagination;
    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    List<Place> places = getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()});
                    for (Place place : places) {
                        System.out.println(place.getId());
                    }
                    return new ListDataModel(places);
                }

                @Override
                public DataModel createPageDataModel(Avion avion) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
        }
        return pagination;
    }

    // indice de l'element en cours
    private int selectedItemIndex;

    // construsteur
    public PlaceController() {
    }

    // get
    public String prepareList() {
        recreateModel();
        return "List";
    }
    public String prepareView() {
        current = (Place) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }
    public String prepareCreate() {
        current = new Place();
        current.setId(new Entity.PlaceId());
        selectedItemIndex = -1;
        return "Create";
    }
    public String prepareEdit() {
        current = (Place) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }
    
    // post
    public String create() {
        try {
            current.getId().setNAvion(current.getAvion().getNAvion());
            current.getId().setNPlace(1);
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PlaceCreated"));
            return prepareCreate();
        } catch (Exception e) {
           
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PlaceUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public String destroy() {
        current = (Place) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
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
    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("PlaceDeleted"));
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

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }
    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }
     public String goPage(int page) {        
        getPagination().setPage(page-1);
        recreateModel();
        return "List";
    }
    
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Place getPlace(Entity.PlaceId id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Place.class)
    public static class PlaceControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            PlaceController controller = (PlaceController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "placeController");
            return controller.getPlace(getKey(value));
        }

        Entity.PlaceId getKey(String value) {
            Entity.PlaceId key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new Entity.PlaceId();
            key.setNAvion(values[0]);
            key.setNPlace(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(Entity.PlaceId value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getNAvion());
            sb.append(SEPARATOR);
            sb.append(value.getNPlace());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Place) {
                Place o = (Place) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Place.class.getName());
            }
        }

    }

}
