# Requirements Document

## Table of Contents
    
 - [Domain Model](#domain-model)
 - [Lexicon](#lexicon)
 - [Requirements](#requirements)

## Domain Model
![Domain model](res/Domain_Diagram.png)


## Lexicon

- User: The user of the presentation software.
- PresentMode: In this mode, system begins with state 0. With the effect of trigger events, system can change to other states.
- PresentationSoftware: The entity of the project.
- Graph: Each graph has its own attributes which contains exact position (x, y). User could add, delete, move graphs.
- GraphObjects: Each graph has different type, such as Text area, Ovals, Straight Line, Group.
- TextArea: The graph object in which user can type text.
- Ovals: The graph object with the shape of ovals.
- StraightLine: A straight line graph object.
- Groups: A combination of several graph objects.
- EditMode: The mode user can modify the timeline, transition, and states in various way. 
- Camera: A rectangular area which user can see with presentation mode
- Clipboard: The interface has several operations
- ClipboardOperations: User can use different operations with the software, such as cut, copy, paste, Trigger control, Undo, Redo. 
- Cut: Cut operation.
- Copy: Copy operation.
- Paste: Paste operation.
- TriggerControl: The operation which used to set the trigger.
- Undo: Cancel or reverse the results of a previous action.
- Redo: Operate the previous operation again.


## Requirements

+ ### __Iteration 1__(GUI, Canvas, Camera, Objects, Attibutes):
  + Main GUI Frame.
  + Main canvas.
  + Camera.
  + (Move) Can move the main camera.
  + (Zoom) Can zoom in or out the main camera.
  + (Edit) In edit mode the presentation can be changed.
  + (Ob-Add) To add graphical objects to a presentation.
  + (Ob-Delete) To delete graphical objects from a presentation.
  + (Attributes) Graphical objects have attributes. The valueof each attribute maydepend on the state.
  + (Attr-modify) To change the values of the various attributes of graphical objects.
  + (Save) To save a presentation to file.
  + (Load) To sead a presentation from a file.  


+ ### __Iteration 2__(States, Presentation):
  + (State-Add) To add a state at any point.
  + (State-Delete) To delete any state.
  + (State-View) To view the presentationat any state.
  + (Trans-Trigger) Too change the triggercondition for movingfrom one state to the next.
  + (Trans-Duration) To change the amount of time it takes to move from one state to the next.
  + Animation
  + (Pres-start) To move from edit mode to presentation mode either starting at the beginning or starting at the current state.
  + (Pres-States) To show each state of the presentation in succession.
  + (Pres-trigger) The presentation begins moving to the next state when the appropriate trigger event happens.
  + (Pres-trans) Between showing one state and the next, a transition will be shown.
  + (Pres-interpolation-objects) The user should see an interpolation of attribute values.
  + (Pres-interpolation-camera) Interpolation also applies to the camera attributes.
  + (Pres-skip) To move quickly forward or backward without tansition.
  + (Pres-end) To exit presentation mode and entering edit mode.