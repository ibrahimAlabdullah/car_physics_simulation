package engineTester;

import static engineTester.MainGameLoop.carEntity;
import static gui.Gui.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import renderEngine.DisplayManager;



public class Physics 
	{
	
		Vector3f engineForce = new Vector3f(carEntity.m_engine,carEntity.m_engine,carEntity.m_engine);
		public Vector3f gravity = new Vector3f(0,-10,0);
		Vector3f totalForce = new Vector3f(0,0,0);
		
		
		float u = 1;
		
		float Cd = 0.30f; // coefficient of friction
		float a = 1.2f; //front area of car 
		float rho = 1.29f;//density of air
		float Cdrag = 0.5f*Cd*a*rho;//constant of drag force it's equal to 0.5*Cd*a*rho
		
		float Crr = 30 * Cdrag; //constant of rolling resistance
		
		float radius = 0.300f;
		
		public Physics()
		{
		}
		

		
public void physics()
		{	
	
		if(isStop == false)
		{
			carEntity.m_mass = 500;
			carEntity.m_engine = 2500;
			rho = 1.29f ; 
			Cd = 0.30f;
		
		}
		else
		{
			carEntity.m_mass = Float.parseFloat(massText.getText());
			carEntity.m_engine = Float.parseFloat(engineText.getText());
			rho = Float.parseFloat(dinsityAirText.getText());
			Cd = Float.parseFloat(coefficientFrictionText.getText());
		}
			
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && carEntity.m_velocity.x > 0)
			totalForce =add ( add(  add(weight(),fr()) , add(fDrag() , fBrake()) ) , frr() ) ;
		else
		    totalForce =add ( add(  add(weight(),fr()) , add(fDrag() , fTraction()) ) , frr() ) ;
		
		
		carEntity.m_acceleration = (  div (totalForce,carEntity.m_mass) );
		
		carEntity.m_velocity = (add(carEntity.m_velocity,mult(carEntity.m_acceleration,DisplayManager.getFrameTimeSeconds())));	
	    
		carEntity.m_angvelocity.y = carEntity.m_velocity.x/radius;
	

		}
		
			
		public Vector3f weight()
		{
			
			return  mult(gravity,carEntity.m_mass);
		}
		
		//reaction force
		public Vector3f fr()
		{  
			if(carEntity.getRotX() > 0 )
				return mult(gravity,-carEntity.m_mass *(float)Math.sin(carEntity.getRotX()) );
			return mult(gravity,-carEntity.m_mass);
		}
		
	
		
         
          
		//drag force
		public Vector3f fDrag()
		{
			Vector3f vec1 = mult(carEntity.m_velocity,-Cdrag);
			return mult(vec1,carEntity.m_velocity.length());
		}
		
		//rolling resistance force
		public Vector3f frr()
		{
			return mult(carEntity.m_velocity,-Crr);
		}
		
		public Vector3f fTraction()
		{
			return mult(engineForce,u);
		}
	
		public Vector3f fBrake()
		{
			return mult(engineForce,-u);
		}
		
	
		
		
		//Operations on vectors
		public Vector3f sub(Vector3f sub1 , Vector3f sub2)
		{
			return new Vector3f(sub1.x-sub2.x,sub1.y-sub2.y,sub1.z-sub2.z);
		}
		public Vector3f mult(Vector3f vec , float number)
		{
			return new Vector3f(vec.x*number,vec.y*number,vec.z*number);
		}
		public Vector3f div(Vector3f vec , float number)
		{
			return new Vector3f(vec.x/number,vec.y/number,vec.z/number);
		}
		public Vector3f add(Vector3f add1 , Vector3f add2)
		{
			return new Vector3f(add1.x+add2.x,add1.y+add2.y,add1.z+add2.z);
		}
		
		
}