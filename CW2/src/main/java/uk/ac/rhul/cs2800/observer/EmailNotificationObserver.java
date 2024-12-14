package uk.ac.rhul.cs2800.observer;

/**
 * Sends notifications via email.
 */
public class EmailNotificationObserver implements Observer {
  /**
   * Prints a message indicating it was sent via email.
   *
   * @param message the message to send.
   */
  @Override
  public void update(String message) {
    System.out.println("Message sent via email: " + message);

  }

}


