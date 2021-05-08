# Events2
A simple library for event based programming. (Reademe in progress)
## Usage
### The Basic Sender
Declaring a receiver
```java
public class Receivers {
	@Receiver
	public void someReceiver(String string){
		// Handle string
	}
}
```
Declaring and using the default sender
```java
Sender sender = new Sender();
sender.addReceiverMethods(Receivers.class);
sender.send("Some string");
```
### The Event Sender
The event sender uses classes extending Event which provide additional functionality.
```java
public class SomeEvent extends Event {
	private final String string;
	public SomeEvent(String string){
		this.string = string;
	}

	public String getString(){
		return string;
	}
}

public class Receivers {
	@Receiver
	public void eventReceiver(SomeEvent event){
		System.out.println(event.getString());
		// Events also can be cancelled if sent through an event sender
		event.setCancelled(true);
	}
}
```
Event senders can be declared and used similarly, but only with classes extending events.
```java
EventSender eventSender = new EventSender();
eventSender.addReceiverMethods(Receivers.class);
eventSender.send(new SomeEvent("A string"));
```
### Sender Constructors
The sender and event sender classes use similar constructors
Declares the default sender
```java
new Sender();
```
Declares a named sender whose receivers cannot receive from the default sender.
```java
new Sender(String name);
```
Declares a named sender where if allowGlobal is true unnamed receivers can also receive from in addition to similarly named.
```java
new Sender(String name, boolean allowGlobal);
```
### Receivers
Receivers are declared with the `@Receiver` annotation. Without any arguments the receiver receives from the default sender and named senders that allow it. The arguments are as follows:
**source** The name of the sender the receiver should receive from, unnamed by default. A sender can specify whether or not it sends to unnamed receivers. An array of names is allowed.
**priority** When there are multiple receivers that would receive an object the priority determines the order in which they happen. Default is medium.
**ignoreCancelled** Only used by event senders. If true the event method will be fired even if it was previously cancelled. Default is false.
