package at.koopro.nexrole.roles;

public class Role {

    private final String name;
    private final String prefix;

    public Role(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }
}
