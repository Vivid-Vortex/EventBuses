Chain of Responsibility Design Pattern
---

The **Chain of Responsibility (CoR) pattern** is a <mark>behavioral design pattern</mark> used to decouple a sender of requests from their receivers. This pattern allows multiple objects to handle a request without hardcoding the sender-receiver relationship. The requests are passed along a chain of handlers, and each handler decides whether to process the request or pass it to the next handler in the chain.

---

### **Key Concepts**
1. **Handler**: An interface or abstract class defining the method to handle a request and a reference to the next handler in the chain.
2. **Concrete Handlers**: Specific implementations of the handler, each responsible for processing specific types of requests.
3. **Client**: Initiates the request and sets up the chain.

---

### **How It Works**
1. A request is sent to the first handler in the chain.
2. If the handler can process the request, it does so.
3. If the handler cannot process the request, it passes the request to the next handler in the chain.
4. The chain continues until a handler processes the request or no handlers are left.

---

### **Example in Real Life**
- **Support Tickets**: A support system where tickets go through levels of support staff. For example:
    - Level 1 support handles basic issues.
    - If Level 1 cannot resolve the issue, it escalates to Level 2.
    - Level 2 escalates to Level 3 if needed.

---

### **Code Example in Java**

#### **1. Define the Handler Interface**
```java
public abstract class Handler {
    protected Handler next;

    // Set the next handler in the chain
    public void setNext(Handler next) {
        this.next = next;
    }

    // Handle the request
    public abstract void handleRequest(String request);
}
```

---

#### **2. Create Concrete Handlers**
```java
public class LowLevelHandler extends Handler {
    @Override
    public void handleRequest(String request) {
        if (request.equals("low")) {
            System.out.println("LowLevelHandler handled the request.");
        } else if (next != null) {
            next.handleRequest(request);
        } else {
            System.out.println("No handler found for the request.");
        }
    }
}

public class MidLevelHandler extends Handler {
    @Override
    public void handleRequest(String request) {
        if (request.equals("mid")) {
            System.out.println("MidLevelHandler handled the request.");
        } else if (next != null) {
            next.handleRequest(request);
        } else {
            System.out.println("No handler found for the request.");
        }
    }
}

public class HighLevelHandler extends Handler {
    @Override
    public void handleRequest(String request) {
        if (request.equals("high")) {
            System.out.println("HighLevelHandler handled the request.");
        } else if (next != null) {
            next.handleRequest(request);
        } else {
            System.out.println("No handler found for the request.");
        }
    }
}
```

---

#### **3. Set Up the Chain**
```java
public class ChainOfResponsibilityExample {
    public static void main(String[] args) {
        // Create handlers
        Handler low = new LowLevelHandler();
        Handler mid = new MidLevelHandler();
        Handler high = new HighLevelHandler();

        // Build the chain
        low.setNext(mid);
        mid.setNext(high);

        // Test the chain
        System.out.println("Sending request 'low':");
        low.handleRequest("low");

        System.out.println("Sending request 'mid':");
        low.handleRequest("mid");

        System.out.println("Sending request 'high':");
        low.handleRequest("high");

        System.out.println("Sending request 'unknown':");
        low.handleRequest("unknown");
    }
}
```

---

#### **Output**
```
Sending request 'low':
LowLevelHandler handled the request.

Sending request 'mid':
MidLevelHandler handled the request.

Sending request 'high':
HighLevelHandler handled the request.

Sending request 'unknown':
No handler found for the request.
```

---

### **Advantages**
1. **Flexibility**: Easy to add new handlers without modifying existing code.
2. **Decoupling**: The sender and receivers are decoupled.
3. **Simplifies Responsibility**: Each handler focuses on a specific task.

---

### **Disadvantages**
1. **Uncertainty**: The request might not be handled if no suitable handler is present.
2. **Performance**: Processing can involve multiple handlers, which might slow things down.

---

### **Use Cases**
1. **Request Filtering**: HTTP request processing pipelines (e.g., filters in servlets).
2. **Authorization Systems**: Checking permissions or roles step by step.
3. **Event Handling**: Sequentially processing events.

Let me know if you'd like to see this applied in a specific scenario!