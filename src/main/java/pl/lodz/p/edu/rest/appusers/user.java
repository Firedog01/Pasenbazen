
package pl.lodz.p.edu.rest.appusers;

import java.util.UUID;

public abstract class user {
    private UUID uuid;
    private boolean isActive = false;
    private String login;

    protected user(String login) {
        this.uuid = UUID.randomUUID();
        this.login = login; //fixme how to make this random?
        // Database and unique bruh
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getLogin() {
        return login;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setLogin(String login) {
        this.login = login;
    }
}
