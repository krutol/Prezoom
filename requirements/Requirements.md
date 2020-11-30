# Requirements Document

## Table of Contents
    
 - [Domain Model](#domain-model)
 - [Lexicon](#lexicon)
 - [Requirements](#requirements)

## Domain Model
![Domain model](res/DomainDiagram.png)


## Lexicon

- controller : the package which used to control the camera, state, attribute and mode.
- model : the package which used to construct camera, attributes and graphical objects.
- view : the package which used to set up the GUI of the project.
- CameraManager : the class to manage all the camera related functions, including movement, info, states.
- AttributeManager : the class to manage the state related functions for the attributes of graphical objects.
- StateManager : the class that controls all the state changes in Prezoom.
- ModeManager : the class that controls mode change.
- GraphicObjects : the base class for all graphical objects.
- Oval : the Oval graph.
- Rectangle : the Rectangle graph.
- Circle : the Circle graph.
- GraphicAttributes : the base attribute class that holds all the attribute an object has.
- CameroInfo : the base class for camera information.
- MainWindow : the main GUI window to hold all the child GUI windows.
- MenuBar : display the application's menu bar.
- StatePanel : show all the state sequentially.
- ClipBoardPanel : contain different function buttons.

## Requirements

+ ### __Iteration 1__(GUI, Canvas, Camera, Objects, Attributes, States):
  + What we have done:
    + Main GUI Frame.
    + Main canvas.
    + Camera.
    + (Move) Can move the main camera.
    + (Zoom) Can zoom in or out the main camera.
    + (Edit) In edit mode the presentation can be changed.
    + (Ob-Add) To add graphical objects to a presentation.
    + (Attributes) Graphical objects have attributes. The value of each attribute may depend on the state.
    + (Attr-modify) To change the values of the various attributes of graphical objects.
    + (State-Add) To add a state at any point.
    + (State-Delete) To delete any state.
    + (State-View) To view the presentation at any state.
  + What we have started:
    + Save and load files.
    + The edit mode change to presentation mode.
    + InspectorPanel design.
    
<br/>

+ ### __Iteration 2__(Presentation):
  + What we have done during this section:
    + (Trans-Trigger) To change the trigger condition for moving from one state to the next.
    + Animation
    + (Pres-start) To move from edit mode to presentation mode either starting at the beginning or starting at the current state.
    + (Pres-States) To show each state of the presentation in succession.
    + (Pres-trigger) The presentation begins moving to the next state when the appropriate trigger event happens.
    + (Pres-end) To exit presentation mode and entering edit mode.
    + Save and load files.
    + The edit mode change to presentation mode.
    + InspectorPanel design.
    + CameraInspectorPanel design.
    + Preview for each state (or each slide).
