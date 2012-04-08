package org.spout.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.PixelFormat;
import org.spout.api.Client;
import org.spout.api.Spout;
import org.spout.api.entity.Entity;
import org.spout.api.geo.World;
import org.spout.api.math.Matrix;
import org.spout.api.math.Vector2;
import org.spout.api.math.Vector3;
import org.spout.api.plugin.PluginStore;
import org.spout.api.render.BasicCamera;
import org.spout.api.render.Camera;
import org.spout.api.render.Renderer;
import org.spout.api.render.Shader;
import org.spout.engine.renderer.BatchVertexRenderer;
import org.spout.engine.renderer.shader.BasicShader;
import org.spout.engine.batcher.PrimitiveBatch;
import org.spout.engine.filesystem.FileSystem;


import java.awt.Color;
import java.io.File;


public class SpoutClient extends SpoutEngine implements Client {

	private Camera activeCamera;
	
	private final Vector2 resolution = new Vector2(854, 480);
	private final float aspectRatio = resolution.getX() / resolution.getY();

	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath",System.getProperty("user.dir") + "/natives/");
		SpoutClient c = new SpoutClient();
		Spout.setEngine(c);
		FileSystem.init();
		
		c.init(args);
		c.start();
		
	
	
	}
	
	

	public SpoutClient() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void init(String[] args){
		super.init(args);
		
		
		
		scheduler.startRenderThread();
		
		
	}
	
	public void initRenderer()
	{
		
		try {
			Display.setDisplayMode(new DisplayMode((int)resolution.getX(), (int)resolution.getY()));
			
			if(System.getProperty("os.name").toLowerCase().equals("mac")){
				String[] ver = System.getProperty("os.version").split("\\.");
				if(Integer.parseInt(ver[1]) > 7){
					ContextAttribs ca  = new ContextAttribs(3, 2).withProfileCore(true);
					Display.create(new PixelFormat(8, 24, 0), ca);
				}
				else{
					Display.create();
				}
			
				
			}
			else{
				Display.create();
				
			}
			
			
			System.out.println("OpenGL Information");
			System.out.println("Vendor: " + GL11.glGetString(GL11.GL_VENDOR));
			System.out.println("OpenGL Version: " + GL11.glGetString(GL11.GL_VERSION));
			System.out.println("GLSL Version: " + GL11.glGetString(GL20.GL_SHADING_LANGUAGE_VERSION));
			System.out.println("Extensions Supported: " + GL11.glGetString(GL11.GL_EXTENSIONS));
			
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		activeCamera = new BasicCamera(Matrix.createPerspective(75, aspectRatio, 0.001f, 1000), Matrix.createLookAt(new Vector3(-10, 10, 10), Vector3.ZERO, Vector3.UP));
		Shader shader = new BasicShader();
		renderer = new PrimitiveBatch();
		renderer.getRenderer().setShader(shader);
	}
	
	
	PrimitiveBatch renderer;
	final boolean[] sides  = { true, true, true, true, true, true };
	
	public void render(float dt){
		
		
		renderer.getRenderer().getShader().setUniform("View", activeCamera.getView());
		renderer.getRenderer().getShader().setUniform("Projection", activeCamera.getProjection());
		
		
		renderer.begin();
		renderer.addCube(Vector3.ZERO, Vector3.ONE, Color.red, sides);
		renderer.end();
		
		
		renderer.draw();
		
		
		
	}

	@Override
	public File getTemporaryCache() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getStatsFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getActivePlayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public World getWorld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Camera getActiveCamera() {
		return activeCamera;
	}

	@Override
	public void setActiveCamera(Camera activeCamera) {
		this.activeCamera = activeCamera;
	}

	@Override
	public PluginStore getPluginStore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File getResourcePackFolder() {
		// TODO Auto-generated method stub
		return null;
	}
}
