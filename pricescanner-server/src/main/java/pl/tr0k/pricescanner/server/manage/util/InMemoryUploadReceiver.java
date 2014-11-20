package pl.tr0k.pricescanner.server.manage.util;

import com.vaadin.ui.Upload;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;


public class InMemoryUploadReceiver implements Upload.Receiver, Upload.StartedListener {

    private static final long serialVersionUID = -1276759102490466761L;

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    private String filename;

    private String mimeType;

    @Override
    public OutputStream receiveUpload(String filename, String mimeType) {
        this.filename = filename;
        this.mimeType = mimeType;
        return outputStream;
    }

    @Override
    public void uploadStarted(Upload.StartedEvent startedEvent) {
        outputStream.reset();
    }

    public byte[] getUpload() {
        return outputStream.toByteArray();
    }

    public String getFilename() {
        return filename;
    }

    public String getMimeType() {
        return mimeType;
    }
}