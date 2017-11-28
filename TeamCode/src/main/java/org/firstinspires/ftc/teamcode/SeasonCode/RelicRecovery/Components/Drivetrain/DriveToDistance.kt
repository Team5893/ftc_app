@file:Suppress("PackageDirectoryMismatch")
package org.directcurrent.season.relicrecovery.drivetrain


import com.vuforia.CameraDevice
import org.firstinspires.ftc.robotcontroller.internal.Core.RobotCommand
import org.firstinspires.ftc.teamcode.SeasonCode.RelicRecovery.Components.Drivetrain.Drivetrain


/**
 * Command to drive the robot to a certain distance
 */
class DriveToDistance(private var _drivetrain: Drivetrain): RobotCommand()
{
    private val _COUNTS_PER_INCH = 64.3304

    private var _interrupt = false
    private var _busy = false

    private var _distance = 0.0

    private var _CLOSE_ENOUGH = 10


    /**
     * Sets the destination to drive to. Call this before running the command.
     *
     * You have to.
     *
     * Yes, I know you don't want to.
     */
    fun setDestination(distance: Double)
    {
        _distance = distance
    }


    /**
     * Drives to the defined distance on the main thread
     */
    override fun runSequentially()
    {
        // Freeze input to the drivetrain so that only the command can control it
        // Of course, the idea is that it can be overwritten
        _drivetrain.freezeInput()

        _drivetrain.encoderStopReset()
        _drivetrain.encoderOn()

        _drivetrain.leftMotor().targetPosition = (_distance / _COUNTS_PER_INCH).toInt()
        _drivetrain.rightMotor().targetPosition = (_distance / _COUNTS_PER_INCH).toInt()

        _busy = true
        while(Math.abs(_drivetrain.leftEncoderCount() + _drivetrain.rightEncoderCount()) / 2 -
                Math.abs(_drivetrain.leftEncoderTarget() + _drivetrain.rightEncoderTarget()) / 2 >
                _CLOSE_ENOUGH && !_interrupt)
        {
            _drivetrain.leftMotor().power = 1.0
            _drivetrain.rightMotor().power = 1.0
        }

        _busy = false
        _drivetrain.allowInput()
    }


    /**
     * Drives to the defined distance on a separate thread
     */
    override fun runParallel()
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    /**
     * Returns whether or not the command is currently running
     */
    override fun isBusy(): Boolean
    {
        return _busy
    }


    /**
     * Stops execution of the command. Note that this probably won't work if the command is executed
     * on the main thread- in such a case, a call has to originate from within the class
     */
    override fun stop()
    {
        _interrupt = true
    }
}