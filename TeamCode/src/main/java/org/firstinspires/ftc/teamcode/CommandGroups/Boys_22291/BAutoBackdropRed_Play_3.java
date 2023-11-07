package org.firstinspires.ftc.teamcode.CommandGroups.Boys_22291;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.Commands.AutoStopOpModeCommand;
import org.firstinspires.ftc.teamcode.Lib.AutoFieldLocation;
import org.firstinspires.ftc.teamcode.Lib.GlobalData;
import org.firstinspires.ftc.teamcode.Lib.TeamColor;
import org.firstinspires.ftc.teamcode.Subsystems.AutoArmSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.AutoDriveSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.AutoClawGripSubsystem;

public class BAutoBackdropRed_Play_3 extends SequentialCommandGroup {

    public BAutoBackdropRed_Play_3(CommandOpMode _opMode, AutoDriveSubsystem _drive, AutoArmSubsystem _arm, AutoClawGripSubsystem _claw) {
        GlobalData.TeamColor = TeamColor.RED;
        GlobalData.FieldLocation = AutoFieldLocation.BACKDROP;
        addCommands(

                new AutoStopOpModeCommand(_opMode) // This must be the last line of every command list



        );

    }
}
