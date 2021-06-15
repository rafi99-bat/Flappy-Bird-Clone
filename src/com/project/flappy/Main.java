package com.project.flappy;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.project.flappy.graphics.Shader;
import com.project.flappy.math.Vector3f;
import com.project.flappy.util.ShaderUtils;

public class Main implements Runnable {
	
	private int width = 1280;
	private int height = 720;
	private String title = "Flappy";
	
	private boolean running = false;
	private Thread thread;
	
	public void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	private void init() {
		String version = glGetString(GL_VERSION);
		System.out.println("OpenGL " + version);
		
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		Shader.loadAll();
	}
	
	public void run() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			ContextAttribs context = new ContextAttribs(3, 3);
			Display.create(new PixelFormat(), context.withProfileCore(true));
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		
		init();
		
		int vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		Shader shader = Shader.BASIC;
		shader.enable();
		shader.setUniform3f("col", new Vector3f(0.8f, 0.2f, 0.3f));
		
		while(running) {
			render();
			Display.update();
			if(Display.isCloseRequested())
				running = false;
		}
		Display.destroy();
	}
	
	private void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		glDrawArrays(GL_TRIANGLES, 0, 3);
	}
	
	public static void main(String[] args) {
		new Main().start();
	}

}
