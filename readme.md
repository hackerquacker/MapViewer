# Map Viewer
This project allows a creator to create a simple road map quickly. This project was created due to a personal city project where
I wanted to map out all the roads however there were no simple solutions available online to do this quickly.

### The roadmap (ha, get it?)

- [x] draw roads, highways and motorways
  - [x] custom widths
  - [x] draw Route Number labels
  - [x] draw Road name labels
  - [x] custom colours
- [x] Map parser and reading map files
  - [x] Define custom road types, colours and label colours.
  - [x] allow user to specify map to load
    - [x] easily switch between maps
- [x] GUI
  - [x] Move map position by dragging mouse
  - [x] Use mouse wheel and keys `=`, and `-` to zoom
  - [x] Show real x,y coordinate of current mouse position
  - [x] Touchbar support
  - [ ] Themes
  - [ ] Graphical map editor
- [ ] Logic
  - [ ] A* Pathfinding

## Dependencies
You will need to add these projects to this project to compile and run.
#### JTouchBar - Maven
```
<dependency>
	<groupId>com.thizzer.jtouchbar</groupId>
	<artifactId>jtouchbar</artifactId>
	<version>1.0.1-SNAPSHOT</version>
</dependency>
```


## How to generate your own map:
This program currently only reads the `map.cities` file to generate maps. To create a custom map, create a new file called this in the root directory of this project.

Because this project was designed to stay personal, the parser is very strict and gives very vague errors. If you encounter an error,
check that your file is 100% correct (The parsing itself is pretty robust if its done correctly. It will 99% be a syntax error).

### Each line of map data ends with a **;** (Semi-colon)

All data is in a `map` object:

```
// This is a comment
/* 
    This is a multiline comment
*/
map your_map_name {
    map data
}
```

### Map Data:

All parameters like `[this]` are optional, but recommended.

#### Defining your roads:
You must define your road types before you can use them. This sets the road color, default width, route label background and foreground.

To do this, use the `def` keyword:

```def <typename> = new Road(red_val, green_val, blue_val, [default width], [label bg], [label fg]);```

The `red_val` `green_val` `blue_val` are all integer values between 0->255 that define the road colour.
<br>The `default width` defines the default width of the road when the `[width]` parameter isn't specified.
<br>`label bg` `label fg` can be one of 4 values:
- BLUE
- GREEN
- WHITE
- BLACK

#### Making your roads.

To make a road, after your road definitions, the syntax is:

`<type>(number_str, road_name_str, [width], (start_x, start_y), (next_x, next_y), [(next_x, next_y)...]);`

(Where `<type>` is your `<typename>`)

You can have as many points for a road as you want.

### Example:

```
map example_map {
    // Define a new road type called motorway
    def motorway = new Road(252, 190, 33, 6, BLUE, WHITE);
    
    // create a new road segment of type motorway
    motorway("M1", "M1 Motorway", 12, (32, -787), (32, 100), (68, 100), (-47, 100));
}
```


## Changelog


>### Alpha 1.3
>
>#### Alpha 1.3.1
> <span>+ Added Javadoc
> <br>+ Simplified Codebase; removed redundant code
>#### Alpha 1.3
> <span>+ Added Touchbar support

>### Alpha 1.2
> <span>+ Added Menu Bar
> <br>+ Added ability to load and switch custom maps

>### Alpha 1.1
><span>+ Added Road names to roads
> <br>+ Fixed road outlines to not draw inside the same road at corners.
> <br>+ Changed map parser to allow for custom road declarations, colours and label colours

>### Alpha 1.0
>**First release.**
><br>+ Added Roads (Predefined motorway, highway, route and local types).
><br>+ Reading and parsing map data from file.
><br>+ Route Numbering.
><br>+ Prints In game coordinate for mouse position.
><br>+ Allows input from mouse and keyboard for zoom and movement