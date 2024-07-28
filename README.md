Sure, here's the README file with all the requested details, links, and descriptions:

---

# Smart Office Management System

## Problem Description

The Smart Office Management System is designed to manage conference room bookings and track room occupancy in an office environment. It allows users to book and cancel conference rooms, add occupants, and view room usage statistics. The system also includes automated control of air conditioning and lighting based on room occupancy.

## Features

### Admin Functions
- Configure Rooms
- Set Room Capacity
- Add Room
- List Rooms

### User Functions
- Book Room
- Cancel Booking
- Add Occupants
- View Room Usage Statistics

## Prerequisites

To run the Smart Office Management System, you will need the following software installed:

### MySQL Workbench

- [MySQL Workbench 8.0 CE (Community Edition) for Windows](https://dev.mysql.com/downloads/workbench/)
- [MySQL Workbench 8.0 CE (Community Edition) for macOS](https://dev.mysql.com/downloads/workbench/)

### MySQL Server

- [MySQL Server for Windows](https://dev.mysql.com/downloads/mysql/)
- [MySQL Server for macOS](https://dev.mysql.com/downloads/mysql/)

### Apache NetBeans IDE

- [Apache NetBeans IDE 20](https://netbeans.apache.org/download/index.html)

### Java Development Kit (JDK)

- [JDK 19](https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html)
- [JDK 22](https://www.oracle.com/java/technologies/javase/jdk22-archive-downloads.html)

### JDBC Driver

- [MySQL Connector/J 8.0.26](https://dev.mysql.com/downloads/connector/j/)

## Installation and Setup

### 1. Install MySQL Server

Download and install the MySQL Server from the links provided above for your respective operating system.

### 2. Install MySQL Workbench

Download and install MySQL Workbench 8.0 CE from the links provided above for your respective operating system.

### 3. Install Apache NetBeans IDE

Download and install Apache NetBeans IDE 20 from the provided link.

### 4. Install JDK

Download and install JDK 19 or JDK 22 from the provided links.

### 5. Set Up the Database

1. Open MySQL Workbench and connect to your MySQL Server.
2. Create a new database named `SmartOffice`.
3. Use the following SQL script to create the required tables:

```sql
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL
);

CREATE TABLE rooms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    capacity INT NOT NULL,
    isOccupied BOOLEAN DEFAULT FALSE
);

CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    roomId INT,
    username VARCHAR(50),
    startTime DATETIME,
    endTime DATETIME,
    FOREIGN KEY (roomId) REFERENCES rooms(id),
    FOREIGN KEY (username) REFERENCES users(username)
);

CREATE TABLE occupants (
    id INT AUTO_INCREMENT PRIMARY KEY,
    roomId INT,
    currentOccupants INT DEFAULT 0,
    FOREIGN KEY (roomId) REFERENCES rooms(id)
);
```

### 6. Configure Database Connection

Ensure that the `DatabaseConnection` class in your project is configured with the correct database connection details.

### 7. Add MySQL Connector/J to Your Project

Download the MySQL Connector/J 8.0.26 and add it to your project's classpath in Apache NetBeans IDE.

## Usage

1. Open the project in Apache NetBeans IDE.
2. Run the `SmartOfficeManagement` main class.
3. Use the menu options to perform various functions as an admin or user.

## Main Menu Options

1. **Login:** Existing users can log in with their username and password.
2. **Register:** New users can register by providing a username, password, and email.

### Admin Menu

1. **Configure Rooms:** Enter the number of meeting rooms to configure.
2. **Set Room Capacity:** Set the capacity for a specific room by providing the Room ID and capacity.
3. **Add Room:** Add a new room to the system.
4. **List Rooms:** List all the configured rooms in the system.

### User Menu

1. **Book Room:** Book a room by providing the Room ID, start time, and end time.
2. **Cancel Booking:** Cancel an existing booking by providing the Booking ID.
3. **Add Occupants:** Add occupants to a specific room by providing the Room ID and number of occupants.
4. **View Room Usage Statistics:** View usage statistics for all rooms.

---

