package org.firstinspires.ftc.teamcode.CommandGroups.AutoWing;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.Lib.GlobalData;
import org.firstinspires.ftc.teamcode.Lib.TeamColor;
import org.firstinspires.ftc.teamcode.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.DriveSubsystem;

public class AutoWingBlue_Play_2 extends SequentialCommandGroup {

    public AutoWingBlue_Play_2(CommandOpMode _opMode, DriveSubsystem _drive, ArmSubsystem _arm) {
        GlobalData.s_teamColor = TeamColor.BLUE;
        addCommands(
            new ParallelCommandGroup(

            )


        );

    }
}