package org.usfirst.frc.team6135.robot.subsystems;

public class VisionData {
	private boolean onTarget, xDirection, yDirection;
    private double TargetCMX, TargetCMY;
	private double angleToGearPeg = 0;
	private double distanceToGearPeg = 0;
	private double targetPixelHeight = 0;
	public VisionData(boolean oT, boolean xD, boolean yD, double cmx, double cmy, double angle, double distance, double height){
		setOnTarget(oT);
		setxDirection(xD);
		setyDirection(yD);
		setTargetCMX(cmx);
		setTargetCMY(cmy);
		setAngleToGearPeg(angle);
		setTargetPixelHeight(height); 
		setDistanceToGearPeg(distance);
	}
	public boolean isOnTarget() {
		return onTarget;
	}
	public void setOnTarget(boolean onTarget) {
		this.onTarget = onTarget;
	}
	public double getTargetCMX() {
		return TargetCMX;
	}
	public void setTargetCMX(double targetCMX) {
		TargetCMX = targetCMX;
	}
	public double getTargetCMY() {
		return TargetCMY;
	}
	public void setTargetCMY(double targetCMY) {
		TargetCMY = targetCMY;
	}
	public double getDistanceToGearPeg() {
		return distanceToGearPeg;
	}
	public void setDistanceToGearPeg(double distanceToGearPeg) {
		this.distanceToGearPeg = distanceToGearPeg;
	}
	public boolean isxDirection() {
		return xDirection;
	}
	public void setxDirection(boolean xDirection) {
		this.xDirection = xDirection;
	}
	public boolean isyDirection() {
		return yDirection;
	}
	public void setyDirection(boolean yDirection) {
		this.yDirection = yDirection;
	}
	public double getAngleToGearPeg() {
		return angleToGearPeg;
	}
	public void setAngleToGearPeg(double angleToGearPeg) {
		this.angleToGearPeg = angleToGearPeg;
	}
	public double getTargetPixelHeight() {
		return targetPixelHeight;
	}
	public void setTargetPixelHeight(double targetPixelHeight) {
		this.targetPixelHeight = targetPixelHeight;
	}
}
