package org.usfirst.frc.team6135.robot;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Drive implements PIDOutput {
	//Variables used
	private double rSpeed = 0.0;
	private double lSpeed = 0.0;
	private double xInput;
	private double yInput;
	private double xFinal;
	private double yFinal;
	private double scale = 1.0;
	//Constants
	private static final double kP = 0.03;
    private static final double kI = 0.00;
    private static final double kD = 0.00;
    private static final double kF = 0.00;
	private static final double kToleranceDegrees = 2.0f;
    
	private static final int yAxis = 1;
	private static final int yReverse = -1;
	private static final int xAxis = 0;
	private static final int xReverse = 1;

	private static final int exactDriveButton = 2;

	private static final boolean rReverse = false;
	private static final boolean lReverse = false;
	//Objects
	private Joystick driveStick = null;
	private Victor leftDrive = null;
	private Victor rightDrive = null;
	private AHRS ahrs = null;
	PIDController balance = null;
	//Constructors
	public Drive(Joystick j, int l, int r) {
		driveStick = j;
		leftDrive = new Victor(l);
		rightDrive = new Victor(r);
		leftDrive.set(0);
		rightDrive.set(0);
		leftDrive.setInverted(lReverse);
		rightDrive.setInverted(rReverse);
		try {
            ahrs = new AHRS(I2C.Port.kMXP); 
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
		balance = new PIDController(kP, kI, kD, kF, ahrs, this);
		balance.setSetpoint(0.0f);
		balance.setInputRange(-180.0f,  180.0f);
        balance.setOutputRange(0.5, 2.0);
        balance.setAbsoluteTolerance(kToleranceDegrees);
        balance.setContinuous(true);
		balance.enable();
	}
	
	//Direct object access methods
	public void setMotors(double l, double r) {//sets motor speeds accounting for directions of motors
		leftDrive.set(scale * l);
		rightDrive.set(scale * r);
	}
	public void setLeft(double d) {
		leftDrive.set(d);
	}
	public void setRight(double d) {
		rightDrive.set(d);
	}
	public void getSpeeds() {//converts input values to motor speeds
		if (yFinal > 0.0) {
	   		if (xFinal > 0.0) {
	       	    rSpeed = yFinal - xFinal;
	       	    lSpeed = Math.max(yFinal, xFinal);
	       	}
			else {
	       	    rSpeed = Math.max(yFinal, -xFinal);
	       	    lSpeed = yFinal + xFinal;
	       	}
	   	}
		else {
	       	if (xFinal > 0.0) {
	       	    rSpeed = -Math.max(-yFinal, xFinal);
	       	    lSpeed = yFinal + xFinal;
	       	}
			else {
	       	    rSpeed = yFinal - xFinal;
	       	    lSpeed = -Math.max(-yFinal, -xFinal);
	       	}
	   	}
	}
	public void getValues(double x, double y, boolean sensitivity, boolean acc) {//takes input values and applies appropriate calculations to values to account for human error and/or joystick deficiencies
		xFinal = x;
		yFinal = y;
		
		if(sensitivity) {
			xFinal = sensitivityCalc(xFinal);
			yFinal = sensitivityCalc(yFinal);
		}	
		if(acc) {
			xFinal = accCalc(xFinal);
			yFinal = accCalc(yFinal);
		}
	}
	
	//Teleop Driving methods
	private static final double accBound = 0.7; //The speed after which the drive starts to accelerate over time
	private static final int accLoop = 15; //The number of loops for the bot to accelerate to max speed
	private int accLoopCount = 0;
	private double accCalc(double input) {//applies a delay for motors to reach full speed for larger joystick inputs
		if(input > accBound && accLoopCount < accLoop) {//positive inputs
	    		return accBound + (input - accBound) * (accLoopCount++ / (double) accLoop);
	    }
		else if(input < -accBound && accLoopCount < accLoop) {//negative inputs
	    		return -accBound + (input + accBound) * (accLoopCount++ / (double) accLoop);
	    }
	    else if(Math.abs(input) <= accBound) {
	    	accLoopCount = 0;
	    }
		return input;
	}
	private double sensitivityCalc(double input) {//Squares magnitude of input to reduce magnitude of smaller joystick inputs
		if (input >= 0.0) {
	        return (input * input);
	    }
		else {
	    	return -(input * input);
	    }
	}

	private void exactDrive() {//This method will essentially "round" the joystick inputs to purely in the x direction or purely in the y direction depending on which has greater magnitude 
		if(Math.abs(yInput) > Math.abs(xInput)) {
			xInput = 0;
			ahrs.reset();
		}
		else {
			yInput = 0;
		}
	}
	private static final boolean useSensitivityCalc = true;
	private static final boolean useAccelerateCalc = true;
	public void teleopDrive() {//Implements individual driving and input methods to allow driving in teleop
		xInput = xReverse * driveStick.getRawAxis(xAxis);
		yInput = yReverse * driveStick.getRawAxis(yAxis);
		if(Math.abs(xInput) < 0.1 && Math.abs(yInput) < 0.1) {
			setMotors(0, 0);
		}
		else {
			if(true || driveStick.getRawButton(exactDriveButton)) {
				exactDrive();
			}
			
			getValues(xInput, yInput, useSensitivityCalc, useAccelerateCalc);
			
			getSpeeds();
			
			
			setMotors(lSpeed, rSpeed);
		}
	}
	public void pidWrite(double output) {
        scale = output;
    }
}
