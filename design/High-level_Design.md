# High-level Design

## Table of Contents
  - [Packages UML](#packages-uml)
  - [Packages](#packages)
  - [Third Party Packages](#third-party-packages)
  - [Classes UML](#classes-uml)
  - [Classes Dependency based on Packages](#classes-dependency-based-on-packages)
  - [Classes Description](#classes-description)
  - [Assignments](#assignments)

## Packages UML
![Package Structure](res/Package_structure.png)

## Packages
+ Model
    - Contains model classes for graphical objects, object attributes, camera information, etc.
+ View
    - Contains view classes that implement Swing GUI. 
+ Controller
    - Contains manager classes that manage interactions between the model and the view, affecting data such as states, attributes, camera, etc.
+ Table
    - Contains inspector table classes and color renderer.

## Third Party Packages
+ Trident
    - Trident provides powerful and flexible animation APIs that scale from simple, single-property cases to complex scenarios that involve multiple animations.
    - Trident is part of [Radiance](https://github.com/kirill-grouchnikov/radiance).
    - Find Trident documentation [here](https://github.com/kirill-grouchnikov/radiance/blob/master/docs/trident/trident.md).
    - Our project uses Trident to build [timelines](https://github.com/kirill-grouchnikov/radiance/blob/master/docs/trident/TimelineLifecycle.md) and [interpolations](https://github.com/kirill-grouchnikov/radiance/blob/master/docs/trident/TimelineInterpolatingFields.md) for the camera and objects.
    - Trident examples:
      - | [Example 1](https://github.com/kirill-grouchnikov/radiance/blob/master/docs/trident/SimpleSwingExample.md) | [Example 2](https://github.com/kirill-grouchnikov/radiance/blob/master/docs/trident/ParallelSwingTimelines.md) |
        | :---------: | :---------: |
        | ![example 1](res/GIF_Trident_example_1.gif) | ![example 2](res/GIF_Trident_example_2.gif) |
          
     


## Classes UML
* Model Packages
![Model Package](res/model_package.png)
* View Packages
![view Package](res/view_package.png)
* Controller Packages
![Controller Package](res/controller_package.png)

## Classes Dependency based on Packages
![Classes Dependency based on Packages](res/package_class_dependency.png)

## Classes Description
+ Model
  + | Class Name | Description |
    | :--------- | :---------- |
    | GObject | The base class for all graphical objects  | 
    | GAttributes | The base attribute class that holds all the attribute an object has. |
    | CameraInfo | The base class for camera info. |
    | GRectangle, GOval, GLine, GCircle, GImage, GText| The classes that implement corresponding graphics. |
    | AttributeMapI | The interface for getting the getter and setter map. |
    | CameraInfoI | The interface of the camera information. |
    | GAttributesI | The interface of the Graphical Attributes. |
    | MethodFactory | To generate maps that contain all non null filed names, values, getter methods, or setter method of a class. |



+ View
  + | Class Name | Description |
    | :--------- | :---------- |
    | MainWindow | This is the main GUI window that holds all the child GUI panels. |
    | CenterCanvas | The center canvas where you can edit the presentation, move the camera, etc. |
    | MenuBar | Class used to display the application's menu bar |
    | StatePanel | The panel that shows all the state sequentially |
    | StatusBar | The bar at the bottom to show some info |
    | ToolPanel | The panel that contains different function buttons |
    | PresentationWindow | The window to present the presentation. |
    | AttributePanel | Construct the inspector panel. |
    | CameraPanel | Construct the camera inspector panel. |

+ Table (inner package in View)
  + | Class Name | Description |
    | :--------- | :---------- |
    | ColorEditor | To set up the editor to create the color chooser dialog. |
    | ColorRenderer | Rendering the color for the object. |
    | InspectorTable | Extending from JTable to model row editor model per row. |
    | InspectorTableModel | Custom table model with row editor model. |

+ Controller
  + | Class Name | Description |
    | :--------- | :---------- |
    | StateManager | This is the class that controls all the state changes in Prezoom. |
    | GAttributeManager | This class is the manager to manage the state related functions for the attributes of graphical objects. Each GObject has a GAttributeManager. |
    | CameraManager | This class is the manager to manage all the camera related functions, including movement, info, states. |
    | GOjectManager | The manager that manages all the graphical objects on the canvas. |
    | InterpolationFactory | The class to build the interpolation for objects. |
    | PresentManager | The class to manage the presentation. |
    | SaveLoadManager | The class to mange saving and loading |


## Assignments

The project was finished by the each member of group Charlie.

+ Abhishek Sharma
  + Finish the state diagram and wire-frame to design the first version software.
  + Inputs on designing the present GUI in the View package with Zhijie Lan.
  + Design the first version inspector panel.
  + Updated the final version for shape inspector panel to display shape attributes in a jtable format.
  + Design the camera inspector panel to control camera attributes on the canvas to enhance the current view.
+ P.Ajanthan
  + Finish the sequence diagram to design the first version software.
  + Design the first version presentation mode.
  + Help to finish the table package.
+ Tianxing Li
  + Finish the user stories and lexcion.
  + Draw the package diagram.
  + Attend to design the test part.
+ Zhijie Lan
  + Team leader and design the whole structure of the project.
  + Draw the use case diagram and corresponding the user stories.
  + Attend to desgin the test part.
  + Combine the MVC structure for the whole project.
  + The main designer for the Control package.
+ Ziyang Li
  + The main diagram design of the project.
  + Draw the Class diagram and design the high level of the project.
  + Add the each object functions to model package.

