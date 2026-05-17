# Multi-threaded Socket Chat Application

A simple yet robust multi-threaded socket application in Java that demonstrates client-server communication using TCP sockets with concurrent client handling.

## Overview

This project implements a basic chat server that can handle multiple concurrent client connections using Java threads. Each client connection is managed independently in a separate thread, allowing the server to serve multiple clients simultaneously.

## Features

- **Multi-threaded Server**: Handles multiple client connections concurrently
- **Simple Client Interface**: Connect to the server and send/receive messages
- **Graceful Connection Handling**: Proper resource management and clean disconnections
- **Timestamped Logging**: All events are logged with timestamps for better debugging
- **Error Handling**: Robust exception handling for network and I/O operations

## Folder Structure

```
socketApp/
├── src/
│   ├── App.java              # Application launcher with usage instructions
│   ├── Server.java           # Multi-threaded server implementation
│   ├── Client.java           # Client application
│   └── ClientHandler.java    # Handler for individual client connections
├── bin/                       # Compiled output (auto-generated)
├── lib/                       # External dependencies (if any)
└── README.md                  # This file
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Java compiler (javac)

### Compilation

Compile all Java files:

```bash
javac -d bin src/*.java
```

### Running the Application

**Step 1: Start the Server**

In a terminal, navigate to the project directory and run:

```bash
java -cp bin Server
```

You should see:
```
[YYYY-MM-DD HH:MM:SS] Server started and listening on port 1234
[YYYY-MM-DD HH:MM:SS] Waiting for client connections...
```

**Step 2: Start Client(s)**

In another terminal, run:

```bash
java -cp bin Client
```

You will see:
```
[HH:MM:SS] Connected to server at localhost:1234
[Server] Welcome to the Chat Server!
...
```

**Step 3: Send Messages**

Type messages and press Enter. They will be echoed back by the server.

### Available Commands

- `help` - Display available commands
- `exit` - Disconnect from server
- Any other text will be echoed back by the server

## Architecture

### Server Architecture

```
ServerSocket (Port 1234)
    ↓
Accepts Client Connection
    ↓
Creates New Thread with ClientHandler
    ↓
ClientHandler Manages Client Communication
```

### Communication Flow

1. **Client** connects to **Server** on port 1234
2. **Server** accepts connection and creates a **ClientHandler** thread
3. **ClientHandler** sends a welcome message
4. **Client** can send commands (help, exit) or any message
5. **Server** processes requests and sends responses
6. **Client** displays received messages
7. Connection closes when client sends "exit"

## Code Quality Improvements

This version includes several improvements over the initial implementation:

- ✅ Comprehensive JavaDoc comments for all classes and methods
- ✅ Proper exception handling and logging
- ✅ Constants for configuration (PORT, commands, etc.)
- ✅ Consistent code formatting and naming conventions
- ✅ Timestamp-based logging for better debugging
- ✅ Resource management using try-with-resources
- ✅ Separated concerns into distinct classes
- ✅ Removed unused database-related code (MySQL dependencies)

## Example Usage

### Terminal 1 (Server):
```
$ java -cp bin Server
[2026-05-17 10:30:15] Server started and listening on port 1234
[2026-05-17 10:30:15] Waiting for client connections...
[2026-05-17 10:30:22] Client connected from: 127.0.0.1
[2026-05-17 10:30:22] Handler started for client: 127.0.0.1
[2026-05-17 10:30:28] Received from 127.0.0.1: hello
[2026-05-17 10:30:32] Received from 127.0.0.1: exit
[2026-05-17 10:30:32] Client 127.0.0.1 disconnected.
```

### Terminal 2 (Client):
```
$ java -cp bin Client
[10:30:22] Connected to server at localhost:1234
[Server] ========================================
[Server] Welcome to the Chat Server!
[Server] ========================================
[Server] Commands:
[Server]   help - Show available commands
[Server]   exit - Disconnect from server
[Server] ========================================
hello
[Server] Echo: hello
exit
[Server] You have been disconnected. Goodbye!
[10:30:32] Disconnected from server.
```

## Future Enhancements

- Add message persistence
- Implement user authentication
- Add message broadcasting to all clients
- Create GUI for client application
- Add database integration for persistent storage
- Implement SSL/TLS for secure communication

## License

This project is open source and available under the MIT License.

## Contributing

Feel free to fork this project and submit pull requests for any improvements.
