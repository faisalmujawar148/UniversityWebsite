package uk.ac.rhul.cs2800.observer;

/**
 * Sends notifications via WhatsApp.
 */
public class WhatsAppNotificationObserver implements Observer {

  /**
   * Prints a message indicating it was sent via WhatsApp.
   *
   * @param message the message to send.
   */
  @Override
  public void update(String message) {
    System.out.println("Message sent via WhatsApp: " + message);
  }
}
