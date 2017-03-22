package org.usfirst.frc.team6135.robot.subsystems;


	import java.util.ArrayList;

	import org.opencv.core.MatOfPoint;
	import org.opencv.core.Rect;
	import org.opencv.imgproc.Imgproc;
	import org.usfirst.frc.team6135.robot.RobotMap;
    import org.usfirst.frc.team6135.robot.commands.ProcessImages;

    import edu.wpi.cscore.UsbCamera;
	import edu.wpi.first.wpilibj.CameraServer;
	import edu.wpi.first.wpilibj.command.Subsystem;
	import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
	import edu.wpi.first.wpilibj.vision.VisionRunner;
	import edu.wpi.first.wpilibj.vision.VisionThread;

	/**
	 *
	 */
	public class Vision extends Subsystem  implements VisionRunner.Listener<Pipeline> {

	    // Put methods for controlling this subsystem
	    // here. Call these from Commands.
	    // A USB camera connected to the roboRIO.
		
	    private UsbCamera usbCam;
	    private boolean onTarget, xDirection, yDirection;
	    private double TargetCMX, TargetCMY;
	    private int TargetPixelHeight;
	    private VisionData visionData;

	    //TODO:  Should be able to stop the running thread processing images after autonomous ends... 
		public Pipeline pipeline;
		private VisionThread visionThread;

	    // The object to synchronize on to make sure the vision thread doesn't
	    // write to variables the main thread is using.
	    private final Object visionLock = new Object();

	    // The pipeline outputs we want
		private double angleToGearPeg = 0;
		private double distanceToGearPeg = 0;
		
		public Vision(){
			//Empty constructor
		}
		
		public void startVisionProcessing(){
			usbCam = CameraServer.getInstance().startAutomaticCapture(0);
			TargetCMX = TargetCMY = 0.0;
			TargetPixelHeight = 0;
			onTarget = xDirection = yDirection = false;
			usbCam.setExposureManual(1);
			usbCam.setResolution(RobotMap.xResolution, RobotMap.yResolution);
			pipeline = new Pipeline();
			visionThread = new VisionThread(usbCam, pipeline, this);
			visionThread.start();
		}
		
		public void processImage(){
			//Call this method from a command run during autonomous
			//to produce image results used to drive and direct the robot
			copyPipelineOutputs(pipeline);
		}
		public boolean getIsOnTarget(){
			return this.onTarget;
		}
		
		public double getTargetCMX(){
			return this.TargetCMX;
		}
		
		public double getTargetCMY(){
			return this.TargetCMY;
		}
		
		public double getAngleToGearPeg(){
			return this.angleToGearPeg;
		}
		
		public double getDistanceToGearPeg(){
			return this.distanceToGearPeg;
		}
		
		public boolean getXDirection(){
			return this.xDirection;
		}
		
		public boolean getYDirection(){
			return this.yDirection;
		}
		
	    public void initDefaultCommand() {
	        // Set the default command for a subsystem here.
	        //setDefaultCommand(new MySpecialCommand());
	    	
	    	//Call the copyPipelineOutputs method from a command to process image data
	    	setDefaultCommand(new ProcessImages());
	    }
	    
	    public void updateStatus(){
	    	synchronized (visionLock){
	    		//SmartDashboard.putBoolean("Is On Target : ", visionData.isOnTarget());
	    		SmartDashboard.putNumber("Target CMX : ", visionData.getTargetCMX());
	    		//SmartDashboard.putNumber("Target CMY : ", visionData.getTargetCMY());
	    		SmartDashboard.putNumber("Distance to Target : ", visionData.getDistanceToGearPeg());
	    		//SmartDashboard.putBoolean("X Direction : ", visionData.isxDirection());
	    		//SmartDashboard.putBoolean("Y Direction : ", visionData.isyDirection());
	    		SmartDashboard.putNumber("Target Pixel Height:", visionData.getTargetPixelHeight());
	    	}
	    }
	    public void endThread() {
			visionThread.interrupt();
			return;
	    }
	    public VisionData getVisionData(){
	    	synchronized (visionLock){
	    		return visionData;
	    	}
	    }
		@Override
		public void copyPipelineOutputs(Pipeline pipeline) {
	        synchronized (visionLock) {
	            // Take a snapshot of the pipeline's output because
	            // it may have changed the next time this method is called!
	            if(!pipeline.filterContoursOutput().isEmpty()){
					final ArrayList<MatOfPoint> target = pipeline.filterContoursOutput();
					this.TargetPixelHeight = target.get(0).height();
					final Rect r = Imgproc.boundingRect(target.get(0));
	            	this.TargetCMX = (r.x + (r.width / 2));
	            	this.TargetCMY = (r.y + (r.height / 2));
	            }
	            this.visionData = new VisionData(pipeline.onTarget(),pipeline.getXDirection(),
	        			pipeline.getYDirection(),pipeline.getTargetCMX(),pipeline.getTargetCMY(),
	        			pipeline.calcAngleToGearPeg(),pipeline.calcDistanceToGearPeg(pipeline.getTargetPixelHeight()), pipeline.getTargetPixelHeight());
	        }
		}
	}
