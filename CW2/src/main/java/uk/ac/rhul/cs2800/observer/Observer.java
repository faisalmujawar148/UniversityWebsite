package uk.ac.rhul.cs2800.observer;

/**
 * Represents an observer that can receive updates.
 */
public interface Observer {

  /**
   * Updates the observer with a message.
   *
   * @param message the message to deliver to the observer.
   */
  void update(String message);
}
