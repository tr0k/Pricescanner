package pl.tr0k.pricescanner.server.manage;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.teemu.clara.Clara;
import org.vaadin.teemu.clara.binder.annotation.UiField;
import org.vaadin.teemu.clara.binder.annotation.UiHandler;
import pl.tr0k.pricescanner.server.domain.Advertisement;
import pl.tr0k.pricescanner.server.manage.util.InMemoryUploadReceiver;
import pl.tr0k.pricescanner.server.service.IAdvertisementService;

import java.io.*;

@Configurable(preConstruction = true)
public class AdvertisementView extends CssLayout implements View
{

    @UiField
    private Table advertisementsList;

    @UiField
    private FormLayout editorLayout;

    @UiField
    private Upload advertisementUpload;

    @UiField
    private CheckBox activeCheckbox;

    private byte[] uploadedAdvertisement;

    private String fileName;

    @Autowired
    private IAdvertisementService advertisementService;

    public AdvertisementView() {
        addComponent(Clara.create("AdvertisementsView.xml", this));
        setSizeFull();
    }

    private void refreshView() {
        advertisementUpload.setImmediate(true);

        final InMemoryUploadReceiver receiver = new InMemoryUploadReceiver();

        advertisementUpload.setReceiver(receiver);
        advertisementUpload.addStartedListener(receiver);
        advertisementUpload.addSucceededListener(new Upload.SucceededListener() {
            public void uploadSucceeded(Upload.SucceededEvent event) {
                uploadedAdvertisement = receiver.getUpload();
                fileName = receiver.getFilename();
            }
        });

        refreshAdvertisementsList();

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        refreshView();
    }

    @UiHandler("removeAdvertisementButton")
    public void removeSelectedProduct(Button.ClickEvent event) {
        ConfirmDialog.show(getUI(), "Potwierdź usunięcie", "Czy na pewno usunąć wybraną reklamę?", "Ok", "Anuluj",
                new ConfirmDialog.Listener() {
                    @Override
                    public void onClose(ConfirmDialog components) {
                        if (components.isConfirmed()) {
                            Object advertisementId = advertisementsList.getValue();
                            advertisementService.deleteAdvertisement((Long) advertisementId);
                            refreshAdvertisementsList();
                        }
                    }
                });
    }

    @UiHandler("saveAdvertisementButton")
    public void saveSelectedProduct(Button.ClickEvent event) {
        Object advertisementId = advertisementsList.getValue();
        Advertisement advertisement = null;
        if (advertisementId != null) {
            advertisement = advertisementService.findAdvertisement((Long) advertisementId);
        }
        if (advertisement == null) {
            advertisement = new Advertisement();
        }

        if (uploadedAdvertisement != null) {
            advertisement.setName(fileName);
            String pathname = fileName;
            File file = new File(pathname);
            try {
                if (file.createNewFile()) {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(uploadedAdvertisement);
                }
            } catch (IOException e) {
                Notification.show("Błąd zapisu pliku", Notification.Type.ERROR_MESSAGE);
                return;
            }
            advertisement.setPath(pathname);
        }
        advertisement.setActive(activeCheckbox.getValue());

        advertisementService.saveAdvertisement(advertisement);

        refreshAdvertisementsList();
    }

    @UiHandler("newAdvertisementButton")
    public void addNewProduct(Button.ClickEvent event) {
        advertisementsList.select(null);

        activeCheckbox.setValue(true);
        editorLayout.setVisible(true);
        uploadedAdvertisement = null;
    }

    private void refreshAdvertisementsList() {
        BeanContainer<Long, Advertisement> advertisementContainer = new BeanContainer<Long, Advertisement>(Advertisement.class);

        Iterable<Advertisement> advertisements = advertisementService.findAllAdvertisements();

        for (Advertisement advertisement : advertisements) {
            advertisementContainer.addItem(advertisement.getId(), advertisement);
        }

        advertisementsList.setContainerDataSource(advertisementContainer);
        advertisementsList.refreshRowCache();

        advertisementsList.setVisibleColumns("no", "name");
        advertisementsList.setColumnHeaders("LP", "Nazwa");
    }

    @UiHandler("advertisementsList")
    public void advertisementSelected(Property.ValueChangeEvent event) {
        Object advertisementId = advertisementsList.getValue();

        uploadedAdvertisement = null;

        if (advertisementId != null) {
            Advertisement bean = ((BeanItem<Advertisement>) advertisementsList.getItem(advertisementId)).getBean();
            advertisementUpload.setEnabled(true);
            activeCheckbox.setValue(bean.isActive());
        }

        editorLayout.setVisible(advertisementId != null);
    }

}
