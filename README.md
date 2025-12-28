# 🎵 Advanced Mini Music Player (Java Swing)

An **Advanced Mini Music Player** built using **Java Swing**, demonstrating modern UI design, object-oriented programming, event-driven architecture, and real-time playback simulation.

This project simulates a desktop music player with playlist management, search functionality, playback modes, and usage statistics.

---

## 📌 Features

### 🎧 Playback Controls
- Play / Pause
- Stop
- Next Track
- Seek simulation with progress bar

### 🔁 Playback Modes
- Repeat One
- Repeat All
- No Repeat
- Shuffle

### 📚 Music Library
- Preloaded demo songs
- Add offline audio files (`mp3`, `wav`, `m4a`, etc.)
- Search songs by title, artist, album, or genre

### 📋 Queue Management
- Add/remove songs to/from queue
- Build queue using keywords
- Shuffle queue
- Clear queue

### ⏱ Playback Simulation
- Timer-based playback progress
- Auto-next song when duration ends
- Real-time progress bar & time display

### 📊 Statistics
- Track play count for each song
- Display statistics in a dialog window

### 🎨 Modern UI
- Custom list renderer
- Alternating row colors
- Responsive layouts
- Custom application icon

---

## 🛠 Technologies Used

- **Java**
- **Java Swing**
- **AWT**
- **Collections Framework**
- **Event Handling**
- **Timer API**

---

## 🧱 Project Structure

AdvancedMusicPlayer.java
│
├── Song (Model)
│ ├── Title
│ ├── Artist
│ ├── Album
│ ├── Genre
│ └── Duration
│
├── Player (Controller)
│ ├── Library Management
│ ├── Queue Management
│ ├── Playback Control
│ ├── Playback Modes
│ └── Statistics Tracking
│
└── MainFrame (View)
├── Swing UI Components
├── Event Listeners
├── Custom List Renderer
└── File Chooser Integration

yaml
Copy code

---

## 🧠 Core Concepts Demonstrated

- Object-Oriented Programming (Encapsulation, Abstraction)
- MVC-inspired architecture
- Event-driven programming
- Timer-based real-time simulation
- Java Swing UI design
- Collection handling (`ArrayList`, `LinkedList`, `HashMap`)
- File handling with `JFileChooser`

---

## ▶️ How to Run

1. Ensure **Java JDK 8 or later** is installed
2. Compile the program:
   ```bash
   javac AdvancedMusicPlayer.java
Run the application:

bash
Copy code
java AdvancedMusicPlayer
📂 Supported Audio Formats
.mp3

.wav

.aiff

.aif

.au

.m4a

(Playback is simulated; real audio playback is not implemented.)

🚀 Future Improvements
Real audio playback using JavaFX MediaPlayer

Playlist saving & loading

Database integration

Dark mode UI

Online streaming support

👨‍💻 Author
Nahin Rahman
Freelancer | Java Developer | UI Enthusiast








