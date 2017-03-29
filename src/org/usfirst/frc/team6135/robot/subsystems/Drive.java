package org.usfirst.frc.team6135.robot.subsystems;
import java.awt.geom.Arc2D.Double;

import org.usfirst.frc.team6135.robot.RobotMap;
import org.usfirst.frc.team6135.robot.commands.teleopDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;


public class Drive extends Subsystem {

	
	//Constants
	private static final boolean rReverse = true;
	private static final boolean lReverse = false;
	private static final double kA=0.03;
	
	//Objects
	private VictorSP leftDrive = null;
	private VictorSP rightDrive = null;
	public ADXRS450_Gyro gyro=null;
	public AHRS ahrs=null;
	public RobotDrive robotDrive=null;
	//Constructors
	public Drive() {
		gyro=RobotMap.gyro;
		ahrs=new AHRS(SerialPort.Port.kUSB1);
		robotDrive=new RobotDrive(RobotMap.leftDriveVictor,RobotMap.rightDriveVictor);
		leftDrive = RobotMap.leftDriveVictor;
		rightDrive = RobotMap.rightDriveVictor;
		leftDrive.set(0);
		rightDrive.set(0);
		leftDrive.setInverted(lReverse);
		rightDrive.setInverted(rReverse);
		ahrs.reset();
	}
	//Direct object access methods
	public void setMotors(double l, double r) {//sets motor speeds accounting for directions of motors
		leftDrive.set(l);
		rightDrive.set(r);
	}
	public void setLeft(double d) {
		leftDrive.set(d);
	}
	public void setRight(double d) {
		rightDrive.set(d);
	}

	//Teleop Driving methods
	private static final double accBoundX = 0.7; //The speed after which the drive starts to accelerate over time
	private static final int accLoopX = 15; //The number of loops for the bot to accelerate to max speed
	private int accLoopCountX = 0;
	public double accCalcX(double input) {//applies a delay for motors to reach full speed for larger joystick inputs
		if(input > accBoundX && accLoopCountX < accLoopX) {//positive inputs
	    		return accBoundX + (input - accBoundX) * (accLoopCountX++ / (double) accLoopX);
	    }
		else if(input < -accBoundX && accLoopCountX < accLoopX) {//negative inputs
	    		return -accBoundX + (input + accBoundX) * (accLoopCountX++ / (double) accLoopX);
	    }
	    else if(Math.abs(input) <= accBoundX) {
	    	accLoopCountX = 0;
	    }
		return input;
	}

	private static final double accBoundY = 0.7; //The speed after which the drive starts to accelerate over time
	private static final int accLoopY = 15; //The number of loops for the bot to accelerate to max speed
	private int accLoopCountY = 0;
	public double accCalcY(double input) {//applies a delay for motors to reach full speed for larger joystick inputs
		if(input > accBoundY && accLoopCountY < accLoopY) {//positive inputs
	    		return accBoundY + (input - accBoundY) * (accLoopCountY++ / (double) accLoopY);
	    }
		else if(input < -accBoundY && accLoopCountY < accLoopY) {//negative inputs
	    		return -accBoundY + (input + accBoundY) * (accLoopCountY++ / (double) accLoopY);
	    }
	    else if(Math.abs(input) <= accBoundY) {
	    	accLoopCountY = 0;
	    }
		return input;
	}

	private static final double accBoundZ = 0.7; //The speed after which the drive starts to accelerate over time
	private static final int accLoopZ = 15; //The number of loops for the bot to accelerate to max speed
	private int accLoopCountZ = 0;
	public double accCalcZ(double input) {//applies a delay for motors to reach full speed for larger joystick inputs
		if(input > accBoundZ && accLoopCountZ < accLoopZ) {//positive inputs
	    		return accBoundZ + (input - accBoundZ) * (accLoopCountZ++ / (double) accLoopZ);
	    }
		else if(input < -accBoundZ && accLoopCountZ < accLoopZ) {//negative inputs
	    		return -accBoundZ + (input + accBoundZ) * (accLoopCountZ++ / (double) accLoopZ);
	    }
	    else if(Math.abs(input) <= accBoundZ) {
	    	accLoopCountZ = 0;
	    }
		return input;
	}
	public double sensitivityCalc(double input) {//Squares magnitude of input to reduce magnitude of smaller joystick inputs
		if (input >= 0.0) {
	        return (input * input);
	    }
		else {
	    	return -(input * input);
	    }
	}
	public void reverse() {
		leftDrive.setInverted(!leftDrive.getInverted());
		rightDrive.setInverted(!rightDrive.getInverted());
		VictorSP temp1 = leftDrive;
		VictorSP temp2 = rightDrive;
		rightDrive = temp1;
		leftDrive = temp2;
	}
	public double getGyroAngle()
	{
		return gyro.getAngle();
	}
	public double getNAVXAngle()
	{
		return ahrs.getAngle();
	}
	public void driveStraight()
	{
		robotDrive.drive(0.6, -gyro.getAngle()*kA);
	}
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new teleopDrive());
		
	}
}
