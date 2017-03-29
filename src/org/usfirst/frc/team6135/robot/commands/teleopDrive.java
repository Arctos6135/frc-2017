package org.usfirst.frc.team6135.robot.commands;

import org.usfirst.frc.team6135.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class teleopDrive extends Command {
	private double scale = 1.0;
	private static final boolean fullSpeedDelay = false;
	private static final boolean squaredInputs = false;
	private static final int xReverse = 1;
	private static final int yReverse = -1;
	private static final int zReverse = 1;
	private static final int xAxis = 4;
	private static final int yAxis = 1;
	private static final int zAxis = 2;
	private static final double threshold=0.75;
	public teleopDrive() {
		requires(Robot.drive);
		//requires(Robot.navx);
	}
	protected void initialize() {
	}
	protected boolean isFinished() {
        return false;
    }
	
	protected void execute() {
		double x;
		double y;
		double z;
		double l;
		double r;
		x = xReverse * Robot.oi.logitechJoystick.getRawAxis(xAxis);
		y = yReverse * Robot.oi.logitechJoystick.getRawAxis(yAxis);
		z = zReverse * Robot.oi.logitechJoystick.getRawAxis(zAxis);
		if(Math.abs(x) < 0.2) {
			x = 0;
		}
		if(Math.abs(y) < 0.2) {
			y = 0;
		}
		if(Math.abs(z) < 0.1) {
			z = 0;
		}
		if(x>threshold)
		{
			x=1;
		}
		if(x<-threshold)
		{
			x=-1;
		}
		if(y>threshold)
		{
			y=1;
		}
		if(y<-threshold)
		{
			y=-1;
		}
		if(z>threshold)
		{
			z=1;
		}
		if(z<-threshold)
		{
			z=-1;
		}
		if(squaredInputs) {
			x = Robot.drive.sensitivityCalc(x);
			y = Robot.drive.sensitivityCalc(y);
			z = Robot.drive.sensitivityCalc(z);
		}
		if(fullSpeedDelay) {
			x = Robot.drive.accCalcX(x);
			z = Robot.drive.accCalcZ(z);
		}
		if (y > 0.0) {
	   		if (x > 0.0) {
	    	   	r = y - x;
	        	l = Math.max(y, x);
	    	}
			else {
	        	r = Math.max(y, -x);
	        	l = y + x;
	    	}
	   	}
		else {
	    	if (x > 0.0) {
	    	    r = -Math.max(-y, x);
	    	    l = y + x;
	    	}
			else {
	    	    r = y - x;
	    	    l = -Math.max(-y, -x);
	    	}
	   	}
		if(l == 0 && r == 0) {
			Robot.drive.setMotors(z, -z);
		}
		else if(l == r && y > 0) {
			Robot.drive.setMotors(l * scale, r / scale); //Dividing one speed while multiplying other to speed up slower motor while slowing down faster motor
		}
		else if(l == r && y < 0) {
			Robot.drive.setMotors(l / scale, r * scale);
		}
		else {
			Robot.drive.setMotors(l, r);
		}	
		Robot.lEncSubsystem.outPutDis("Left:");
		Robot.rEncSubsystem.outPutDis("Right:");
	}
	protected void end() {
		Robot.drive.setMotors(0, 0);
    }
    protected void interrupted() {
    }
}
