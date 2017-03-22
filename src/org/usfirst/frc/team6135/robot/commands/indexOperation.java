// RobotBuilder Version: 2.0
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc.team6135.robot.commands;
import org.usfirst.frc.team6135.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will start the indexer
 * Activated by: Command SIOperation
 * Creater: Carl Yu
 */
public class indexOperation extends Command {

    public double targetRPM=-1;
    public double adjustVal=0;
    public boolean c;
    public indexOperation() {
        requires(Robot.indexer);
    }
    public indexOperation(double s,boolean f)
    {
    	requires(Robot.indexer);
    	this.targetRPM=s;
    	this.c=f;
    	
    }
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(c)
    	{
        	autoShoot();
    	}
    	else
    	{
    		Robot.indexer.set(-1);
    	}
    }
    protected void autoShoot()
    {
    	if(Robot.indexer.getVel()>(targetRPM+adjustVal)*1.03)
    	{
    		Robot.indexer.set(0);
    	}
    	else
    	{
    		if(Robot.indexer.getVel()<(targetRPM+adjustVal)*1.03 /*&& Robot.indexer.getVel()>(targetRPM+adjustVal)*0.97*/)
    		{
    			Robot.indexer.set(1);
    		}
    		else
    		{
    			Robot.indexer.set(0);
    		}
    	}
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
