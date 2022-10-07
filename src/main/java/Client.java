public class Client {
    private String firstName;
    private String lastName;
    private String ID;

    private boolean archive;

    //TODO current rents? List?

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Client{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", ID='").append(ID).append('\'');
        sb.append(", archive=").append(archive);
        sb.append('}');
        return sb.toString();
    }
}
