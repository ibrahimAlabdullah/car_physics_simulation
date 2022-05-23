package gui;

import static gui.Gui.stage;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import entities.Camera;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import renderEngine.DisplayManager;

public class Gui extends Application
{
	public static TextField massText;
	public static TextField engineText;
	public static TextField dinsityAirText;
	public static TextField coefficientFrictionText;

	
	public static Label outLabel;
	
	public static Button stop;
	public static Button start;
	public static boolean isStop = false;

	
public static Stage stage = new Stage();
	@Override
	public void start(Stage stage1) throws Exception 
	{
		
		
//-------------------- Input ---------------------------------------		
	  VBox pane = new VBox();
	 
	  
	  Label input = new Label("----------< Input >-----------");
	  input.setStyle("-fx-color:black;"+"-fx-font: bold 25px adobe;"+"-fx-pref-width :350px;"+"-fx-pref-height:50px;");
	 

	  
	  
	  
	  VBox vBoxInput = new VBox(); 
	  HBox hBoxInput1 = new HBox();
	  HBox hBoxInput2 = new HBox();
	  HBox hBoxInput3 = new HBox();
	  HBox hBoxInput4 = new HBox();
	  HBox hBoxInput5 = new HBox();
	 
	 
	  Label massLabel = new Label("Mass                 :  ");
	  massLabel.setStyle("-fx-color:black;"+"-fx-font: bold 25px adobe;");
	  Label engineLabel = new Label("Engine              :  ");
	  engineLabel.setStyle("-fx-color:black;"+"-fx-font: bold 25px adobe;");
	  Label dinsityLabel = new Label("Air dinsity        :  ");
	  dinsityLabel.setStyle("-fx-color:black;"+"-fx-font: bold 25px adobe;");
	  Label coefficientFrictionLabel = new Label("Coefficient Friction : ");
	  coefficientFrictionLabel.setStyle("-fx-color:black;"+"-fx-font: bold 20px adobe;");
	  
	  
	  massText = new TextField("500");
	  massText.setMaxSize(75, 30);
	  massText.setStyle("-fx-font: bold 15px adobe;"+"-fx-padding:3;");
	  hBoxInput1.getChildren().addAll(massLabel,massText);
	  
	  engineText = new TextField("2500");
	  engineText.setMaxSize(75, 30);
	  engineText.setStyle("-fx-font: bold 15px adobe;"+"-fx-padding:3;");
	  hBoxInput2.getChildren().addAll(engineLabel,engineText);
	  
	  dinsityAirText = new TextField("1.29"); 
	  dinsityAirText.setMaxSize(75, 30);
	  dinsityAirText.setStyle("-fx-font: bold 15px adobe;"+"-fx-padding:3;");
	  hBoxInput3.getChildren().addAll(dinsityLabel, dinsityAirText);
	  
	  coefficientFrictionText = new TextField("0.30"); 
	  coefficientFrictionText.setMaxSize(75, 30);
	  coefficientFrictionText.setStyle("-fx-font: bold 15px adobe;"+"-fx-padding:3;");
	  hBoxInput4.getChildren().addAll(coefficientFrictionLabel,coefficientFrictionText);
	  
	  stop = new Button("Stop");
	  stop.setMaxSize(200, 100);
	  stop.setStyle("-fx-font: bold 25px adobe;"+"-fx-padding:3;");
	  start = new Button("Start");
	  start.setMaxSize(200, 100);
	  start.setStyle("-fx-font: bold 25px adobe;"+"-fx-padding:3;");
	  
	  
	  hBoxInput5.getChildren().addAll(new Label("          "),stop,new Label("                                    "),start);
	  
	  
	  Label changeValue = new Label("     *** Change Value ***");
      changeValue.setStyle("-fx-font: bold 25px adobe;"+"-fx-padding:3;");
	  vBoxInput.getChildren().addAll(input,hBoxInput1,hBoxInput2,hBoxInput3,hBoxInput4,changeValue,hBoxInput5);

	 stop.setOnAction(Event->
		{
			   isStop = false;	
	    });
	 start.setOnAction(Event->
		{		
				isStop = true;		
	    });
	 
	 

	  
	//-------------------- OutPut ---------------------------------------	
	  
	  
	  VBox vBoxOut = new VBox(); 
	 
	  Label outPut = new Label("----------< OutPut >-----------");
	  outPut.setStyle("-fx-color:black;"+"-fx-font: bold 25px adobe;"+"-fx-pref-width :350px;"+"-fx-pref-height:50px;");
	  
	 
	  outLabel = new Label();
	  outLabel.setMaxSize(350, 900);
	  outLabel.setStyle("-fx-background-color:black;"+"-fx-font: bold 13px adobe;");
	  outLabel.setTextFill(Color.WHITE);
	
	  vBoxOut.getChildren().addAll(outPut,outLabel);
	  vBoxOut.setTranslateY(75);
	  
	  pane.getChildren().addAll(vBoxInput,vBoxOut);
	  Scene scene = new Scene(pane,350,700);
	  
	  stage.setScene(scene);
	  stage.setX(0);
	  stage.setY(0);
	  stage.show();
		
	
	}

}
