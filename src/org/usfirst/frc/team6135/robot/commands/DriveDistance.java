package org.usfirst.frc.team6135.robot.commands;

import org.usfirst.frc.team6135.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class driveDistance extends Command {
	private double distance = 0.0;
	private double speed = 1.0;
	boolean useNAVX = false;
	boolean useGyro=false;
	boolean useEncoder=false;
	public driveDistance(double d,double s, boolean n,boolean g, boolean e) {
		distance = d;
		speed=s;
		useNAVX=n;
		useGyro=g;
		useEncoder=e;
		requires(Robot.drive);
		requires(Robot.lEncSubsystem);
		requires(Robot.rEncSubsystem);
	}
    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.lEncSubsystem.reset();
		Robot.rEncSubsystem.reset();
		Robot.lEncSubsystem.setSetpoint(distance);
		Robot.rEncSubsystem.setSetpoint(distance);
		if(useEncoder)
		{
			Robot.lEncSubsystem.setSpeed(speed);
			Robot.rEncSubsystem.setSpeed(speed);
			Robot.lEncSubsystem.enable();
			Robot.rEncSubsystem.enable();
		}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		if(useEncoder)
		{
	    	Robot.drive.setMotors(Robot.lEncSubsystem.getOutput(), Robot.rEncSubsystem.getOutput());
		}
		if(useGyro)
		{
			Robot.drive.driveStraight();
		}
		Robot.lEncSubsystem.outPutDis("Left:");
		Robot.rEncSubsystem.outPutDis("Right:");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.lEncSubsystem.onTarget() && Robot.rEncSubsystem.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
		Robot.lEncSubsystem.setSpeed(0);
		Robot.rEncSubsystem.setSpeed(0);
		Robot.lEncSubsystem.disable();
		Robot.rEncSubsystem.disable();
		Robot.lEncSubsystem.reset();
		Robot.rEncSubsystem.reset();
		Robot.drive.setMotors(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
