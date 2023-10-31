package org.firstinspires.ftc.teamcode.Subsystems;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.wpilibcontroller.ArmFeedforward;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.Lib.ArmPos;
import org.firstinspires.ftc.teamcode.Lib.Hw;
import org.firstinspires.ftc.teamcode.Lib.k;

public class Shoulder {

    private  MotorEx m_motor;
    private CommandOpMode m_opMode;
    private PIDController rotPID;
    private ArmFeedforward armFeedforward;
    private ArmSubsystem m_arm; // Used here to get the limits from the arm.
    private double ks, kv, kCos;
    private double vel = 0.01;
    private double kfa, kfaMax;
    public Shoulder(CommandOpMode _opMode, ArmSubsystem _arm) {
        m_opMode = _opMode;
        m_arm = _arm;
        initHardware();
    }

    private void initHardware(){
        m_motor = new MotorEx(m_opMode.hardwareMap, Hw.ShoulderMotor, MotorEx.GoBILDA.RPM_435);
        m_motor.setRunMode(Motor.RunMode.RawPower);  // Wish I had voltage mode
        m_motor.setInverted(false);
        m_motor.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        m_motor.resetEncoder();
        rotPID = new PIDController(0.0075,0.025,0);
        rotPID.setTolerance(.1);
        rotPID.setIntegrationBounds(-5,5);
        rotPID.reset();

        ks = 0.0;
        kv = 0.0;
        kCos = 0.2;
        vel = 0.0;
        kfa = 0.2; // Forearm Constant multiplier for arm extension.
        kfaMax = 0.45;
    }

    /**  <b>setAngle</b><p>
     * This function uses logic from the armFeedForward class as a base.
     * A rotating arm requires changing values since gravity affects the amount of torque as the arm rotates.
     * At a vertical position we call 0 degrees the arm requires a feedforward value of around 0.2 to
     * make the arm hold it's position of vertical.</p><p>
     * The Cos value of the angle is used to change the feedforward value from a max of ~0.2
     * A PID loop does the rest to set the power to make the motor move to position.
     * </p><p></p>
     * Since this is using raw power and not voltage mode, the control will never be perfect since
     * we must rely on a changing battery supply.
     *
     * @param _ang The angle you want the shoulder to move to
     */
    public void setAngle(double _ang){

        // Get the current arm angle in degrees
        double Cosang = getAngle();
        // Subtract the straight angle from it to make horizontal be 0 Degrees
        Cosang -= m_arm.getArmSetAngle(ArmPos.STRAIGHT);
        // Convert the angle to radians for Cos function
        Cosang = Math.toRadians(Cosang);

        double ShAngRad = Math.toRadians(_ang);
        // Find scale factor for feed forward. This is the power setting that makes the arm rotate freely.
        double kfaScale = (kfaMax - kfa) / Math.toRadians(m_arm.getArmSetAngle(ArmPos.BACKDROPUPLIMIT) - m_arm.getArmSetAngle(ArmPos.STRAIGHT));
        // Calculate the Arm Feedforward. Cos of 0 Deg = 1.0, 1.0 * kfa = 0.2
        double cos = Math.cos(Cosang) * kfa;
        // Between the area where the arm extends and changes the cos value
        if(Cosang > 0 && Cosang < Math.toRadians(m_arm.getArmSetAngle(ArmPos.BACKDROPUPLIMIT))){
            cos = cos + Cosang * kfaScale;  // Add a little more power since the forearm is extending
        }
        // Calculate the PID value, Since the feedforward is doing most the PID does not have to do much
        double rot = rotPID.calculate(getAngle(), _ang);
        // When near vertical the arm needs very little to make it move so scale back the PID near vertical
        if(Cosang > Math.toRadians(m_arm.getArmSetAngle(ArmPos.BACKDROPUPLIMIT))){
            rot = rot * 0.8;
        }
        // Set the raw power to the motor of the cos/feedforward and PID values
        setPower(cos + rot);
    }
    public void setPower(double _pwr){
        m_motor.set(_pwr);
    }
    public double getAngle(){
       return  m_motor.getCurrentPosition() / k.SHOULDER.Motor_CountsPDeg;
    }

}
