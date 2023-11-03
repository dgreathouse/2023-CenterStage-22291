package org.firstinspires.ftc.teamcode.AutoOpModes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandGroups.Boys_22291.BAutoWingRed_Play_1;
import org.firstinspires.ftc.teamcode.CommandGroups.Boys_22291.BAutoWingRed_Play_3;
import org.firstinspires.ftc.teamcode.CommandGroups.Girls_14623.GAutoWingRed_Play_1;
import org.firstinspires.ftc.teamcode.CommandGroups.Girls_14623.GAutoWingRed_Play_3;
import org.firstinspires.ftc.teamcode.Commands.Arm.ArmDefaultCommand;
import org.firstinspires.ftc.teamcode.Lib.GlobalData;
import org.firstinspires.ftc.teamcode.Lib.Hw;
import org.firstinspires.ftc.teamcode.Lib.TeamPropLocation;
import org.firstinspires.ftc.teamcode.Lib.k;
import org.firstinspires.ftc.teamcode.Subsystems.ArmAutoSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ClawAutoGripSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;

import java.util.concurrent.TimeUnit;

@Autonomous(name = "Wing Red Play 3", group = "Auto Wing Red")
public class AutoWingRedOpMode_Play_3 extends CommandOpMode {
    Timing.Timer m_timer;

    Hw hw;
    DriveSubsystem drive;
    ArmAutoSubsystem arm;
    ClawAutoGripSubsystem claw;

    GAutoWingRed_Play_3 Gauto;
    BAutoWingRed_Play_3 Bauto;

    @Override
    public void initialize() {
        hw = new Hw(this);
        hw.init();

        // Create Subsystems
        drive = new DriveSubsystem(this);
        arm = new ArmAutoSubsystem(this);
        claw = new ClawAutoGripSubsystem(this);
        // Create Commands


        createCommandGroup();
        // Register subsystems
        register(drive,arm);


        m_timer = new Timing.Timer(100, TimeUnit.MILLISECONDS);
        m_timer.start();
        GlobalData.TeamPropLocation = TeamPropLocation.NONE;
    }
    @Override
    public void runOpMode() throws InterruptedException{
        initialize();

        waitForStart();
        // Schedule the auto play to run
        scheduleCommandGroup();


        // run the scheduler
        while (!isStopRequested() || opModeIsActive()) {
            run();
            // Calculate the run rate of this loop
            telemetry.addData("CPU Load Auto %", 100 - m_timer.remainingTime());
            telemetry.addData("TeamPropLocation", GlobalData.TeamPropLocation);
            telemetry.update();
            // wait till timer is > 50ms to try an create a stable run rate
            // TODO: Decide if timer is needed and helps with accuracy of driving and change accordingly
            if(k.SYSTEM.isLoopRateLimited){while(!m_timer.done()){} m_timer.start();}


        }
        reset();
    }
    private void createCommandGroup() {
        if(GlobalData.TeamNumber == 22291) {
            Bauto = new BAutoWingRed_Play_3(this, drive,arm,claw);
        }else {
            Gauto = new GAutoWingRed_Play_3(this, drive,arm,claw);
        }

    }
    private void scheduleCommandGroup() {
        if(GlobalData.TeamNumber == 22291) {
            CommandScheduler.getInstance().schedule(Bauto);
        }else {
            CommandScheduler.getInstance().schedule(Gauto);
        }
    }
}
