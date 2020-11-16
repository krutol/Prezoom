# Test Plan

## Model
* Write JUnit5 tests for each model
* CameraInfo

      Test constructor
      Test clone
      Test getter
      Test setter
      
* GAttributes
    
      Test constructor
      Test clone
      Test getter
      Test setter
      
* GObject

      Test constructor
      Test get attributes
      
* GLine
    
      Test constructor
      Test get attributes
      Test in shape
      Test draw
    
* GOval
    
      Test constructor
      Test get attributes
      Test in shape
      Test draw
    
* GRectangle
    
      Test constructor
      Test get attributes
      Test in shape
      Test draw
    
    
## Controller
* Write JUnit5 tests for each controller
* CameraManager
      
      Test constructor
      Test move camera
      Test get camera information
      Test insert state
      Test delete state
      Test update camera information

* GAttributeManager

      Test constructor
      Test get attributes
      Test insert state
      Test delete state
      Test update attributes

* GObjectManager

      Test add objects
      Test delete objects
      Test draw objects
      Test find the selected object
      Test insert state
      Test delete state
      Test update objects
      

* StateManager
  
      Test insert state
      Test delete state
      Test switch state
      Test get current state
      Test get the total number of states