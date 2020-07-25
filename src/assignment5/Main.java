package assignment5;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static String myPackage = Main.class.getPackage().toString().split(" ")[1];

    public static void main(String[] args) {
        // launch(args);
    }
    
    public void start(Stage primaryStage) throws Exception {
    	primaryStage.setTitle("Critters");
    	
    	TabPane tb = new TabPane();
    	//Set tabs
    	Tab ctrl = new Tab();
    	ctrl.setText("Controls");
    	
    	Tab anime = new Tab();
    	anime.setText("Animation");
    	
    	Tab stats = new Tab();
    	stats.setText("Statistics");
    	//create gridpanes for each tab
    	GridPane ctPane = new GridPane();
    	GridPane anPane = new GridPane();
    	GridPane stPane = new GridPane();
    	
    	//control tab
    	//make critter function
    	Label makeCrit = new Label("Make Critters");
    	ctPane.addRow(0, makeCrit);
    	
    	ArrayList<String> critterChoices = critterClasses();
    	
    	ChoiceBox<String> make = new ChoiceBox<String>();
    	make.getItems().addAll(critterChoices);
    	GridPane.setConstraints(make,0,1);
    	
    	TextField makeTF = new TextField("Enter Amount");
    	GridPane.setConstraints(makeTF, 1, 1);
    	
    	Button create = new Button("Create");
    	GridPane.setConstraints(create, 2, 1);
    	
    	create.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			String amount = makeTF.getText();
    			try {
    				int num;
    				if(amount.equals("Enter Amount")) {
    					num = 0;
    				} else {
    					num = Integer.parseInt(amount);
    				}
    				for(int i = 0; i < num; i++) {
    					Critter.createCritter(make.getValue().toString());
    				}
    				//need to write display function and runStats function
    			} catch (NumberFormatException | InvalidCritterException e) {
    				System.out.println("Error Creating Critters");
    			}
    			makeTF.clear();
    		}
    	});
    	
    	//set seed function
    	Label setSeed = new Label("Set Seed");
    	ctPane.addRow(2, setSeed);
    	
    	TextField seedTF = new TextField("Seed Number");
    	GridPane.setConstraints(seedTF, 0, 3);
    	
    	Button seed = new Button("Set");
    	GridPane.setConstraints(seed, 1, 3);
    	
    	seed.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			String numb = seedTF.getText();
    			try {
    				long num;
    				if(numb.equals("Seed Number")) {
    					num = 1;
    				} else {
    					num = Long.parseLong(numb);
    				}
    				Critter.setSeed(num);
    			} catch (NumberFormatException e) {
    				System.out.println("Error in changing Seed Number");
    			}
    			seedTF.clear();
    		}
    	});
    	
    	//step function
    	Label critStep = new Label("Critter Time Step");
    	ctPane.addRow(4, critStep);
    	
    	TextField stepTF = new TextField("Number of Steps");
    	GridPane.setConstraints(stepTF, 0, 5);
    	
    	Button timeStep = new Button("Step");
    	GridPane.setConstraints(timeStep, 1, 5);
    	
    	timeStep.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			String steps = stepTF.getText();
    			try {
    				int num;
    				if(steps.equals("Number of Steps")) {
    					num = 0;
    				} else {
    					num = Integer.parseInt(steps);
    				}
    				for(int i = 0; i < num; i++) {
    					Critter.worldTimeStep();
    				}
    				//need to write display function and runStats function
    			} catch (NumberFormatException e) {
    				System.out.println("Error in Time Step");
    			}
    			stepTF.clear();
    		}
    	});
    	
    	//add controls to control pane
    	ctPane.getChildren().addAll(make, makeTF, create, seedTF, seed, stepTF, timeStep);
    	
    	//add all to splitpane which will be scene for stage
    	SplitPane sp = new SplitPane();
    }
    
    private ArrayList<String> critterClasses() {
    	String path = myPackage.replaceAll("\\.", File.separator);
    	ArrayList<String> critters = new ArrayList<String>();

    	// Finding class path
    	String classPathEntry = System.getProperty("user.dir");

    	String name;

		// go through all files in the package
        File base = new File(classPathEntry + File.separatorChar
        		+ "src" + File.separatorChar +path);

        for (File file : base.listFiles()) {
            name = file.getName();
            // Find classes
            name = name.substring(0, name.lastIndexOf('.'));  // remove file extension

            // Check for instance
            Object critInstance = null;

            try {
            Class<?> critClass = Class.forName(myPackage + "." + name);
            critInstance = critClass.getConstructor().newInstance();
            }
            catch (InstantiationException | NoSuchMethodException | ClassNotFoundException e) {
            	// These are alright.
            }
        	catch (Exception e) {}
            if(Critter.class.isInstance(critInstance)) {
            	critters.add(name);
            }
        }

        return critters;
    }
}


