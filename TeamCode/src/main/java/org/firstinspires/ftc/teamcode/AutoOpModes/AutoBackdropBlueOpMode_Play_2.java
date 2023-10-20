package org.firstinspires.ftc.teamcode.AutoOpModes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.util.Timing;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.CommandGroups.AutoBackdrop.AutoBackdropBlue_Play_1;
import org.firstinspires.ftc.teamcode.CommandGroups.AutoBackdrop.AutoBackdropBlue_Play_2;
import org.firstinspires.ftc.teamcode.Lib.Hw;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;

import java.util.concurrent.TimeUnit;

@Autonomous(name = "Backdrop Blue Play 2", group = "Auto Wing Blue")
public class AutoBackdropBlueOpMode_Play_2 extends CommandOpMode {
    Timing.Timer m_timer;

    double m_timerAvg = 0.0;
    double m_timerCnt = 0;
    Hw hw;
    DriveSubsystem drive;
    ArmSubsystem arm;
    AutoBackdropBlue_Play_2 auto;
    @Override
    public void initialize() {
        hw = new Hw(this);
        hw.init();

        // Create Subsystems
        drive = new DriveSubsystem(this);
        arm = new ArmSubsystem(this);
        //drive.setDefaultCommand(new DriveDefaultCommand(this, drive));

        // Create Commands
        auto = new AutoBackdropBlue_Play_2(this, drive,arm);

        // Register subsystems
        register(drive);

        m_timer = new Timing.Timer(100, TimeUnit.MILLISECONDS);
        m_timer.start();
    }
    @Override

    public void runOpMode() throws InterruptedException{
        initialize();

        waitForStart();
        // Schedule the auto play to run
        CommandScheduler.getInstance().schedule(auto);
        // run the scheduler
        while (!isStopRequested() || opModeIsActive()) {
            run();
            // Calculate the run rate of this loop
            telemetry.addData("CPU Load Auto %", 100 - m_timer.remainingTime());
            telemetry.update();
            // wait till timer is > 50ms to try an create a stable run rate
            while(!m_timer.done()){} m_timer.start();

        }
        reset();
    }
}
