# **DragMania**

DragMania is a multilayer game created for Android devices as the main project for the course TDT4240 Software Architecture at NTNU. 

The game is based on the activity “Red Light, Green Light”. The players are playing on separate devices,
and the screen shows their own car as well as their own and their opponent's score and a policeman. When the policeman is facing
the car, the players will get a time penalty if they are still driving. The first player to cross the finish line wins. 

### **Prerequisities**
The following programs are required: 
- Git
- Android Studio
- Java (8 for Client, 11 for Server)


### **Client**
  1. Clone or download the repo
  2. Open the DragMania/client project in Android Studio 
  
  ***Running on Desktop: (Warning: not optimized. You may experience bugs)***
  Go to "run>edit configurations" and press the + sign to create a new run configuration. Choose Application. Set the main class to com.mygdx.dragmania.desktop.DesktopLauncher and the working directory to {PATH_TO_DRAGMANIA}\DragMania\client\android\assets. Use the classpath of module client.desktop. 
Apply the changes. Now you can run the game in desktop mode. 

  ***Running on Android Emulator:***
  Run the android app by clicking "Run" and choosing "Android" while in Android Studio. Ensure that you have an Android Emulator installed with the correct API-level (Nougat 7.0). If not, install this version under the AVD Manager in Android Studio. If it is the first time you are setting up the emulator, you may have to press "run" twice in order for the application to load on your emulator. 
  
  ***Running on Android Phone:***
  Plug your Android phone to your computer and allow access. Make sure USB debugging is enabled. Next, go to run > select device and select your plugged in device. Now you can run the android configuration to your phone. 

### **Server**
  1. Clone or download the repo
  2. Open the DragMania/server project in your prefered editor
  3. Go to GameServer.java and execute its main function in order to start the server 
