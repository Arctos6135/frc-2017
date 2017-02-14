package org.usfirst.frc.team6135.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// For example to map the left and right motors, you could define the
	// following variables to use with your drivetrain subsystem.
	// public static int leftMotor = 1;
	// public static int rightMotor = 2;

	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
	//Ports
	//Motor controllers for driving
	public static final int rVicPort = 1;
	public static final int lVicPort = 0;
	
	//Victors fo r Robot Arm
	public static final int wVicPort = 2;
	
	//Motor Controllers for shooter
	public static final int shootLFTalon = 0;
	public static final int shootLBVic = 2;
	public static final int shootRFTalon = 1;
	public static final int shootRBVic = 3;
	
	//CANTalons for arm
	public static final int lArmTal = 9;//previously 8
	public static final int rArmTal = 8;//previously 9
	
	//Encoders for distance and balancing motors
	public static final int rEnc1 = 2;
	public static final int rEnc2 = 3;
	public static final int lEnc1 = 0;
	public static final int lEnc2 = 1;
	
	//Joystick
	public static final int dStick = 0;
	public static final int sStick = 1;
}
