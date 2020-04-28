package ORS.Controller;

 public class URLMapping implements java.io.Serializable 
 {
   // Declare Instance Variables
   private String   eventName;  // Name of the Event
   private String   className;  // Model Class Name
   private String   methodName; // Business Method to be invoked when this event happens
   private String   nextScreen; // Next Screen Page which will be used to show the results

   /**
    * Empty Constructor of this Class
    *
    * @since 1.0
    */
   public URLMapping() {}

   /**
    * Constructor with appropriate parameters.
    * @param  eventName  Name of the Event.
    * @param  className Name of the Class which need to be invoked when this event
    *                   happens.
    * @param  methodName Name of the method which need to be invoked when this event
    *                  happens.
    * @param  nextScreen Name of the JSP Page which will act as a Screen or which will
    *                  represent the data.
    * @param  roles Set of Roles which can access these methods.
    * @since   1.0
    */
   public URLMapping(String eventName, String className, String methodName,
                     String nextScreen) {

     this.eventName  = eventName;
     this.className  = className;
     this.methodName = methodName;
     this.nextScreen = nextScreen;
   }

   /**
    * Method to retrieve the Event Name
    * @return Desired Event Name
    * @since   1.0
    */
   public String getEventName() {
     return eventName;
   }

   /**
    * Method to retrieve the Class Name associated with the event
    * @return Desired Class Name
    * @since   1.0
    */
   public String getClassName() {
     return className;
   }

   /**
    * Method to retrieve the Method Name associated with the event
    * @return Desired Method Name
    * @since   1.0
    */
   public String getMethodName() {
     return methodName;
   }

   /**
    * Method to retrieve the Next Screen associated with the event
    * @return Desired Next Screen Name
    * @since   1.0
    */
   public String getNextScreen() {
     return nextScreen;
   }

   /**
    * Method to set the Event Name
    * @param eventName Name of the Event
    * @since   1.0
    */
   public void setEventName(String eventName) {
     this.eventName = eventName;
   }

   /**
    * Method to set the Class Name associated with the event
    * @param className  Name of the Class
    * @since   1.0
    */
   public void setClassName(String className) {
     this.className = className;
   }

   /**
    * Method to set the Method Name associated with the event
    * @param methodName  Name of the method
    * @since   1.0
    */
   public void setMethodName(String methodName) {
     this.methodName = methodName;
   }

   /**
    * Method to set the Next Screen associated with the event
    * @param nextScreen Next Screen value
    * @since   1.0
    */
   public void setNextScreen(String nextScreen) {
     this.nextScreen = nextScreen;
   }

   /**
    * Method returning String representation this Class. This method overrides the
    * toString() method in java.lang.Object
    * @return String representation of this Class.
    * @since   1.0
    */
   public String toString() {
     return (eventName + " " + className + " " + methodName + " " + nextScreen + "\n");
   }
 }


