package cn.doitoo.game.framework.opengl.load3d;

public interface IDirtyManaged 
{
	public boolean isDirty();
	public void setDirtyFlag();
	public void clearDirtyFlag();
}
