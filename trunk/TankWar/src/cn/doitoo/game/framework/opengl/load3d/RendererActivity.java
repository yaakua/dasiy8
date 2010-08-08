package cn.doitoo.game.framework.opengl.load3d;


import cn.doitoo.game.framework.context.G;
import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.WindowManager;

/**
 * Extend this class when creating your min3d-based Activity (application) 
 * Then, override initScene() and updateScene().
 * 
 * Override onCreateSetContentView() to change layout, if desired.
 */
public class RendererActivity extends Activity implements ISceneController
{
	public Scene scene;
	protected GLSurfaceView _glSurfaceView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//
		// These 4 lines are important.
		//
		Shared.context(this);
		G.setContext(this);
		scene = new Scene(this);
		Renderer r = new Renderer(scene);
		Shared.renderer(r);
		
		_glSurfaceView = new GLSurfaceView(this);
		
		// Uncomment for logging:
		// _glSurfaceView.setDebugFlags(GLSurfaceView.DEBUG_CHECK_GL_ERROR | GLSurfaceView.DEBUG_LOG_GL_CALLS);

		_glSurfaceView.setRenderer(r);
		_glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		
		onCreateSetContentView();
	}
	
	/**
	 * Separated out for easier overriding...
	 */
	protected void onCreateSetContentView()
	{
		setContentView(_glSurfaceView);
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		_glSurfaceView.onResume();
	}
	
	@Override
	protected void onPause() 
	{
		super.onPause();
		_glSurfaceView.onPause();
	}

	/**
	 * Instantiation of Object3D's, setting their properties, and adding Object3D's 
	 * to the scene should be done here. Or any point thereafter.
	 * 
	 * Note that this method is always called after GLCanvas is created, which occurs
	 * not only on Activity.onCreate(), but on Activity.onResume() as well.
	 * It would be the user's responsibility to build the logic to restore state on-resume,
	 * if that's desired. 
	 */
	public void initScene()
	{
	}

	/**
	 * All manipulation of scene and Object3D instance properties should go here.
	 * Gets called on every frame, right before drawing.   
	 */
	public void updateScene()
	{
	}
	
}
