package org.usfirst.frc.team6135.robot.subsystems;

import org.usfirst.frc.team6135.robot.Drive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This command will start the shooter (DEBUG CLASS)
 * Activated by: SmartDashboard
 * Creater: Carl Yu
 */
public class AutoDrive extends PIDSubsystem {
	//Constants
	private static final double kPD = 0.03;//Driving specific distance
    private static final double kID = 0.00;
    private static final double kDD = 0.00;
	private static final double kToleranceDegreesD = 2.0f;
    
	private static final double kPR = 0.03;//Rotating accurately
    private static final double kIR = 0.00;
    private static final double kDR = 0.00;
	private static final double kToleranceDegreesR = 2.0f;
	
	private static final double encDist = 0.5 * 3.14/(48*48);
	
	private static final boolean lReverse = true;
	private static final boolean rReverse = false;
	
	private boolean reversed = false;
	private boolean straight = true;
	private double scale = 1.0;
	private double speed = 1.0;
	public static Encoder leftEnc;
	public static Encoder rightEnc;
	public Drive drive;
    // Initialize your subsystem here
    public AutoDrive(int l1, int l2, int r1, int r2, Drive d) {
    	super(kPD, kID, kDD);
    	super.setAbsoluteTolerance(kToleranceDegreesD);
    	super.setOutputRange(-1, 1);
    	leftEnc = new Encoder(l1, l2, lReverse, Encoder.EncodingType.k2X);
    	rightEnc = new Encoder(r1, r2, rReverse, Encoder.EncodingType.k2X);
    	leftEnc.setDistancePerPulse(encDist);
    	rightEnc.setDistancePerPulse(encDist);
    	super.disable();
    	drive = d;
    	LiveWindow.addActuator("AutoDrive", "PID", super.getPIDController());
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void setStraight() {
    	leftEnc.reset();
    	rightEnc.reset();
    	straight = true;
    	this.getPIDController().setPID(kPD, kID, kDD);
    	this.getPIDController().setAbsoluteTolerance(kToleranceDegreesD);
    }
    public void setRotate() {
    	straight = false;
    	this.getPIDController().setPID(kPR, kIR, kDR);
    	this.getPIDController().setAbsoluteTolerance(kToleranceDegreesR);
    }
    /*public void turnRight() {
    	drive.setMotors(1 * scale, -1 / scale);
    }
    public void turnLeft() {
    	drive.setMotors(1 / scale, -1 * scale);
    }*/
    public void turn() {
    	drive.setMotors(1 * scale, -1 / scale);
    }
    public void move() {
    	drive.setMotors(speed, speed);
    }
    protected double returnPIDInput() {
        if(!straight) {
        	return -leftEnc.getRate() / rightEnc.getRate();
        }
        else {
        	return leftEnc.getDistance();
        }
    }

    protected void usePIDOutput(double output) {
        if(!straight) {
        	scale = Math.pow(2,  output);
        }
        else {
        	speed = output;
        }
    }
	public double getRateR()
	{
		return leftEnc.getRate();
	}
	public double getDisR()
	{
		return rightEnc.getDistance();
	}
	public double getRateL()
	{
		return rightEnc.getRate();
	}
	public double getDisL()
	{
		return leftEnc.getDistance();
	}
	public void reverse() {
		reversed = !reversed;
		leftEnc.setReverseDirection(lReverse != reversed);
		rightEnc.setReverseDirection(rReverse != reversed);
		Encoder temp1 = leftEnc;
		Encoder temp2 = rightEnc;
		rightEnc = temp1;
		leftEnc = temp2;
	}
	public void printValues() {
		SmartDashboard.putNumber("LEnc", leftEnc.getRate());
		SmartDashboard.putNumber("REnc", rightEnc.getRate());	
		SmartDashboard.putString("The front side of the robot is: ", frontSide());
	}
	public String frontSide()
	{
		return reversed ? "Gear": "Intake";
	}
}
