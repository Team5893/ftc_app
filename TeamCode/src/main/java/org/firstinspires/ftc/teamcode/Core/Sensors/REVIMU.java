package org.firstinspires.ftc.teamcode.Core.Sensors;


import com.qualcomm.hardware.adafruit.AdafruitBNO055IMU;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.BNO055IMUImpl;
import com.qualcomm.hardware.lynx.LynxEmbeddedIMU;
import com.qualcomm.robotcore.util.ReadWriteFile;

import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.Core.HardwareMapper;
import org.firstinspires.ftc.teamcode.Core.RobotBase;

import java.io.File;

public class REVIMU
{
    private RobotBase _robot = null;

    private BNO055IMU _imu = null;

    private Orientation _orien = null;
    private Acceleration _accel = null;
    private Position _pos = null;


    /**
     * Creates a new gyroscope
     *
     * @param myRobot The robot we're working with
     */
    public REVIMU(RobotBase myRobot)
    {
        _robot = myRobot;
    }



    /**
     * Maps the Bosch BNO055 IMU given a name and parameters
     *
     * @param NAME Name of the IMU as appears in the configuration file
     * @param PARAMETERS Fully initialized Parameters of the IMU
     */
    public void mapIMU(final String NAME , BNO055IMU.Parameters PARAMETERS)
    {
        HardwareMapper mapHelper = new HardwareMapper(_robot);  // Helps with mapping

        _imu = mapHelper.mapREVIMU(NAME , PARAMETERS);
    }


    /**
     * Writes the calibration data to the file.
     */
    public void writeCalibrationFile()
    {
        BNO055IMU.CalibrationData calData = _imu.readCalibrationData();

        File file = AppUtil.getInstance().getSettingsFile(_imu.getParameters().calibrationDataFile);
        ReadWriteFile.writeFile(file , calData.serialize());
    }


    /**
     * @return The calibration status as a String
     */
    public String calibrationStatus()
    {
        return _imu.getCalibrationStatus().toString();
    }


    /**
     * Starts the logging of measured acceleration
     */
    public void startAccelMeasurement()
    {
        _imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
    }


    /**
     * Pulls sensor readings
     */
    public void pull()
    {
        _orien = _imu.getAngularOrientation(AxesReference.INTRINSIC , AxesOrder.XYZ , AngleUnit.DEGREES);
        _accel = _imu.getGravity();
        _pos = _imu.getPosition();
    }


    /**
     * @return Returns x orientation from the gyro
     */
    public double xAngle()
    {
        return _orien.firstAngle;
    }


    /**
     * @return Returns y orientation from the gyro
     */
    public double yAngle()
    {
        return _orien.secondAngle;
    }


    /**
     * @return Returns z orientation from the gyro
     */
    public double zAngle()
    {
        return _orien.thirdAngle;
    }


    /**
     * @return Returns acceleration in the x direction from the accelerometer
     */
    public double xAccel()
    {
        return _accel.xAccel;
    }


    /**
     * @return Returns acceleration in the y direction from the accelerometer
     */
    public double yAccel()
    {
        return _accel.yAccel;
    }


    /**
     * @return Returns acceleration in the z direction from the accelerometer
     */
    public double zAccel()
    {
        return _accel.zAccel;
    }


    /**
     * @return Returns position in the x direction
     */
    public double xPos()
    {
        return _pos.x;
    }


    /**
     * @return Returns position in the y direction
     */
    public double yPos()
    {
        return _pos.y;
    }


    /**
     * @return Returns position in the z direction
     */
    public double zPos()
    {
        return _pos.z;
    }
}
