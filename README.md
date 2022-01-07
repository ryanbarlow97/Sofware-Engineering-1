# The Welsh Dragon

Using Eclipse:

Add the folder "Group7" into your eclipse workspace, and then import the project into your eclipse using the built in tools : "Import Projects from File System or Archive".

After the project imports, you need to setup the built path/build tools. We can do this by right clicking on the imported project and selecting "Build Path -> Configure Build Path".

You will be greeted with a "Properties for Group7" popup, inside the TabPane "Source" add a folder. We are adding the "resources" folder, now move to the "Libraries" TabPane and import the external jar "json-20210307.jar" and all essential javaFX libraries you should have installed on your PC already.

We now "apply and close"

Before trying to launch the game, press the drop down arrow next to the "play" button and click "Run Configurations" -> Now Select the "Arguments" TabPane, and enter the following into your "VM Arguements" box, 

You will need to enter the following for the program to compile:

"--module-path "YOUR\DIRECTORY\javafx-sdk-17.0.0.1\lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.media"

Now you will be able to run the game!
