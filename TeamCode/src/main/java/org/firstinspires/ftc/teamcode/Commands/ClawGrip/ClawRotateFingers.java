package org.firstinspires.ftc.teamcode.Commands.ClawGrip;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandOpMode;

import org.firstinspires.ftc.teamcode.Subsystems.AutoClawGripSubsystem;

/**
 */
public class ClawRotateFingers extends CommandBase {
    CommandOpMode m_opMode;
    boolean isFinished = false;
    AutoClawGripSubsystem m_claw;
    double m_angle;

    public ClawRotateFingers(CommandOpMode _opMode, AutoClawGripSubsystem _claw, double _angle) {
        m_opMode = _opMode;
        m_claw = _claw;
        m_angle = _angle;
        addRequirements(m_claw);
    }

    @Override
    public void initialize(){
        m_claw.setClawGripAngle(m_angle);
    }
    @Override
    public void execute(){
        //GlobalData.ClawAngleAuto = m_angle;
        isFinished = true;
    }
    @Override
    public boolean isFinished(){
        return isFinished;
    }
    @Override
    public void end(boolean _interrupted){

    }
}
