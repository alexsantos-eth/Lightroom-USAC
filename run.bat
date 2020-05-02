cd .\Source
javac -d ..\Classes .\Controllers\*.java .\Handlers\*.java .\Menus\*.java .\Structure\*.java .\Utils\*.java .\Views\*.java LightRoom.java
cd ..\Classes
java Source.LightRoom
cd ..\