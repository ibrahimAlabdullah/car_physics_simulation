package engineTester;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import static gui.Gui.*;

public class MainGameLoop 
{
	public static Player carEntity;
	public static Physics physics; 

	public static void main(String[] args)
	{
		
		
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		
		
		new Thread()
		{
			@Override
			public void run()
			{
			  javafx.application.Application.launch(gui.Gui.class);
			}
		}.start();
	
		
		
		// *********TERRAIN TEXTURE STUFF***********
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		Terrain terrain = new Terrain(0,-1,loader, texturePack, blendMap,"heightmap");
		Terrain terrain2 = new Terrain(-1,-1,loader,texturePack, blendMap,"heightmap");
		// *****************************************
		
		TexturedModel tree = new TexturedModel(OBJLoader.loadObjModel("tree", loader), new ModelTexture(loader.loadTexture("tree")));
		TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture")));
		TexturedModel flower = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("flower")));

		ModelTexture fernTexture = new ModelTexture(loader.loadTexture("fern"));

		TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTexture);

		TexturedModel bobble = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree", loader), new ModelTexture(loader.loadTexture("lowPolyTree")));

		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		grass.getTexture().setReflectivity(0);
		flower.getTexture().setHasTransparency(true);
		flower.getTexture().setUseFakeLighting(true);
		flower.getTexture().setReflectivity(0);
		fern.getTexture().setHasTransparency(true);
		fern.getTexture().setUseFakeLighting(true);
		fern.getTexture().setReflectivity(0);
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();

		for (int i = 0; i < 500; i++)
		{
			if (i % 7 == 0)
			{
				float x = random.nextFloat() * 800 - 400;
				float z = random.nextFloat() * -600;
				float y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(fern, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 0.9f));

				x = random.nextFloat() * 800 - 400;
				z = random.nextFloat() * -600;
				y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(grass, new Vector3f(x, y, z), 0, 0, 0, 1.8f));

				x = random.nextFloat() * 800 - 400;
				z = random.nextFloat() * -600;
				y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(flower, new Vector3f(x, y, z), 0, 0, 0, 2.3f));
			}
		}
		for (int i = 0; i < 100; i++)
		{
			if (i % 3 == 0) 
			{
				float x = random.nextFloat() * 800 - 400;
				float z = random.nextFloat() * -600;
				float y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(bobble, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, random.nextFloat() * 0.1f + 0.6f));

				x = random.nextFloat() * 800 - 400;
				z = random.nextFloat() * -600;
				y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(tree, new Vector3f(x, y, z), 0, 0, 0, random.nextFloat() * 1 + 4));
			}
			
		}


		Light light = new Light(new Vector3f(90000, 90000, 90000), new Vector3f(1,1,1));
			

		TexturedModel carTexture = new TexturedModel(OBJLoader.loadObjModel("car",  loader), new ModelTexture(loader.loadTexture("car")));
		
		
		carEntity = new Player(carTexture, new Vector3f(100,3,-50), 0,180,0,2f);
		Camera camera = new Camera(carEntity);
		
		MasterRenderer renderer = new MasterRenderer(loader);
		physics = new Physics();
		carEntity.reset();
		
		while(!Display.isCloseRequested()) 
		{
			camera.move();
			carEntity.move(terrain);
			renderer.processEntity(carEntity);
			
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
			
			for (Entity entity : entities)
			{
				renderer.processEntity(entity);
			}
			
			renderer.render(light, camera);
			
			javafx.application.Platform.runLater(new Runnable() 
	          { 
				@Override
	              public void run() 
	              { 
					outLabel.setText("Position  :  [ "+Float.toString(Math.round(carEntity.getPosition().x) )+" , "+Float.toString(Math.round(carEntity.getPosition().y))+" , "+Float.toString(Math.round(carEntity.getPosition().z))+" ]"
				    +"\n\nRotation  :  [ "+Float.toString(Math.round(carEntity.getRotX()) )+" , "+Float.toString(Math.round(carEntity.getRotY()))+" , "+Float.toString(Math.round(carEntity.getRotZ() ))+" ]"
					+"\n\n Velocity : [ "+Float.toString(Math.round(carEntity.m_velocity.x))+" ] "
					+"\n\nAngularVelocity  :  [ "+Float.toString(Math.round(carEntity.m_velocity.y))+" ]"
			    	+"\n\n acceleration : [ "+Float.toString(Math.round(carEntity.m_acceleration.x))+" ] \n\n\n");
	              }
	          });
			
			
			
			physics.physics();
			
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUp();
		
		DisplayManager.closeDisplay();
	}

}
