package org.usfirst.frc.team6135.robot.commands;

import org.usfirst.frc.team6135.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class intakeOperation extends Command {

    private double speed;
    private boolean ifFullBack=false;
	public intakeOperation() {
        requires(Robot.intake);
    }
	public intakeOperation(boolean v)
	{
		requires(Robot.intake);
		ifFullBack=v;
	}
    // Called just before this Command runs the first time
    protected void initialize() {
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double t3Val=Robot.oi.operationJ.getRawAxis(3);
    	double t2Val=-Robot.oi.operationJ.getRawAxis(2);
    	speed=t3Val+t2Val;
    	if(ifFullBack)
    	{
    		speed=-1.0;
    	}
    	Robot.intake.set(speed);
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
