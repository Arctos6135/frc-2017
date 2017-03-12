package org.usfirst.frc.team6135.robot.commands.debug;

import org.usfirst.frc.team6135.robot.Robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will start the shooter (DEBUG)
 * Activated by: Smartdashboard
 * Creater: Carl Yu
 */
public class shooterDEBUG extends Command {

	public double targetRPM;
	public double adjustVal;
	public Joystick operatorJ;
	public shooterDEBUG() {
        requires(Robot.shooter);
    }
	public shooterDEBUG(double v, Joystick j)
	{
		requires(Robot.shooter);
		this.targetRPM=v;
		this.operatorJ=j;
	}
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooter.setOn(true);
    	Robot.shooter.set(0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	autoShoot();
    }
    protected void autoShoot()
    {
    	if(Robot.shooter.getVel()>(targetRPM+adjustVal)*1.03)
    	{
    		Robot.shooter.set(0);
    	}
    	else
    	{
    		if(Robot.shooter.getVel()<(targetRPM+adjustVal)*1.03 && Robot.shooter.getVel()>(targetRPM+adjustVal)*0.97)
    		{
    			Robot.shooter.set(1);
    		}
    		else
    		{
    			Robot.shooter.set(0);
    		}
    	}
    	if(operatorJ.getRawAxis(3)>0)
    	{
    		incrementAdjVal();
    	}
    	if(operatorJ.getRawAxis(2)>0)
    	{
    		decreaseAdjVal();
    	}
    	if(operatorJ.getRawButton(6))
    	{
    		roughIncrement();
    	}
    	if(operatorJ.getRawButton(5))
    	{
    		roughDecrease();
    	}
    }
    public void incrementAdjVal()
    {
    	this.adjustVal+=20;
    }
    public void roughIncrement()
    {
    	this.adjustVal+=100;
    }
    public void roughDecrease()
    {
    	this.adjustVal-=100;
    }
    public void decreaseAdjVal()
    {
    	this.adjustVal-=20;
    }
    public void setTargetRPM(double v)
    {
    	this.targetRPM=v;
    }
    public String toString()
    {
    	return "targetRPM: "+targetRPM+" "+"adjustVal: "+adjustVal;
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        Robot.shooter.setOn(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
