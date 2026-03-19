
# Distributed System with Java Sockets

This project demonstrates a **distributed system** built using **Java sockets**, designed with a **client-server architecture**. The server communicates with clients over **port 4000**, and integrates with the **Northwind database** to handle distributed queries and operations.

---

## 🚀 Getting Started

### Prerequisites
- **Java 25** (or compatible JDK)
- **VS Code** or any Java IDE
- **Northwind Database** (SQL Server or compatible setup)
- Properly configured **JDBC driver** for database connectivity

---

## 📂 Folder Structure

```
project-root/
│
├── src/                # Source code for client and server
│   ├── client/         # Client-side implementation
│   ├── server/         # Server-side implementation
│  
│
├── lib/                # External libraries (e.g., JDBC drivers)
├── bin/                # Compiled output files
└── README.md           # Project documentation
```

---

## ⚙️ How It Works

- **Server**  
  - Listens on **port 4000**.  
  - Handles multiple client connections concurrently.  
  - Executes queries against the **Northwind database**.  
  - Sends results back to clients in a structured format.

- **Client**  
  - Connects to the server via **socket**.  
  - Sends requests (e.g., product queries, customer info).  
  - Receives and displays responses from the server.  

This architecture simulates a **distributed environment** where multiple clients interact with a centralized server, enabling scalability and resource sharing.

---

## 🔌 Running the Project

1. **Compile the project**  
   ```bash
   javac -d bin src/**/*.java
   ```

2. **Start the server**  
   ```bash
   java -cp bin server.ServerMain
   ```

3. **Run a client instance**  
   ```bash
   java -cp bin client.ClientMain
   ```

4. **Test multiple clients**  
   - Open several terminals and run `ClientMain`.  
   - Each client connects to the server on **port 4000**.  

---

## 🗄️ Database Integration

- Ensure the **Northwind database** is installed and accessible.  
- Update the **JDBC connection string** in `server/DatabaseConfig.java`:  

```java
String url = "jdbc:sqlserver://localhost:1433;databaseName=Northwind";
String user = "your_username";
String password = "your_password";
```

---

## 📖 Example Workflow

1. Client requests:  
   ```
   GET PRODUCTS
   ```

2. Server queries Northwind:  
   ```sql
   SELECT ProductName, UnitPrice FROM Products;
   ```

3. Server responds with structured data:  
   ```
   Product: Chai | Price: 18.00
   Product: Chang | Price: 19.00
   ...
   ```

## 🌐 Distributed System Features

- Multi-client support with concurrent socket handling.  
- Centralized database access for distributed queries.  
- Scalable design for future expansion (e.g., load balancing, replication).  

