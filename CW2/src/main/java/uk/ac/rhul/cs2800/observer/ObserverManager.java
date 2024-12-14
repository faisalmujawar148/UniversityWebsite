package uk.ac.rhul.cs2800.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages a list of observers and notifies them of updates.
 */
public class ObserverManager {
  private static ObserverManager manager;
  List<Observer> observers;

  /**
   * Private constructor to enforce singleton pattern.
   */
  private ObserverManager() {
    observers = new ArrayList<>();
  }

  /**
   * Returns the singleton instance of ObserverManager.
   *
   * @return the single instance of ObserverManager
   */
  public static ObserverManager getInstance() {
    if (manager == null) {
      manager = new ObserverManager();
    }
    return manager;
  }

  /**
   * Subscribes an observer to receive updates.
   *
   * @param observer the observer to add
   */
  public void subscribe(Observer observer) {
    observers.add(observer);
  }

  /**
   * Notifies all subscribed observers with a message.
   *
   * @param message the message to send to observers
   */
  public void notify(String message) {
    for (Observer observer : observers) {
      observer.update(message);
    }
  }

  /**
   * Clears all subscribed observers.
   */
  public void clearObservers() {
    observers.clear();
  }
}
