package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.teamcode.Lib.GlobalData;
import org.firstinspires.ftc.teamcode.Lib.Hw;
import org.firstinspires.ftc.teamcode.Lib.KiwiDrive;
import org.firstinspires.ftc.teamcode.Lib.k;

public class AutoDriveSubsystem extends SubsystemBase {
    // Declare the MotorEx and Vector2D classes for each motor
    private MotorEx m_lDrive, m_rDrive, m_bDrive;
    private KiwiDrive m_drive;
    // Declare a CommandOpMode variable
    private CommandOpMode m_opMode;

    /** Class Constructor
     *
     * @param _opMode The opMode used which will be Driver Controlled or Autonomous
     */
    public AutoDriveSubsystem(CommandOpMode _opMode, Hw _hw) {
        m_opMode = _opMode;
        initHardware();
        m_drive = new KiwiDrive(m_lDrive,m_rDrive, m_bDrive);

    }
    private void initHardware(){
        m_lDrive = new MotorEx(m_opMode.hardwareMap, Hw.DriveFrontLeft, Motor.GoBILDA.RPM_435);
        m_lDrive.setInverted(true);
        m_lDrive.setRunMode(Motor.RunMode.VelocityControl);
        m_lDrive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        m_lDrive.setDistancePerPulse(k.DRIVE.InchPerCount);
        m_lDrive.encoder.setDirection(Motor.Direction.FORWARD);
        m_lDrive.setVeloCoefficients(k.DRIVE.Drive_P,k.DRIVE.Drive_I,0);

        m_rDrive = new MotorEx(m_opMode.hardwareMap, Hw.DriveFrontRight, Motor.GoBILDA.RPM_435);
        m_rDrive.setInverted(true);
        m_rDrive.setRunMode(Motor.RunMode.VelocityControl);
        m_rDrive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        m_rDrive.setDistancePerPulse(k.DRIVE.InchPerCount);
        m_rDrive.encoder.setDirection(Motor.Direction.REVERSE);
        m_rDrive.setVeloCoefficients(k.DRIVE.Drive_P,k.DRIVE.Drive_I,0);

        m_bDrive = new MotorEx(m_opMode.hardwareMap, Hw.DriveBack, Motor.GoBILDA.RPM_435);
        m_bDrive.setInverted(true);
        m_bDrive.setRunMode(Motor.RunMode.VelocityControl);
        m_bDrive.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        m_bDrive.setDistancePerPulse(k.DRIVE.InchPerCount);
        m_bDrive.encoder.setDirection(Motor.Direction.REVERSE);
        m_bDrive.setVeloCoefficients(k.DRIVE.Drive_P,k.DRIVE.Drive_I,0);
    }

    /**
     *
     * @param _strafeSpeed The forward speed in +/- 1 left is positive
     * @param _forwardSpeed The strafe speed in +/- 1 forward is positive
     * @param _zRotation The rotation speed in +/- 1 CCW/left is positive
     */
    public void driveXY(double _strafeSpeed, double _forwardSpeed, double _zRotation) {
        m_lDrive.setRunMode(Motor.RunMode.VelocityControl);
        m_rDrive.setRunMode(Motor.RunMode.VelocityControl);
        m_bDrive.setRunMode(Motor.RunMode.VelocityControl);
        m_drive.driveXY(_strafeSpeed,_forwardSpeed,_zRotation,getRobotAngle());
    }

    /**
     *
     * @param _angle The angle to drive at.
     * @param _speed The speed from +/- 1.0 to drive at
     * @param _rot The direction you want the robot to face in field oriented mode.
     */
    public void drivePolar(double _angle, double _speed, double _rot){
        m_lDrive.setRunMode(Motor.RunMode.VelocityControl);
        m_rDrive.setRunMode(Motor.RunMode.VelocityControl);
        m_bDrive.setRunMode(Motor.RunMode.VelocityControl);

        m_drive.drivePolar(_angle,_speed,_rot,getRobotAngle());
    }

    /**
     *
     * @return The robot angle in degrees with CCW as positive
     */
    public double getRobotAngle(){
        YawPitchRollAngles angles = Hw.s_imu.getRobotYawPitchRollAngles();
        return angles.getYaw(AngleUnit.DEGREES);
    }
    public void disableMotors(){
        m_lDrive.setRunMode(Motor.RunMode.RawPower);
        m_rDrive.setRunMode(Motor.RunMode.RawPower);
        m_bDrive.setRunMode(Motor.RunMode.RawPower);
        m_lDrive.set(0);
        m_rDrive.set(0);
        m_bDrive.set(0);
    }
    public void setIsFieldOriented(boolean _val){
        m_drive.setIsFieldOriented(_val);
    }
    @Override
    public void periodic(){
        m_opMode.telemetry.addData("Robot Angle", "%3.3f",getRobotAngle());
        m_opMode.telemetry.addData("Drive Angle", "%3.3f", m_drive.getDriveAngle());
//        m_opMode.telemetry.addLine(String.format("AprilTag X: %.4f m", GlobalData.AprilTag_X));
//        m_opMode.telemetry.addLine(String.format("AprilTag Y: %.4f m", GlobalData.AprilTag_Y));
//        m_opMode.telemetry.addLine(String.format("AprilTag Z: %.4f m", GlobalData.AprilTag_Z));
//       m_opMode.telemetry.addData("AprilTag Angle", "%3.4f", GlobalData.AprilTagBearing);
//        m_opMode.telemetry.addData("AprilTag Distance", "%3.4f", GlobalData.AprilTagRange);
//        m_opMode.telemetry.addData("TPS_Left", m_lDrive.getCorrectedVelocity());
//        m_opMode.telemetry.addData("TPS_Right", m_rDrive.getCorrectedVelocity());
//        m_opMode.telemetry.addData("TPS_Back", m_bDrive.getCorrectedVelocity());

    }
}
