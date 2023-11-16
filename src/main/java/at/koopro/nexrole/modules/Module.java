package at.koopro.nexrole.modules;

public interface Module {
    void activate();
    void deactivate();
    boolean isActive();
    String getName();
    // Add other necessary methods
}
