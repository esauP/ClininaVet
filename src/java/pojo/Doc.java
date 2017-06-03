package pojo;


public class Doc implements java.io.Serializable {

    private int iddoc;
    private int idcons;
    private String date_doc;
    private String description;
    public String fileattached;
    private Consultation consultation;

    public Doc() {
    }

    public Doc(int iddoc, int idcons, String date_doc, String description, String fileattached, Consultation consultation) {
        this.iddoc = iddoc;
        this.idcons = idcons;
        this.date_doc = date_doc;
        this.description = description;
        this.fileattached = fileattached;
        this.consultation = consultation;
    }

    public int getIddoc() {
        return iddoc;
    }

    public void setIddoc(int iddoc) {
        this.iddoc = iddoc;
    }

    public int getIdcons() {
        return idcons;
    }

    public void setIdcons(int idcons) {
        this.idcons = idcons;
    }

    public String getDate_doc() {
        return date_doc;
    }

    public void setDate_doc(String date_doc) {
        this.date_doc = date_doc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileattached() {
        return fileattached;
    }

    public void setFileattached(String fileattached) {
        this.fileattached = fileattached;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }

}
