package org.usfirst.frc.team6135.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public abstract class baseSubsystem extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public baseSubsystem(String n)
	{
		this.className=n;
		this.ifOn=false;
	}
	protected boolean ifOn;
	public String className;
	public abstract void set(double s);
	public abstract double getRPM();
	public void stop()
	{
		set(0);
	}
	public void turnOn()
	{
		this.ifOn=true;
	}
	public void turnOff()
	{
		this.ifOn=false;
	}
	public void printToSmartDashboard()
	{
		SmartDashboard.putBoolean(className+"On", ifOn);
		SmartDashboard.putNumber(className+"RPM", getRPM());
	}
}

