package Controller;

import Controller.MiniController.AvionPlace;
import Entity.Vol;
import Controller.util.JsfUtil;
import Controller.util.PaginationHelper;
import Entity.Avion;
import Facade.VolFacade;
import java.io.Serializable;
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

@Named("volController")
@SessionScoped
public class VolController implements Serializable {
    
    // element selectionne
    private Vol current;
    public Vol getSelected() {
        if (current == null) {
            current = new Vol();
            selectedItemIndex = -1;
        }
        return current;
    }
    
    private DataModel items = null;
     public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    // responsables DAO
    @EJB
    private Facade.VolFacade ejbFacade;
    private VolFacade getFacade() {
        return ejbFacade;
    }
    @EJB
    private Facade.AvionFacade ejbAvionFacade;
    private Facade.AvionFacade getAvionFacade() {
        return ejbAvionFacade;
    }
    @EJB
    private Facade.PlaceFacade ejbPlaceFacade;
    private Facade.PlaceFacade getPlaceFacade() {
        return ejbPlaceFacade;
    }
    
    // pagination des listes de Vols
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
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }

                @Override
                public DataModel createPageDataModel(Avion avion) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            };
        }
        return pagination;
    }

    private int selectedItemIndex;

    
    // constructeur
    public VolController() {
    }

    // GET CRUD   
    public String prepareList() {
        recreateModel();
        return "List";
    }
    public String prepareView() {
        current = (Vol) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        
        getAvionplace().createPlace(current.getAvion());
        return "View";
    }
    public String prepareCreate() {
        current = new Vol();
        selectedItemIndex = -1;
        return "Create";
    }
    public String prepareEdit() {
        current = (Vol) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }
    
    // POST CRUD
    public String create() {
        try {
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VolCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VolUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }
    public String destroy() {
        current = (Vol) getItems().getRowData();
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
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("VolDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }
    
    // Affichage du tableau
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

    /* Visualtisation des voyageurs */
    // details des places
    private AvionPlace avionplace;
    public AvionPlace getAvionplace() {
        if(avionplace==null)
        {
            avionplace = new AvionPlace();
            avionplace.setEjbAvionFacade(ejbAvionFacade);
            avionplace.setEjbPlaceFacade(ejbPlaceFacade);
        }
        return avionplace;
    }
    
    // listes a selectionne
    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }
    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    // Util dans get Objet
    public Vol getVol(int id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Vol.class)
    public static class VolControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            VolController controller = (VolController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "volController");
            return controller.getVol(getKey(value));
        }

        int getKey(String value) {
            int key;
            key = Integer.parseInt(value);
            return key;
        }

        String getStringKey(int value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Vol) {
                Vol o = (Vol) object;
                return getStringKey(o.getNVol());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Vol.class.getName());
            }
        }

    }

}
