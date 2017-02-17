package org.usfirst.frc.team6135.robot.subsystems;

import org.usfirst.frc.team6135.robot.Drive;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class AutoDrive extends PIDSubsystem {
	//Constants
	private static final double kPD = 0.03;//Driving specific distance
    private static final double kID = 0.01;
    private static final double kDD = 0.01;
    private static final double kFD = 0.01;
	private static final double kToleranceDegreesD = 2.0f;
    
	private static final double kPR = 0.03;//Rotating accurately
    private static final double kIR = 0.01;
    private static final double kDR = 0.01;
    private static final double kFR = 0.01;
	private static final double kToleranceDegreesR = 2.0f;
	
	private static final double encDist = 6 * 3.14 / 48;
	
	private boolean straight = true;
	private double scale = 1.0;
	private double speed = 1.0;
	Encoder leftEnc;
	Encoder rightEnc;
	public Drive drive;
    // Initialize your subsystem here
    public AutoDrive(int l1, int l2, int r1, int r2, Drive d) {
    	super(kPD, kID, kDD, kFD);
    	super.setAbsoluteTolerance(kToleranceDegreesD);
    	super.setOutputRange(-1, 1);
    	leftEnc = new Encoder(l1, l2, true, Encoder.EncodingType.k2X);
    	rightEnc = new Encoder(r1, r2, false, Encoder.EncodingType.k2X);
    	leftEnc.setDistancePerPulse(encDist);
    	rightEnc.setDistancePerPulse(encDist);
    	super.disable();
    	drive = d;
    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void setStraight() {
    	leftEnc.reset();
    	rightEnc.reset();
    	straight = true;
    	this.getPIDController().setPID(kPD, kID, kDD, kFD);
    	this.getPIDController().setAbsoluteTolerance(kToleranceDegreesD);
    	super.setOutputRange(-1, 1);
    }
    public void setRotate() {
    	straight = false;
    	this.getPIDController().setPID(kPR, kIR, kDR, kFR);
    	this.getPIDController().setAbsoluteTolerance(kToleranceDegreesR);
    	super.setOutputRange(0.5, 2);
    }
    public void turnRight() {
    	drive.setMotors(1 * scale, -1 / scale);
    }
    public void turnLeft() {
    	drive.setMotors(1 / scale, -1 * scale);
    }
    public void move() {
    	drive.setMotors(speed, speed);
    }
    protected double returnPIDInput() {
        if(!straight) {
        	return leftEnc.getRate() + rightEnc.getRate();
        }
        else {
        	return leftEnc.getDistance();
        }
    }

    protected void usePIDOutput(double output) {
        if(!straight) {
        	scale = output;
        }
        else {
        	speed = output;
        }
    }
}
