package model;

public class Client {
    private String firstName;
    private String lastName;
    private String ID;

    private idType idType;
    private boolean archive;

    //TODO current rents? List?


    public Client(String firstName, String lastName, String ID, model.idType idType, boolean archive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ID = ID;
        this.idType = idType;
        this.archive = archive;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("model.Client{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", ID='").append(ID).append('\'');
        sb.append(", archive=").append(archive);
        sb.append('}');
        return sb.toString();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getID() {
        return ID;
    }

    public model.idType getIdType() {
        return idType;
    }

    public boolean isArchive() {
        return archive;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setIdType(model.idType idType) {
        this.idType = idType;
    }

    public void setArchive(boolean archive) {
        this.archive = archive;
    }
}
