# **2D Floor Planner Project Documentation**

## **1. Overview**

The 2D Floor Planner is a desktop-based application that allows users to create and manage room layouts, save their designs, and load them for later use. The application features components for drawing, interacting with rooms, and managing saved layouts. 

### Key Features:
- Add, remove, and modify room layouts on a 2D grid.
- Save floor plans to a file for future retrieval.
- User-friendly interface with customizable elements.
- A grid-based layout for accurate positioning of rooms.

---

## **2. Project Structure**

The project is organized into several packages and files that manage different functionalities:

### Folder Structure Overview:

```
2DFloorPlanner
│
├── assets                   # Directory to store additional assets
├── saves                    # Directory to store save files
│   └── saveFileTest.txt      # Example saved file
└── src/com/example           # Main source directory
    ├── canvas                # Canvas-related classes (manages grid and room drawing)
    │   └── Canvas.java       
    ├── frames                # Window frame management
    │   └── OuterFrame.java   
    ├── main                  # Main application entry point
    │   └── Main.java         
    ├── menubar               # Menu bar implementation (if any)
    ├── models                # Data models for rooms and other objects
    │   └── Room.java         
    ├── panels                # UI panels for commands and other inputs
    │   └── CommandPanel.java 
    └── services              # Services for file I/O and constants
        ├── Constants.java    # Contains constant values such as file paths
        ├── RetrieveFile.java # Loads room data from files
        └── SaveFile.java     # Saves room data to files
```

---

## **3. Detailed Component Descriptions**

### 3.1 **Canvas.java (canvas package)**

- **Purpose**: Manages the drawing and interaction with the 2D grid where rooms are placed. This class listens for user input, processes clicks to place rooms, and handles the rendering of rooms on the grid.
- **Key Responsibilities**:
  - Draws the grid based on the specified `gridSize`.
  - Handles mouse events to snap rooms to the grid.
  - Dynamically renders rooms and updates the canvas upon user interactions.
  - Loads rooms from a saved file using the `RetrieveFile` class.

- **Key Methods**:
  - `paintComponent(Graphics g)`: Draws the grid and any rooms on the canvas.
  - `snapToGrid(int value)`: Snaps room positions to the nearest grid point.
  - `setSelectedObject(T fixture)`: Allows users to select a type of room (e.g., bedroom, kitchen) for placement.
  - `getRoomList()`: Returns the current list of rooms on the canvas.

---

### 3.2 **OuterFrame.java (frames package)**

- **Purpose**: Manages the main application window frame.
- **Key Responsibilities**:
  - Creates the window frame for the 2D Floor Planner application.
  - Sets up the layout and integrates UI components like menus and panels.

---

### 3.3 **Main.java (main package)**

- **Purpose**: Entry point for the application.
- **Key Responsibilities**:
  - Launches the application and initializes key components, such as the `OuterFrame` and the `Canvas`.
  - Ensures that all elements are correctly displayed when the application starts.

---

### 3.4 **CommandPanel.java (panels package)**

- **Purpose**: Manages the user input panel for issuing commands related to room management (e.g., adding or removing rooms).
- **Key Responsibilities**:
  - Provides buttons and input fields for interacting with the floor plan.
  - Connects user commands with the canvas, allowing them to modify the room layout.

---

### 3.5 **Room.java (models package)**

- **Purpose**: Represents a room with properties such as dimensions and position.
- **Key Properties**:
  - `int x, y`: Coordinates of the room on the grid.
  - `int width, height`: Dimensions of the room.
  - `Color color`: The color representing the room (e.g., bedrooms may be red, bathrooms green).

---

### 3.6 **Constants.java (services package)**

- **Purpose**: Stores shared constant values used across the application.
- **Key Constants**:
  - `ROOM_FILE_PATH`: The path where room layouts are saved and loaded.
  - `GRID_SIZE`: Default size of the grid for snapping rooms.

---

### 3.7 **RetrieveFile.java (services package)**

- **Purpose**: Manages the loading of saved room layouts from a file.
- **Key Responsibilities**:
  - Reads room data from a text file (e.g., `saveFileTest.txt`).
  - Returns an array of `Room` objects to be displayed on the canvas.
  
- **Key Methods**:
  - `getFile()`: Returns a list of `Room` objects loaded from the file.

---

### 3.8 **SaveFile.java (services package)**

- **Purpose**: Manages the saving of room layouts to a file.
- **Key Responsibilities**:
  - Saves the current list of rooms to a specified file (e.g., `saveFileTest.txt`).
  - Converts the `Room` objects to a file format for persistence.

---

## **4. File Operations**

### 4.1 **Saving Room Layouts**

- The `SaveFile.java` class is responsible for writing the current list of `Room` objects to a text file.
- The saved file can later be retrieved using `RetrieveFile.java`.

### 4.2 **Loading Room Layouts**

- The `RetrieveFile.java` class reads room data from a file and reconstructs the room objects for display on the canvas.

---

## **5. Constants Management**

- The `Constants.java` file stores shared configuration values, such as file paths and grid sizes. This helps in centralizing configurations and ensuring that any changes are reflected across the application without modifying each class individually.

---

## **6. Getting Started**

### 6.1 **Prerequisites**

- Java Development Kit (JDK) installed.
- A text editor or IDE (e.g., IntelliJ, Eclipse) for development.

### 6.2 **Running the Application**

1. Open the project in your preferred IDE.
2. Navigate to `Main.java` and run the application.
3. The main window will open, displaying a grid where users can place rooms.

### 6.3 **Usage**

- **Place a Room**: Select a room type (e.g., bedroom, bathroom) and click on the grid to place it.
- **Save Layout**: Use the `SaveFile` functionality to save your current floor plan.
- **Load Layout**: Use the `RetrieveFile` functionality to load a previously saved layout.

---

## **7. Future Improvements**

- **3D Rendering**: Upgrade the canvas to support 3D room modeling.
- **Room Resizing**: Allow users to resize rooms dynamically.
- **Drag and Drop**: Implement drag-and-drop functionality for rooms.
- **Undo/Redo**: Add undo/redo functionality for better user experience.

---

## **8. Troubleshooting**

- **Issue: Application does not start**:
  - Ensure you have the correct JDK installed and the project is set up properly.
  
- **Issue: Saved rooms are not loading correctly**:
  - Verify the file path in `Constants.java` is correct.

---

## **9. Acknowledgements**

- Java Swing for UI components.
- Open-source libraries for file handling (if any are used).

## **10. Authors**

- Pratyush Nair
- Saket Goyal
- Riddhi Chatterjee
- Aryan Dalmia