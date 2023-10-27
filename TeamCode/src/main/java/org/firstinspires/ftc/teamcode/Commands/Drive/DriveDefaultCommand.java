package org.firstinspires.ftc.teamcode.Commands.Drive;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.controller.PIDController;


import org.firstinspires.ftc.teamcode.Lib.Hw;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;

public class DriveDefaultCommand extends CommandBase {
    // Declare a variable called "drive" of type "DriveSubsystem"
    DriveSubsystem m_drive;

    // Declare a variable called "opMode" of type "CommandOpMode"
    CommandOpMode m_opMode;
    // Create local variables of type double to store the stick X,Y,Z values and Angle of robot.
    double m_x, m_y, m_z, m_ang;
    PIDController rotPID = new PIDController(.0075,0.005,0);
    /** Constructor of class
     *
     * @param _opMode The opMode used which will be teleOp or Autonomous
     * @param _drive The DriveSubsystem instance variable
     */
    public DriveDefaultCommand(CommandOpMode _opMode, DriveSubsystem _drive) {

        m_drive = _drive;    // Set the local "m_drive" variable to the parameter "_drive"


        m_opMode = _opMode;  // Set the local "opMode" variable to the parameter "_opMode"
        // Set the requirements for the Command. This always must be done for a "Command"
        // The requirement is any subsystem that will be used by this command.
        addRequirements(m_drive);
    }

    @Override
    public void initialize(){
        m_drive.setIsFieldOriented(true);
    }

    @Override
    public void execute(){

        // Get the X,Y, and Z axis values from the Driver joystick.
        // The values from the joystick are always in the range of +/- 1.0

        m_y = -Hw.s_gpDriver.getRightY();
        m_x = Hw.s_gpDriver.getRightX();
        m_z = Hw.s_gpDriver.getLeftX();
        // Handle rotation value
        if(Math.abs(m_z) > 0.2){ // in rotation mode
            // Scale the turning rotation down by 1/2
            m_z = Math.signum(m_z) * (Math.abs(m_z) - 0.2);
            m_z = m_z * 0.35;
            m_drive.setDrivePIDAngle(361);
        }else if(Math.abs(m_drive.getDrivePIDAngle()) < 360){ // In PID rotation
            m_z = -rotPID.calculate(m_drive.getRobotAngle(), m_drive.getDrivePIDAngle());
        }else { // Not PID and lower than the deadband
            m_z = 0.0;
        }
        // Call the drive method "driveCaresianXY" with the stick X,Y,Z and angle parameters
        m_drive.driveXY(m_x,m_y, m_z);

        m_opMode.telemetry.addData("X", m_x);
        m_opMode.telemetry.addData("Y", m_y);
        m_opMode.telemetry.addData("Z", m_z);
    }
    @Override
    public void end(boolean _interrupted){
        m_drive.driveXY(0,0,0);
    }

}
