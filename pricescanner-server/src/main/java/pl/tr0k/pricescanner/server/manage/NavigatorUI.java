package pl.tr0k.pricescanner.server.manage;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.vaadin.teemu.clara.Clara;
import org.vaadin.teemu.clara.binder.annotation.UiField;

public class NavigatorUI extends UI {

    private Navigator navigator;

    protected static final String PRODUCTS_VIEW = "products";

    protected static final String ADVERTISEMENTS_VIEW = "advertisements";

    @UiField
    private CssLayout mainLayout;

    @UiField
    private MenuBar menuBar;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        getPage().setTitle("Sprawdzarka Panel");
        setContent(Clara.create("MainUI.xml", this));

        createNavigator();
        createMenuBar();
    }

    private void createNavigator() {
        navigator = new Navigator(this, mainLayout);
        navigator.addView(ADVERTISEMENTS_VIEW, new AdvertisementView());
        ProductsView productsView = new ProductsView();
        navigator.addView(PRODUCTS_VIEW, productsView);
        navigator.addView("", productsView);
    }

    private void createMenuBar() {
        menuBar.addItem("Produkty", new NavigationCommand(PRODUCTS_VIEW, navigator));
        menuBar.addItem("Reklamy", new NavigationCommand(ADVERTISEMENTS_VIEW, navigator));
    }

    class NavigationCommand implements MenuBar.Command {

        private final String navigationState;

        private final Navigator navigator;

        NavigationCommand(String navigationState, Navigator navigator) {
            this.navigationState = navigationState;
            this.navigator = navigator;
        }

        @Override
        public void menuSelected(MenuBar.MenuItem menuItem) {
            navigator.navigateTo(navigationState);
        }

    }
}
