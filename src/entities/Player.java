package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;


public class Player extends Entity
{

	private static  float RUN_SPEED = 20;   
	private static  float TURN_SPEED = 160; 
	private static  float GRAVITY = -50;
	
	private static final float TERRAIN_HEIGHT = 0;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
		
		
	public Vector3f  m_velocity;
	public Vector3f  m_acceleration;
	public Vector3f  m_angvelocity;
	
	public float m_mass;
	public float m_engine;

	
	
	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) 
	{
		super(model, position, rotX, rotY, rotZ, scale);
		m_mass = 500;
		m_engine = 2500;
		reset();
	}
	
	public void reset() 
	   {
		   m_velocity = new Vector3f(0, 0, 0);
		   m_acceleration = new Vector3f(0, 0, 0);
		   m_angvelocity = new Vector3f(0, 0, 0);
		}

	public void move(Terrain terrain)
	{
		
		checkInputs();
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z)+3f;
		float terrainNextHeight = terrain.getHeightOfTerrain(super.getPosition().x+4, super.getPosition().z+4)+3f;
		
		float tendon = Distance_B_W(new Vector3f(super.getPosition().x, terrainHeight, super.getPosition().z),
				new Vector3f(super.getPosition().x+3,terrainNextHeight , super.getPosition().z+3));
		float currentHeight = terrainHeight - 0;
		float nextHeight = terrainNextHeight - 0;
		
		float rib = nextHeight - currentHeight;
		
		float theta = (float) Math.asin(rib / tendon);
		
		if(rib > 0)
		{
			super.setRotX(theta * (float) Math.sin(Math.toRadians(super.getRotY() ) ));
			super.setRotZ(theta * (float) Math.sin(Math.toRadians(super.getRotY() ) ));
		}
		else
		{
			super.setRotX(-theta * (float) Math.sin(Math.toRadians(super.getRotY())  ));
			super.setRotZ(-theta * (float) Math.sin(Math.toRadians(super.getRotY()) ));
		}
		
	
		if (super.getPosition().y  < terrainHeight)
		{
			super.getPosition().y = terrainHeight;
		}
			
	}
	
	public float Distance_B_W(Vector3f current , Vector3f next)
	{
		Vector3f temp1 = current;
		Vector3f temp2 = next;
		float tempX = temp1.x - temp2.x;
		float tempZ = temp1.z - temp2.z;
		float temp = tempX*tempX + tempZ*tempZ;
		float distance = (float) Math.sqrt(temp);
			return distance;
	}
	
	
	
	private void checkInputs()
	{
		
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) 
		{
			RUN_SPEED = m_velocity.x;
			this.currentSpeed = RUN_SPEED;
		} 
		else if (Keyboard.isKeyDown(Keyboard.KEY_S)) 
		{
			RUN_SPEED = m_velocity.x;
			this.currentSpeed = -RUN_SPEED;
		}
		else 
		{
			
			if(m_velocity.x > 2 )
			{
				m_velocity.x = m_velocity.x - 0.5f;
				this.currentSpeed =  m_velocity.x;
			}
			else
			{
			     this.currentSpeed = 0;
			     reset();
			}
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) 
		{
			TURN_SPEED = m_angvelocity.y;
			this.currentTurnSpeed = -TURN_SPEED;
		}
		else if (Keyboard.isKeyDown(Keyboard.KEY_A)) 
		{
			TURN_SPEED = m_angvelocity.y;
			this.currentTurnSpeed = TURN_SPEED;
			
		} 
		else 
		{
			this.currentTurnSpeed = 0;
		}
		
		
	}
}
