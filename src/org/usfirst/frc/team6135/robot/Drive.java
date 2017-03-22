package org.usfirst.frc.team6135.robot;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 This class handles the driving mechanism of the robot. It uses navX to adjust its position
 Creater: Adrian Carpenter 
 */
public class Drive implements PIDOutput {
	//Variables used
	private double rSpeed = 0.0;
	private double lSpeed = 0.0;
	private double xInput;
	private double yInput;
	private double zInput;
	private double xFinal;
	private double yFinal;
	private double scale = 1.0;
	private double speed = 1.0;
	private static boolean straight = true;//If true drive straight; if false rotate to correct angle
	
	//Constants
	private static final double kPS = 0.03;//Driving straight
    private static final double kIS = 0.00;
    private static final double kDS = 0.00;
	private static final double kToleranceDegreesS = 2.0f;
    
	private static final double kPR = 0.03;//Rotating to angle
    private static final double kIR = 0.00;
    private static final double kDR = 0.00;
	private static final double kToleranceDegreesR = 2.0f;
	
	private static final int yAxis = 1;
	private static final int yReverse = -1;
	private static final int xAxis = 0;
	private static final int xReverse = 1;
	private static final int zAxis = 2;

	private static final int exactDriveButton = 1;

	private static final boolean rReverse = true;
	private static final boolean lReverse = false;
	
	private static final double WHEEL_RADIUS=6;
	private static final double PULSECNT=48;
	//Objects
	private Joystick driveStick = null;
	private Victor leftDrive = null;
	private Victor rightDrive = null;
	private AHRS ahrs = null;
	//private CANTalon test = null;
	public PIDController balance = null;
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
			ahrs = new AHRS(SerialPort.Port.kUSB);
			SmartDashboard.putString("NAVX", "Not RIP?");
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
            SmartDashboard.putString("NAVX", "RIP");
        }
		balance = new PIDController(kPS, kIS, kDS, ahrs, this);
		balance.setSetpoint(0.0f);
		balance.setInputRange(-180.0f,  180.0f);
		balance.setOutputRange(-1, 1);
        balance.setAbsoluteTolerance(kToleranceDegreesS);
        balance.setContinuous(true);
		balance.enable();
		LiveWindow.addActuator("Drive", "PID", balance);
	}
	public void setRotate() {
		ahrs.reset();
		balance.setPID(kPR, kIR, kDR);
		balance.setAbsoluteTolerance(kToleranceDegreesR);
		straight = false;
	}
	//public void test() {
	//	test.set(0.3);
	//}
	public void setStraight() {
		ahrs.reset();
		balance.setPID(kPS, kIS, kDS);
		balance.setAbsoluteTolerance(kToleranceDegreesS);
		straight = true;
	}
	//Direct object access methods
	public void setMotors(double l, double r) {//sets motor speeds accounting for directions of motors
		if(l == r && yInput > 0) {
			leftDrive.set(l * scale);
			rightDrive.set(r / scale); //Dividing one speed while multiplying other to speed up slower motor while slowing down faster motor
		}
		else if(l == r && yInput < 0) {
			leftDrive.set(l / scale);
			rightDrive.set(r * scale);
		}
		else {
			leftDrive.set(l * speed);
			rightDrive.set(r * speed);
		}
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
		zInput = driveStick.getRawAxis(zAxis);
		if(driveStick.getRawButton(exactDriveButton)) {
			exactDrive();
		}
		if(Math.abs(xInput) < 0.2) {
			xInput = 0;
			if(!balance.isEnabled()) {
				//balance.enable();
				ahrs.reset();
			}
		}
		else {
			if(balance.isEnabled()) {
				balance.disable();
			}
		}
		if(Math.abs(yInput) < 0.2) {
			yInput = 0;
			if(Math.abs(zInput) < 0.2) {
				zInput = 0;
			}
			else {
				xInput = zInput;
			}
		}
		getValues(xInput, yInput, useSensitivityCalc, useAccelerateCalc);
			
		getSpeeds();
			
		setMotors(lSpeed, rSpeed);
	}
	public void pidWrite(double output) {
		if(straight) {
			scale = Math.pow(2, output);
			speed = 1.0;
		}
		else {
			speed = output;
			scale = 1.0;
		}
    }
	public void reverse() {
		leftDrive.setInverted(!leftDrive.getInverted());
		rightDrive.setInverted(!rightDrive.getInverted());
		Victor temp1 = leftDrive;
		Victor temp2 = rightDrive;
		rightDrive = temp1;
		leftDrive = temp2;
	}
	//added accessMethod
	public void printValues() {
		SmartDashboard.putNumber("Yaw", ahrs.getYaw());
		SmartDashboard.putNumber("Angle", ahrs.getAngle());
		SmartDashboard.putNumber("Scale", scale);
		SmartDashboard.putBoolean("PID", balance.isEnabled());
		SmartDashboard.putNumber("Compass", ahrs.getCompassHeading());
	}
}
